package com.example.demo.Model.VO;

import lombok.Data;

import java.util.List;

@Data
public class ClientProgramAssignmentsVO {
    private String clientId;
    private List<AssignedProgramVO> programs;
}
