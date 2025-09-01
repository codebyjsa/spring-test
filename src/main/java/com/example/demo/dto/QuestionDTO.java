package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private Long answerId;
    private Long proposalId;
    private Long questionId;
    private String answer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
