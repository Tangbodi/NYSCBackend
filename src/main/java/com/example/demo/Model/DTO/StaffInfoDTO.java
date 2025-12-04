package com.example.demo.Model.DTO;

import com.example.demo.Annotation.ValidPhone;
import com.example.demo.Annotation.ValidUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
public class StaffInfoDTO implements Serializable {
    private Long staffId;
    @NotBlank(message = "Username is required")
    @Length(min = 1, max = 31, message = "Username length not eligible")
    @ValidUsername
    private String username;
    @NotBlank(message = "Email is required")
    @Length(max = 63, message = "Email address length not eligible")
    @Email(message = "Invalid email address")
    private String email;
    @NotBlank(message = "Staff first name is required")
    @Length(min = 1, max = 31, message = "Staff first name length not eligible")
    @ValidUsername
    private String firstName;
    @NotBlank(message = "Staff last name is required")
    @Length(min = 1, max = 31, message = "Staff last name length not eligible")
    @ValidUsername
    private String lastName;
    @ValidUsername
    @Length(min = 1, max = 31, message = "Staff middle name length not eligible")
    private String middleName;
    private String employeeType;
    private String supervisor;
    @ValidPhone
    private String phone;
    private String title;
    private String status;

}
