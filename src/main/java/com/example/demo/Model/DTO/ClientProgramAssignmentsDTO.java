package com.example.demo.Model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class ClientProgramAssignmentsDTO implements Serializable {

    @NotBlank(message = "Client ID is required.")
    private String clientId;

    @NotBlank(message = "Program ID is required.")
    private String programId;
}
