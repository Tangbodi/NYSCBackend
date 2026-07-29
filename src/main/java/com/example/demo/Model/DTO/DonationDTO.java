package com.example.demo.Model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.Instant;

@Data
public class DonationDTO implements Serializable {

    private String donationId;

    @NotBlank(message = "Donation date is required.")
    @Length(max = 15, message = "Donation date length not eligible.")
    private String donationDate;

    @NotBlank(message = "Donor is required.")
    @Length(max = 512, message = "Donor length not eligible.")
    private String donor;

    @NotBlank(message = "Amount is required.")
    private String amount;

    @NotBlank(message = "Donation type is required.")
    @Length(max = 50, message = "Donation type length not eligible.")
    private String donationType;

    @Length(max = 512, message = "Note length not eligible.")
    private String note;

}
