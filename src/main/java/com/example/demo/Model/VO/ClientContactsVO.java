package com.example.demo.Model.VO;

import lombok.Data;

@Data
public class ClientContactsVO {
    private String clientId;
    private String firstName;
    private String lastName;
    private String isPrimary;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String relationshipType;
    private String createdAt;
    private String modifiedAt;
}
