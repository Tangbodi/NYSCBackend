package com.example.demo.Model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ClientFundersDTO {
    @NotBlank(message = "clientId is required")
    private String clientId;
    @NotBlank(message = "Funder ID is required.")
    private String funderId;
    @NotBlank(message = "Insurance ID is required.")
    private String insuranceId;

}
