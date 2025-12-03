package com.example.demo.Model.DTO;

import com.example.demo.Annotation.ValidPassword;
import com.example.demo.Annotation.ValidPhone;
import com.example.demo.Annotation.ValidUsername;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import java.io.Serializable;
import java.time.Instant;


@Data
public class StaffRegisterDTO implements Serializable {
    private Long staffId;
    @NotBlank(message = "Username is required")
    @Length(min = 1, max = 30, message = "Username length not eligible")
    @ValidUsername
    private String username;
    @NotBlank(message = "Email is required")
    @Length(max = 60, message = "Email address length not eligible")
    @Email(message = "Invalid email address")
    private String email;
    @NotBlank(message = "Password is required")
    @ValidPassword
    private String password;
    @NotBlank(message = "Confirm password is required")
    @ValidPassword
    private String confirmPassword;
    @NotBlank(message = "Username is required")
    @Length(min = 1, max = 30, message = "Username length not eligible")
    @ValidUsername
    private String firstName;
    @NotBlank(message = "Username is required")
    @Length(min = 1, max = 30, message = "Username length not eligible")
    @ValidUsername
    private String lastName;
    private String middleName;
    private String employeeType;
    private String supervisor;
    @ValidPhone
    private String phone;
    private String title;
    private Integer status;
    private Instant createdAt;
    private Instant modifiedAt;
}
