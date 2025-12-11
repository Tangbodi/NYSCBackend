package com.example.demo.Model.DTO;

import com.example.demo.Annotation.ValidPhone;
import com.example.demo.Annotation.ValidUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
public class StaffsInfoDTO implements Serializable {
    private Long staffId;
    @NotBlank(message = "Username is required.")
    @Length(min = 1, max = 31, message = "Username length not eligible.")
    @ValidUsername
    private String username;
    @NotBlank(message = "Email is required.")
    @Length(max = 63, message = "Email address length not eligible.")
    @Email(message = "Invalid email address.")
    private String email;
    @NotBlank(message = "First name is required.")
    @Length(min = 1, max = 31, message = "First name length not eligible.")
    @ValidUsername
    private String firstName;
    @NotBlank(message = "Last name is required.")
    @Length(min = 1, max = 31, message = "Last name length not eligible.")
    @ValidUsername
    private String lastName;
    @ValidUsername
    @Length(min = 1, max = 31, message = "Middle name length not eligible.")
    private String middleName;
    @Length(min = 1, max = 15, message = "Employee type length not eligible.")
    private String employeeType;
    @Length(min = 1, max = 31, message = "Supervisor length not eligible.")
    private String supervisor;
    @ValidPhone
    private String phone;
    @Length(min = 1, max = 7, message = "Title length not eligible.")
    private String title;
    @NotBlank(message = "Status is required.")
    @Length(min = 1, max = 2, message = "Status length not eligible.")
    private String status;

}
