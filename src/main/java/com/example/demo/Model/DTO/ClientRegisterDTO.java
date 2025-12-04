package com.example.demo.Model.DTO;

import com.example.demo.Annotation.ValidPassword;
import com.example.demo.Annotation.ValidPhone;
import com.example.demo.Annotation.ValidUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.Instant;

@Data
public class ClientRegisterDTO implements Serializable {
    private Long clientId;
    @NotBlank(message = "First name is required")
    @Length(min = 1, max = 31, message = "First name length not eligible")
    @ValidUsername
    private String clientFirstName;
    @NotBlank(message = "Last name is required")
    @Length(min = 1, max = 31, message = "Last name length not eligible")
    @ValidUsername
    private String clientLastName;
    @Length(min = 1, max = 31, message = "Middle name length not eligible")
    @ValidUsername
    private String clientMiddleName;
    @NotBlank(message = "Date of birth is required")
    private String dateOfBirth;
    @NotBlank(message = "Gender is required")
    @Length(min = 1, max = 7, message = "Gender length not eligible")
    private String gender;
    @NotBlank(message = "Status is required")
    @Length(min = 1, max = 2, message = "Status length not eligible")
    private String status;
    @NotBlank(message = "Address is required")
    @Length(min = 1, max = 63, message = "Address length not eligible")
    private String address;
    @NotBlank(message = "City is required")
    @Length(min = 1, max = 15, message = "City length not eligible")
    private String city;
    @NotBlank(message = "State is required")
    @Length(min = 1, max = 7, message = "State length not eligible")
    private String state;
    @NotBlank(message = "Zip code is required")
    @Length(min = 1, max = 15, message = "Zip code length not eligible")
    private String zipCode;

    @Length(min = 1, max = 63, message = "Notes length not eligible")
    private String notes;
    private Instant createdAt;
    private Instant modifiedAt;
}
