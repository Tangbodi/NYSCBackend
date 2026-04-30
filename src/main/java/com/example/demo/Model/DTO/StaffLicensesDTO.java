package com.example.demo.Model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
@Data
public class StaffLicensesDTO implements Serializable {

    private String licenseId;
    @NotBlank(message = "staffId is required")
    private String staffId;
    @Length(min = 0, max = 31, message = "License name length not eligible.")
    private String licenseName;
    @Length(min = 0, max = 31, message = "License number length not eligible.")
    private String licenseNumber;
    @Length(min = 0, max = 7, message = "License state length not eligible.")
    private String licenseState;
    @Length(min = 0, max = 15, message = "Issue date length not eligible.")
    private String issueDate;
    @Length(min = 0, max = 15, message = "Expired date length not eligible.")
    private String expiredDate;
    @Length(min = 0, max = 63, message = "Notes length not eligible.")
    private String notes;

}
