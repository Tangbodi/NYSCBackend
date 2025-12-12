package com.example.demo.Model.DTO;

import org.hibernate.validator.constraints.Length;

public class ClientsFundersDTO {

    private String clientId;
    @Length(min = 1, max = 31, message = "Payer name length not eligible.")
    private String payerName;
    @Length(min = 1, max = 31, message = "Plan name length not eligible.")
    private String planName;
    @Length(min = 1, max = 31, message = "Member ID length not eligible.")
    private String memberId;
    @Length(min = 1, max = 31, message = "Group number length not eligible.")
    private String groupNumber;
    @Length(min = 1, max = 31, message = "Relationship to client length not eligible.")
    private String relationshipToClient;
    @Length(min = 1, max = 31, message = "Policy holder name length not eligible.")
    private String policyHolderName;
    @Length(min = 1, max = 11, message = "Policy holder phone length not eligible.")
    private String policyHolderPhone;
    @Length(min = 1, max = 63, message = "Policy holder email length not eligible.")
    private String policyHolderEmail;
    @Length(min = 1, max = 63, message = "Policy holder address length not eligible.")
    private String policyHolderAddress;
    @Length(min = 1, max = 15, message = "Policy holder city length not eligible.")
    private String policyHolderCity;
    @Length(min = 1, max = 7, message = "Policy holder state length not eligible.")
    private String policyHolderState;
    @Length(min = 1, max = 15, message = "Policy holder zip code length not eligible.")
    private String policyHolderZipCode;
    @Length(min = 1, max = 63, message = "Coverage order length not eligible.")
    private String coverageOrder;
    @Length(min = 1, max = 15, message = "Effective start length not eligible.")
    private String effectiveStart;
    @Length(min = 1, max = 15, message = "Effective end length not eligible.")
    private String effectiveEnd;
    @Length(min = 1, max = 2, message = "Status length not eligible.")
    private String isActive;
    @Length(min = 1, max = 63, message = "Notes length not eligible.")
    private String notes;

}
