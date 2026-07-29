package com.example.demo.Model.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;


@Data
public class ClientFundersDTO {
    @NotBlank(message = "clientId is required")
    private String clientId;
    @NotBlank(message = "Funder ID is required.")
    private String funderId;
    @NotBlank(message = "Insurance ID is required.")
    private String insuranceId;

    @NotBlank(message = "Relationship is required.")
    @Length(max = 63, message = "Relationship length not eligible.")
    private String relationship;

    @Length(max = 15, message = "Start date length not eligible.")
    private String startDate;

    @Length(max = 15, message = "End date length not eligible.")
    private String endDate;

    @NotBlank(message = "First name is required.")
    @Length(max = 31, message = "First name length not eligible.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Length(max = 31, message = "Last name length not eligible.")
    private String lastName;

    @NotBlank(message = "Coverage type is required.")
    @Pattern(regexp = "Primary|Secondary", message = "Coverage type must be 'Primary' or 'Secondary'.")
    private String coverageType;

}
