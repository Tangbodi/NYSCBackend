package com.example.demo.Model.DTO;

import com.example.demo.Annotation.ValidPhone;
import com.example.demo.Annotation.ValidUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.Instant;

@Data
public class ClientsContactsDTO implements Serializable {

    private String clientId;
    @NotBlank(message = "First name is required.")
    @Length(min = 1, max = 31, message = "First name length not eligible.")
    @ValidUsername
    private String firstName;
    @NotBlank(message = "Last name is required.")
    @Length(min = 1, max = 31, message = "Last name length not eligible.")
    @ValidUsername
    private String lastName;
    @Length(min = 1, max = 31, message = "Middle name length not eligible.")
    @ValidUsername
    private String middleName;
    @NotBlank(message = "Email is required.")
    @Length(max = 63, message = "Email address length not eligible.")
    @Email(message = "Invalid email address.")
    private String email;
    @ValidPhone
    private String phone;
    @NotBlank(message = "Address is required.")
    @Length(min = 1, max = 63, message = "Address length not eligible.")
    private String address;
    @Length(min = 1, max = 15, message = "City length not eligible.")
    private String city;
    @Length(min = 1, max = 7, message = "State length not eligible.")
    private String state;
    @Length(min = 1, max = 15, message = "Zip code length not eligible.")
    private String zipCode;
    @Length(min = 1, max = 63, message = "Notes length not eligible.")
    private String notes;
    @NotBlank
    @NotBlank(message = "Status is required.")
    @Length(min = 1, max = 2, message = "Status length not eligible.")
    private String isPrimary;
    @NotBlank
    @Length(min = 1, max = 15, message = "Relationship type length not eligible")
    private String relationshipType;

}
