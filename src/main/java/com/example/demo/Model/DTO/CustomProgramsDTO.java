package com.example.demo.Model.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class CustomProgramsDTO implements Serializable {

    @NotBlank(message = "Library is required.")
    @Length(min = 1, max = 31, message = "Library length not eligible.")
    private String library;

    @NotBlank(message = "Domain is required.")
    @Length(min = 1, max = 31, message = "Domain length not eligible.")
    private String domain;

    @NotBlank(message = "Program name is required.")
    @Length(min = 1, max = 63, message = "Program name length not eligible.")
    private String programName;

    @NotBlank(message = "Program goal is required.")
    @Length(min = 1, max = 511, message = "Program goal length not eligible.")
    private String programGoal;

    @Length(max = 511, message = "Objective one length not eligible.")
    private String objectiveOne;

    @Length(max = 511, message = "Objective two length not eligible.")
    private String objectiveTwo;

    @Length(max = 511, message = "Objective three length not eligible.")
    private String objectiveThree;

    @Length(max = 511, message = "Exercise length not eligible.")
    private String exercise;

    @Length(max = 511, message = "Generalization length not eligible.")
    private String generalization;

    @Length(max = 511, message = "Error correction length not eligible.")
    private String errorCorrection;

    @Length(max = 511, message = "Supplies length not eligible.")
    private String supplies;

    @Length(max = 511, message = "Teaching strategies length not eligible.")
    private String teachingStrategies;

    @Length(max = 511, message = "Troubleshooting length not eligible.")
    private String troubleshooting;

    @Length(max = 511, message = "Helpful hints length not eligible.")
    private String helpfulHints;

    @Valid
    private List<ProgramTargetDTO> targets = new ArrayList<>();
}
