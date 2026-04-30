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
public class CustomPrograms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id", nullable = false)
    private Long id;

    @Size(max = 31)
    @NotNull
    @Column(name = "library", nullable = false, length = 31)
    private String library;

    @Size(max = 31)
    @NotNull
    @Column(name = "domain", nullable = false, length = 31)
    private String domain;

    @Size(max = 63)
    @NotNull
    @Column(name = "program_name", nullable = false, length = 63)
    private String programName;

    @Size(max = 511)
    @NotNull
    @Column(name = "program_goal", nullable = false, length = 511)
    private String programGoal;

    @Size(max = 511)
    @Column(name = "objective_one", length = 511)
    private String objectiveOne;

    @Size(max = 511)
    @Column(name = "objective_two", length = 511)
    private String objectiveTwo;

    @Size(max = 511)
    @Column(name = "objective_three", length = 511)
    private String objectiveThree;

    @Size(max = 511)
    @Column(name = "exercise", length = 511)
    private String exercise;

    @Size(max = 511)
    @Column(name = "generalization", length = 511)
    private String generalization;

    @Size(max = 511)
    @Column(name = "error_correction", length = 511)
    private String errorCorrection;

    @Size(max = 511)
    @Column(name = "supplies", length = 511)
    private String supplies;

    @Size(max = 511)
    @Column(name = "teaching_strategies", length = 511)
    private String teachingStrategies;

    @Size(max = 511)
    @Column(name = "troubleshooting", length = 511)
    private String troubleshooting;

    @Size(max = 511)
    @Column(name = "helpful_hints", length = 511)
    private String helpfulHints;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;
}
