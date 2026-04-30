package com.example.demo.Model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
public class ProgramTargetDTO implements Serializable {

    @NotBlank(message = "Target name is required.")
    @Length(min = 1, max = 127, message = "Target name length not eligible.")
    private String targetName;

    @Length(max = 511, message = "Objective length not eligible.")
    private String objective;

    @Length(max = 31, message = "Status length not eligible.")
    private String status;

    @Length(max = 15, message = "Date opened length not eligible.")
    private String dateOpened;

    @Length(max = 15, message = "Date mastered length not eligible.")
    private String dateMastered;

}
