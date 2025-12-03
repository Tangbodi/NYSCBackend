package com.example.demo.Model.DTO;

import lombok.Data;

import java.time.Instant;

@Data
public class UserInfoDTO {
    private Long userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String middleName;
    private String employeeType;
    private String supervisor;
    private String phone;
    private String title;
    private Boolean status;
    private Instant createdAt;
    private Instant modifiedAt;
}
