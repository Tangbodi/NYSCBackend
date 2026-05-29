package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.BCBAInfoDTO;
import com.example.demo.Model.VO.BCBAInfoVO;
import com.example.demo.Service.BCBAInfo.BCBAInfoService;
import com.example.demo.Util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/bcba-info")
public class BCBAInfoController {
    private static final Logger logger = LoggerFactory.getLogger(BCBAInfoController.class);

    @Autowired
    private BCBAInfoService bcbaInfoService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> GetBCBAInfo(
            @RequestParam("staff") String staffId,
            HttpServletRequest request) {
        ApiResponse apiResponse;
        Long sessionStaffId = (Long) request.getSession().getAttribute("staffId");
        if (sessionStaffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            BCBAInfoVO vo = bcbaInfoService.GetBCBAInfo(staffId);
            if (vo != null) {
                apiResponse = ApiResponse.success(vo);
            } else {
                apiResponse = ApiResponse.error(ReturnCode.RC404.getCode(), "No BCBA record found.");
            }
        } catch (Exception e) {
            logger.error("Failed to get BCBA info: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> UpdateBCBAInfo(
            @Validated @RequestBody BCBAInfoDTO dto,
            HttpServletRequest request) {
        ApiResponse apiResponse;
        Long sessionStaffId = (Long) request.getSession().getAttribute("staffId");
        if (sessionStaffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            boolean updated = bcbaInfoService.UpdateBCBAInfo(dto);
            if (updated) {
                apiResponse = ApiResponse.success("BCBA info updated successfully.");
            } else {
                apiResponse = ApiResponse.error(ReturnCode.RC404.getCode(), "No BCBA record found for this staff.");
            }
        } catch (Exception e) {
            logger.error("Failed to update BCBA info: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
