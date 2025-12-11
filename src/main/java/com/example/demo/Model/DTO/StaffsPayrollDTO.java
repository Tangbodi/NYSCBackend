package com.example.demo.Model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
@Data
public class StaffsPayrollDTO implements Serializable {
    private Long staffId;
    @NotBlank
    private String hourlyRate;
    @NotBlank
    private String payCode;
    private String effectiveStartDate;
    private String effectiveEndDate;
    @Length(min = 1, max = 63, message = "Notes length not eligible.")
    private String notes;

}
