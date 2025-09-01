package com.example.demo.util;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.entity.Question;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class TestDataUtil {
    
    public static Question createTestQuestion() {
        return createTestQuestion(1L, 100L, 200L, "Sample answer");
    }
    
    public static Question createTestQuestion(Long answerId, Long proposalId, Long questionId, String answer) {
        Question question = new Question();
        question.setAnswerId(answerId);
        question.setProposalId(proposalId);
        question.setQuestionId(questionId);
        question.setAnswer(answer);
        question.setCreatedAt(LocalDateTime.now());
        question.setUpdatedAt(LocalDateTime.now());
        return question;
    }
    
    public static QuestionDTO createTestQuestionDTO() {
        return createTestQuestionDTO(1L, 100L, 200L, "Sample answer");
    }
    
    public static QuestionDTO createTestQuestionDTO(Long answerId, Long proposalId, Long questionId, String answer) {
        LocalDateTime now = LocalDateTime.now();
        return QuestionDTO.builder()
                .answerId(answerId)
                .proposalId(proposalId)
                .questionId(questionId)
                .answer(answer)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
    
    public static List<Question> createTestQuestions() {
        Question q1 = createTestQuestion(1L, 100L, 200L, "First answer");
        Question q2 = createTestQuestion(2L, 101L, 201L, "Second answer");
        Question q3 = createTestQuestion(3L, 102L, 202L, "Third answer");
        
        return Arrays.asList(q1, q2, q3);
    }
    
    public static List<QuestionDTO> createTestQuestionDTOs() {
        QuestionDTO q1 = createTestQuestionDTO(1L, 100L, 200L, "First answer");
        QuestionDTO q2 = createTestQuestionDTO(2L, 101L, 201L, "Second answer");
        QuestionDTO q3 = createTestQuestionDTO(3L, 102L, 202L, "Third answer");
        
        return Arrays.asList(q1, q2, q3);
    }
}
