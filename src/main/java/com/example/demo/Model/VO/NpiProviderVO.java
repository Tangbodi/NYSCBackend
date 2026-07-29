package com.example.demo.Model.VO;

import lombok.Data;

@Data
public class NpiProviderVO {
    private String npi;
    private String name;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phone;
    private String taxonomy;
    private String taxonomyCode;
    private String taxonomyDescription;
    private String address;
    private String city;
    private String state;
    private String postalCode;
}
