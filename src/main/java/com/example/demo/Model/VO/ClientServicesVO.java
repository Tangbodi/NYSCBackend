package com.example.demo.Model.VO;

import lombok.Data;

import java.util.List;

@Data
public class ClientServicesVO {
    private String clientId;
    private List<ServiceLinesVO> services;
}
