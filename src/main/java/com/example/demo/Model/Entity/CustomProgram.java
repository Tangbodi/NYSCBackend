package com.example.demo.Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "custom_programs")
public class CustomProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id", nullable = false)
    private Long id;

    @Size(max = 31)
    @NotNull
    @Column(name = "library", nullable = false, length = 127)
    private String library;

    @Size(max = 31)
    @NotNull
    @Column(name = "domain", nullable = false, length = 127)
    private String domain;

    @Size(max = 31)
    @NotNull
    @Column(name = "program_name", nullable = false, length = 127)
    private String programName;

    @Size(max = 255)
    @NotNull
    @Column(name = "program_goal", nullable = false, length = 511)
    private String programGoal;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;
}
