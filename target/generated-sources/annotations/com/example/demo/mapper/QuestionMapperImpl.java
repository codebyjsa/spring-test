package com.example.demo.mapper;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.entity.Question;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-01T17:31:06+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Eclipse Adoptium)"
)
@Component
public class QuestionMapperImpl implements QuestionMapper {

    @Override
    public QuestionDTO toDTO(Question question) {
        if ( question == null ) {
            return null;
        }

        QuestionDTO.QuestionDTOBuilder questionDTO = QuestionDTO.builder();

        questionDTO.answerId( question.getAnswerId() );
        questionDTO.proposalId( question.getProposalId() );
        questionDTO.questionId( question.getQuestionId() );
        questionDTO.answer( question.getAnswer() );
        questionDTO.createdAt( question.getCreatedAt() );
        questionDTO.updatedAt( question.getUpdatedAt() );

        return questionDTO.build();
    }

    @Override
    public Question toEntity(QuestionDTO questionDTO) {
        if ( questionDTO == null ) {
            return null;
        }

        Question question = new Question();

        question.setAnswerId( questionDTO.getAnswerId() );
        question.setProposalId( questionDTO.getProposalId() );
        question.setQuestionId( questionDTO.getQuestionId() );
        question.setAnswer( questionDTO.getAnswer() );
        question.setCreatedAt( questionDTO.getCreatedAt() );
        question.setUpdatedAt( questionDTO.getUpdatedAt() );

        return question;
    }

    @Override
    public void updateEntity(QuestionDTO questionDTO, Question question) {
        if ( questionDTO == null ) {
            return;
        }

        question.setAnswerId( questionDTO.getAnswerId() );
        question.setProposalId( questionDTO.getProposalId() );
        question.setQuestionId( questionDTO.getQuestionId() );
        question.setAnswer( questionDTO.getAnswer() );
        question.setCreatedAt( questionDTO.getCreatedAt() );
        question.setUpdatedAt( questionDTO.getUpdatedAt() );
    }
}
