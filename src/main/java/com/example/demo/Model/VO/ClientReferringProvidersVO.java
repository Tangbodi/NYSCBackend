package com.example.demo.Model.VO;

import lombok.Data;

@Data
public class ClientReferringProvidersVO {
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
    private String createdAt;
    private String modifiedAt;

}
