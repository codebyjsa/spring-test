package com.example.demo.exception;

import com.example.demo.controller.QuestionController;
import com.example.demo.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuestionController questionController;

    @BeforeEach
    void setUp() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(questionController)
                .setControllerAdvice(globalExceptionHandler)
                .build();
    }

    @Test
    void handleResourceNotFoundException_ShouldReturnNotFoundResponse() throws Exception {
        // Given
        Long questionId = 1L;
        when(questionService.getQuestionById(questionId))
                .thenThrow(new ResourceNotFoundException("Question not found with id: " + questionId));

        // When & Then
        mockMvc.perform(get("/api/questions/{id}", questionId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.message").value("Question not found with id: " + questionId))
                .andExpect(jsonPath("$.path").exists());
    }

    @Test
    void handleAllExceptions_ShouldReturnInternalServerError() throws Exception {
        // Given
        when(questionService.getAllQuestions())
                .thenThrow(new RuntimeException("Unexpected error occurred"));

        // When & Then
        mockMvc.perform(get("/api/questions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.message").exists());
    }
}
