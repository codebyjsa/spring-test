package com.example.demo.controller;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.QuestionService;
import com.example.demo.util.TestDataUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class QuestionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuestionController questionController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(questionController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getQuestionById_ShouldReturnQuestion() throws Exception {
        // Given
        QuestionDTO questionDTO = TestDataUtil.createTestQuestionDTO();
        when(questionService.getQuestionById(1L)).thenReturn(questionDTO);

        // When/Then
        mockMvc.perform(get("/api/questions/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answerId", is(questionDTO.getAnswerId().intValue())))
                .andExpect(jsonPath("$.proposalId", is(questionDTO.getProposalId().intValue())))
                .andExpect(jsonPath("$.questionId", is(questionDTO.getQuestionId().intValue())))
                .andExpect(jsonPath("$.answer", is(questionDTO.getAnswer())));
    }

    @Test
    void getQuestionById_WhenNotFound_ShouldReturnNotFound() throws Exception {
        // Given
        when(questionService.getQuestionById(999L))
                .thenThrow(new ResourceNotFoundException("Question not found with id: 999"));

        // When/Then
        mockMvc.perform(get("/api/questions/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Question not found with id: 999")));
    }

    @Test
    void deleteQuestion_ShouldReturnNoContent() throws Exception {
        // Given
        doNothing().when(questionService).deleteQuestion(1L);

        // When/Then
        mockMvc.perform(delete("/api/questions/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        
        verify(questionService, times(1)).deleteQuestion(1L);
    }
}
