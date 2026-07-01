package com.example.demo.Model.VO;

import lombok.Data;

@Data
public class DonationVO {
    private String donationId;
    private String donationDate;
    private String donor;
    private String amount;
    private String donationType;
    private String note;
    private String createdAt;
    private String updatedAt;
}
