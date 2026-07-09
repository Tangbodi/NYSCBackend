package com.example.demo.Model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
public class ServiceLinesDTO implements Serializable {

    @NotBlank(message = "Billing code is required.")
    @Length(min = 1, max = 15, message = "Billing code length not eligible.")
    private String billingCode;

    @NotBlank(message = "Rate per unit is required.")
    @Length(min = 1, max = 15, message = "Rate per unit length not eligible.")
    private String ratePerUnit;

    @NotBlank(message = "Unit type is required.")
    @Length(min = 1, max = 15, message = "Unit type length not eligible.")
    private String unitType;

    @NotBlank(message = "Service is required.")
    @Length(min = 1, max = 127, message = "Service length not eligible.")
    private String service;

    @NotBlank(message = "Description is required.")
    @Length(min = 1, max = 127, message = "Description length not eligible.")
    private String description;

    @Length(max = 1, message = "Inactive flag length not eligible.")
    private String inactive;

    @Length(max = 15, message = "Start date length not eligible.")
    private String startDate;

    @Length(max = 15, message = "End date length not eligible.")
    private String endDate;
}
