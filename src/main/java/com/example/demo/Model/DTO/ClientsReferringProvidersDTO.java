package com.example.demo.Model.DTO;

import com.example.demo.Annotation.ValidPhone;
import com.example.demo.Annotation.ValidUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
public class ClientsReferringProvidersDTO implements Serializable {
    private String clientId;
    @NotBlank(message = "First name is required.")
    @Length(min = 1, max = 31, message = "First name length not eligible.")
    @ValidUsername
    private String providerFirstName;
    @NotBlank(message = "Last name is required.")
    @Length(min = 1, max = 31, message = "Last name length not eligible.")
    @ValidUsername
    private String providerLastName;
    @Length(min = 1, max = 31, message = "Middle name length not eligible.")
    @ValidUsername
    private String providerMiddleName;
    @Length(min = 1, max = 15, message = "Middle name length not eligible.")
    private String npiNumber;
    @NotBlank(message = "Status is required.")
    @Length(min = 1, max = 2, message = "Status length not eligible.")
    private String isActive;
    @Length(min = 1, max = 31, message = "Last name length not eligible.")
    private String taxonomyCode;
    @ValidPhone
    private String phone;
    @Length(min = 1, max = 15, message = "Middle name length not eligible.")
    private String fax;
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

}
