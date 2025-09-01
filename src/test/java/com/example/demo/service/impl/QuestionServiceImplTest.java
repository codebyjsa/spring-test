package com.example.demo.service.impl;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.entity.Question;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.util.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import com.example.demo.mapper.QuestionMapper;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @Mock
    private QuestionRepository questionRepository;
    
    @Mock
    private QuestionMapper questionMapper;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Test
    void getAllQuestions_ShouldReturnListOfQuestionDTOs() {
        // Given
        Question question = TestDataUtil.createTestQuestion();
        QuestionDTO questionDTO = TestDataUtil.createTestQuestionDTO();
        List<Question> questions = List.of(question);

        when(questionRepository.findAll()).thenReturn(questions);
        when(questionMapper.toDTO(question)).thenReturn(questionDTO);

        // When
        List<QuestionDTO> result = questionService.getAllQuestions();

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(questionDTO, result.get(0));
        verify(questionRepository, times(1)).findAll();
        verify(questionMapper, times(1)).toDTO(question);
    }

    @Test
    void getAllQuestions_WhenNoQuestionsFound_ShouldThrowResourceNotFoundException() {
        // Given
        when(questionRepository.findAll()).thenReturn(Collections.emptyList());

        // When & Then
        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> questionService.getAllQuestions()
        );
        
        assertEquals("No questions found", exception.getMessage());
    }
}
