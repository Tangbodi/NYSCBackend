package com.example.demo.Model.DTO;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
@Data
public class StaffsLicensesDTO implements Serializable {
    private Long staffId;
    @Length(min = 1, max = 31, message = "License name length not eligible.")
    private String licenseName;
    @Length(min = 1, max = 31, message = "License number length not eligible.")
    private String licenseNumber;
    @Length(min = 1, max = 7, message = "License state length not eligible.")
    private String licenseState;
    @Length(min = 1, max = 15, message = "Issue date length not eligible.")
    private String issueDate;
    @Length(min = 1, max = 15, message = "Expired date length not eligible.")
    private String expiredDate;
    @Length(min = 1, max = 663, message = "Notes length not eligible.")
    private String notes;

}
