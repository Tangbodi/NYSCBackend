package com.example.demo.Model.DTO;

import com.example.demo.Annotation.ValidPassword;
import com.example.demo.Annotation.ValidUsername;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

@Data
public class StaffsLoginDTO implements Serializable {
    @NotBlank(message = "Username is required.")
    @Length(min = 1, max = 30, message = "Username length not eligible.")
    @ValidUsername(message = "User not found.")
    private String username;
    @NotBlank(message = "Password is required.")
    @Length(min =8, max = 30, message = "Password length not eligible.")
    @ValidPassword(message = "Username or password is incorrect.")
    private String password;
}
