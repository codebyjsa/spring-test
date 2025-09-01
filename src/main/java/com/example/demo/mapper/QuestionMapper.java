package com.example.demo.mapper;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    QuestionDTO toDTO(Question question);

    Question toEntity(QuestionDTO questionDTO);

    void updateEntity(QuestionDTO questionDTO, @MappingTarget Question question);
}
