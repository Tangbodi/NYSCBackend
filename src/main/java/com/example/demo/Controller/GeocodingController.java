package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.VO.GeocodingAddressVO;
import com.example.demo.Model.VO.GeocodingZipVO;
import com.example.demo.Service.Geocoding.GeocodingService;
import com.example.demo.Util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/geocoding")
public class GeocodingController {
    private static final Logger logger = LoggerFactory.getLogger(GeocodingController.class);

    @Autowired
    private GeocodingService geocodingService;

    @GetMapping("/address")
    public ResponseEntity<ApiResponse> geocodeAddress(
            @RequestParam(value = "address") String address,
            HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        if (address == null || address.isBlank()) {
            apiResponse = ApiResponse.error(ReturnCode.RC400.getCode(), "address is required.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            GeocodingAddressVO vo = geocodingService.geocodeAddress(address);
            if (vo == null) {
                apiResponse = ApiResponse.error(ReturnCode.RC404.getCode(), "No coordinates found for the given address.");
            } else {
                apiResponse = ApiResponse.success(vo);
            }
        } catch (Exception e) {
            logger.error("Address geocoding error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/zip")
    public ResponseEntity<ApiResponse> geocodeZip(
            @RequestParam(value = "zip") String zip,
            HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        if (zip == null || !zip.matches("\\d{5}")) {
            apiResponse = ApiResponse.error(ReturnCode.RC400.getCode(), "zip must be exactly 5 digits.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            GeocodingZipVO vo = geocodingService.geocodeZip(zip);
            if (vo == null) {
                apiResponse = ApiResponse.error(ReturnCode.RC404.getCode(), "No coordinates found for ZIP: " + zip);
            } else {
                apiResponse = ApiResponse.success(vo);
            }
        } catch (Exception e) {
            logger.error("ZIP geocoding error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
