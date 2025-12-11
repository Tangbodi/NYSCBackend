package com.example.demo.Model.VO;

import com.example.demo.Annotation.ValidPhone;
import com.example.demo.Annotation.ValidUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
@Data
public class ClientsContactsVO {
    private String clientId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String isPrimary;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String notes;
    private String relationshipType;
}
