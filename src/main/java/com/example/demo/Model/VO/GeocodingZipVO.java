package com.example.demo.Model.VO;

import lombok.Data;

@Data
public class GeocodingZipVO {
    private String zip;
    private Double lat;
    private Double lng;
    private String city;
    private String state;
    private String source;
}
