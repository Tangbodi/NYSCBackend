package com.example.demo.Service.Geocoding;

import com.example.demo.Model.VO.GeocodingAddressVO;
import com.example.demo.Model.VO.GeocodingZipVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("unchecked")
public class GeocodingService {
    private static final Logger logger = LoggerFactory.getLogger(GeocodingService.class);
    private static final String CENSUS_URL = "https://geocoding.geo.census.gov/geocoder/locations/onelineaddress";
    private static final String ZIPPOPOTAM_URL = "https://api.zippopotam.us/us/";

    @Autowired
    private RestTemplate restTemplate;

    public GeocodingAddressVO geocodeAddress(String address) {
        logger.info("Geocoding address: {}", address);
        try {
            String url = UriComponentsBuilder.fromHttpUrl(CENSUS_URL)
                    .queryParam("address", address)
                    .queryParam("benchmark", "Public_AR_Current")
                    .queryParam("format", "json")
                    .build().toUriString();

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response == null) return null;

            Map<String, Object> result = (Map<String, Object>) response.get("result");
            if (result == null) return null;

            List<Map<String, Object>> matches = (List<Map<String, Object>>) result.get("addressMatches");
            if (matches == null || matches.isEmpty()) return null;

            Map<String, Object> match = matches.get(0);
            Map<String, Object> coords = (Map<String, Object>) match.get("coordinates");

            GeocodingAddressVO vo = new GeocodingAddressVO();
            vo.setLng(((Number) coords.get("x")).doubleValue());
            vo.setLat(((Number) coords.get("y")).doubleValue());
            vo.setMatchedAddress((String) match.get("matchedAddress"));
            vo.setSource("census");
            return vo;
        } catch (Exception e) {
            logger.error("Address geocoding failed: {}", e.getMessage(), e);
            return null;
        }
    }

    public GeocodingZipVO geocodeZip(String zip) {
        logger.info("Geocoding ZIP: {}", zip);
        try {
            Map<String, Object> response = restTemplate.getForObject(ZIPPOPOTAM_URL + zip, Map.class);
            if (response == null) return null;

            List<Map<String, Object>> places = (List<Map<String, Object>>) response.get("places");
            if (places == null || places.isEmpty()) return null;

            Map<String, Object> place = places.get(0);
            GeocodingZipVO vo = new GeocodingZipVO();
            vo.setZip((String) response.get("post code"));
            vo.setLat(Double.parseDouble((String) place.get("latitude")));
            vo.setLng(Double.parseDouble((String) place.get("longitude")));
            vo.setCity((String) place.get("place name"));
            vo.setState((String) place.get("state abbreviation"));
            vo.setSource("zippopotam");
            return vo;
        } catch (Exception e) {
            logger.error("ZIP geocoding failed: {}", e.getMessage(), e);
            return null;
        }
    }
}
