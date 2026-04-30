package com.example.demo.Model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class FunderServiceMapDTO implements Serializable {

    @NotBlank(message = "Funder ID is required.")
    private String funderId;

    @NotBlank(message = "Service ID is required.")
    private String serviceId;
}
