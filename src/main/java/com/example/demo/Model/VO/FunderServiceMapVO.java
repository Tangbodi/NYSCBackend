package com.example.demo.Model.VO;

import lombok.Data;

import java.util.List;

@Data
public class FunderServiceMapVO {
    private String funderId;
    private List<AssignedServiceVO> services;
}
