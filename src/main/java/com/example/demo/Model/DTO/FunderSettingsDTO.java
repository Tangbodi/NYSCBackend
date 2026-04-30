package com.example.demo.Model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
public class FunderSettingsDTO implements Serializable {

    @NotBlank(message = "Funder type is required.")
    @Length(min = 1, max = 15, message = "Funder type length not eligible.")
    private String funderType;

    @NotBlank(message = "Funder name is required.")
    @Length(min = 1, max = 31, message = "Funder name length not eligible.")
    private String funderName;

    @NotBlank(message = "Address is required.")
    @Length(min = 1, max = 63, message = "Address length not eligible.")
    private String address;

    @NotBlank(message = "Coverage type is required.")
    @Length(min = 1, max = 31, message = "Coverage type length not eligible.")
    private String coverageType;

    @Length(max = 15, message = "Vendor ID length not eligible.")
    private String vendorId;

    @Length(max = 15, message = "Phone length not eligible.")
    private String phone;

    @Length(max = 63, message = "Email length not eligible.")
    private String email;

    @Length(max = 15, message = "Fax length not eligible.")
    private String fax;

    @Length(max = 63, message = "Default billing provider length not eligible.")
    private String defaultBillingProvider;
}
