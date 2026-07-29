package com.example.demo.Model.VO;

import lombok.Data;

@Data
public class GeocodingAddressVO {
    private Double lat;
    private Double lng;
    private String source;
    private String matchedAddress;
}
