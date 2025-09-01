package com.example.demo.service.impl;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.entity.Question;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<QuestionDTO> getAllQuestions() {
        log.info("Fetching all questions");
        List<Question> questions = questionRepository.findAll();
        
        if (questions.isEmpty()) {
            throw new ResourceNotFoundException("No questions found");
        }
        
        return questions.stream()
                .map(questionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionDTO getQuestionById(Long id) {
        log.info("Fetching question with id: {}", id);
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
        return questionMapper.toDTO(question);
    }

    @Override
    public QuestionDTO createQuestion(QuestionDTO questionDTO) {
        log.info("Creating new question");
        Question question = questionMapper.toEntity(questionDTO);
        question.setCreatedAt(LocalDateTime.now());
        question.setUpdatedAt(LocalDateTime.now());
        Question savedQuestion = questionRepository.save(question);
        return questionMapper.toDTO(savedQuestion);
    }

    @Override
    public QuestionDTO updateQuestion(Long id, QuestionDTO questionDTO) {
        log.info("Updating question with id: {}", id);
        Question existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
        
        // Update fields from DTO
        existingQuestion.setProposalId(questionDTO.getProposalId());
        existingQuestion.setQuestionId(questionDTO.getQuestionId());
        existingQuestion.setAnswer(questionDTO.getAnswer());
        existingQuestion.setUpdatedAt(LocalDateTime.now());
        
        Question updatedQuestion = questionRepository.save(existingQuestion);
        return questionMapper.toDTO(updatedQuestion);
    }

    @Override
    public void deleteQuestion(Long id) {
        log.info("Deleting question with id: {}", id);
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
        questionRepository.delete(question);
    }
}
