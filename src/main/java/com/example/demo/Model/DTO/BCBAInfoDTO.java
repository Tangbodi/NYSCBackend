package com.example.demo.Model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
public class BCBAInfoDTO implements Serializable {

    @NotBlank(message = "Staff ID is required.")
    private String staffId;

    @Length(max = 31, message = "NPI number length not eligible.")
    private String npiNumber;

    @Length(max = 31, message = "Medicaid ID length not eligible.")
    private String medicaidId;
}
