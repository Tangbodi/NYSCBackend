package com.example.demo.Model.VO;

import com.example.demo.Annotation.ValidUsername;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;

@Data

public class ClientsInfoVO {
    private String clientId;
    private String firstName;

    private String lastName;

    private String middleName;

    private String dateOfBirth;

    private String gender;

    private String status;

    private String address;

    private String city;

    private String state;

    private String zipCode;
    private String notes;
    private String createdAt;
    private String modifiedAt;
}
