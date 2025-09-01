package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "proposal_question_answer")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @Column(nullable = false)
    private Long proposalId;

    @Column(nullable = false)
    private Long questionId;

    @Column(nullable = false, columnDefinition = "text")
    private String answer;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
