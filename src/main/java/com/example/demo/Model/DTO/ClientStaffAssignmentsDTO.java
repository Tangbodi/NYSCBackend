package com.example.demo.Model.DTO;

import com.example.demo.Annotation.ValidUserId;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientStaffAssignmentsDTO {

    @NotNull
    @ValidUserId
    private String clientId;
    @NotNull
    @ValidUserId
    private String staffId;

}
