package com.example.demo.Model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientStaffAssignmentsDTO {

    @NotBlank(message = "clientId is required.")
    private String clientId;
    @NotBlank(message = "staffId is required.")
    private String staffId;

}
