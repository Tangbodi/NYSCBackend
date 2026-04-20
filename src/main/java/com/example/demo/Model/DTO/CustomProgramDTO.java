package com.example.demo.Model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
public class CustomProgramDTO implements Serializable {

    @NotBlank(message = "Library is required.")
    @Length(min = 1, max = 31, message = "Library length not eligible.")
    private String library;

    @NotBlank(message = "Domain is required.")
    @Length(min = 1, max = 31, message = "Domain length not eligible.")
    private String domain;

    @NotBlank(message = "Program name is required.")
    @Length(min = 1, max = 31, message = "Program name length not eligible.")
    private String programName;

    @NotBlank(message = "Program goal is required.")
    @Length(min = 1, max = 255, message = "Program goal length not eligible.")
    private String programGoal;
}
