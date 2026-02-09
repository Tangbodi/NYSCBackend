package com.example.demo.Model.VO;

import com.example.demo.Annotation.ValidPhone;
import com.example.demo.Annotation.ValidUsername;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ClientsReferringProvidersVO {
    private String providerId;
    private String clientId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String npiNumber;
    private String isActive;
    private String taxonomyCode;
    private String phone;
    private String fax;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String notes;
    private String createdAt;
    private String modifiedAt;

}
