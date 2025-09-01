package com.example.demo.dto;

import com.example.demo.util.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.json.ObjectContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class QuestionDTOTest {

    @Autowired
    private JacksonTester<QuestionDTO> json;

    @Test
    void shouldSerializeToJson() throws IOException {
        // Given
        QuestionDTO questionDTO = TestDataUtil.createTestQuestionDTO();
        
        // Configure Jackson to handle Java 8 date/time types
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        JacksonTester.initFields(this, objectMapper);

        // When
        JsonContent<QuestionDTO> jsonContent = json.write(questionDTO);

        // Then
        assertThat(jsonContent).hasJsonPathNumberValue("$.answerId");
        assertThat(jsonContent).extractingJsonPathNumberValue("$.proposalId")
                .isEqualTo(100);
        assertThat(jsonContent).extractingJsonPathNumberValue("$.questionId")
                .isEqualTo(200);
        assertThat(jsonContent).extractingJsonPathStringValue("$.answer")
                .isEqualTo("Sample answer");
    }

    @Test
    void shouldDeserializeFromJson() throws IOException {
        // Given
        String jsonContent = """
        {
            "answerId": 1,
            "proposalId": 100,
            "questionId": 200,
            "answer": "Test answer",
            "createdAt": "2025-01-01T12:00:00",
            "updatedAt": "2025-01-01T12:00:00"
        }
        """;

        // Configure Jackson to handle Java 8 date/time types
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        JacksonTester.initFields(this, objectMapper);

        // When
        ObjectContent<QuestionDTO> questionDTO = json.parse(jsonContent);

        // Then
        assertThat(questionDTO.getObject())
                .hasFieldOrPropertyWithValue("answerId", 1L)
                .hasFieldOrPropertyWithValue("proposalId", 100L)
                .hasFieldOrPropertyWithValue("questionId", 200L)
                .hasFieldOrPropertyWithValue("answer", "Test answer");
    }
}
