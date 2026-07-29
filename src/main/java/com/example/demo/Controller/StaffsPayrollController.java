package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.StaffsPayrollDTO;
import com.example.demo.Model.VO.StaffsPayrollVO;
import com.example.demo.Service.StaffsLogin.StaffsLoginService;
import com.example.demo.Service.StaffsPayroll.StaffsPayrollService;
import com.example.demo.Util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Validated
@RequestMapping("/staffs/payroll")
public class StaffsPayrollController {
    private static final Logger logger = LoggerFactory.getLogger(StaffsPayrollController.class);
    @Autowired
    private StaffsPayrollService staffsPayrollService;
    @Autowired
    private StaffsLoginService staffsLoginService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> GetStaffsPayroll(@RequestParam(value = "staff") String staffId,
                                                        HttpServletRequest request) {
        ApiResponse apiResponse;

        Long sessionStaffId = (Long) request.getSession().getAttribute("staffId");
        if (sessionStaffId == null) {
            logger.info("No staffId in session. Access denied.");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }

        try {
            StaffsPayrollVO staffsPayrollVO = staffsPayrollService.GetStaffsPayroll(Long.valueOf(staffId));

            if (staffsPayrollVO == null) {
                logger.info("No existing payroll found for staffId: {}", staffId);
                apiResponse = ApiResponse.error(ReturnCode.RC404.getCode(), "No payroll record found.");
            } else {
                apiResponse = ApiResponse.success(staffsPayrollVO);
            }

        } catch (Exception e) {
            logger.error("Failed to get StaffsPayroll: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Failed to get payroll information.");
        }

        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }


    @PutMapping("/")
    public ResponseEntity<ApiResponse> UpdateStaffsPayroll(@Validated @RequestBody StaffsPayrollDTO staffsPayrollDTO,
                                                           HttpServletRequest request) {
        ApiResponse apiResponse;
        Long sessionStaffId = (Long) request.getSession().getAttribute("staffId");
        if (sessionStaffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        if (!staffsLoginService.CheckIsAdmin(sessionStaffId)) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "You aren't admin.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        if (staffsPayrollDTO.getStaffId() == null || staffsPayrollDTO.getStaffId().isBlank()) {
            apiResponse = ApiResponse.error(ReturnCode.RC400.getCode(), "staffId is required.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            staffsPayrollService.UpdateStaffsPayroll(staffsPayrollDTO);
            apiResponse = ApiResponse.success("Payroll updated successfully.");
        } catch (Exception e) {
            logger.error("Failed to update StaffsPayroll: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @DeleteMapping("/")
    public ResponseEntity<ApiResponse> DeleteStaffsPayroll(
            @RequestBody Map<String, String> body,
            HttpServletRequest request) {
        ApiResponse apiResponse;
        Long sessionStaffId = (Long) request.getSession().getAttribute("staffId");
        if (sessionStaffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            if (!staffsLoginService.CheckIsAdmin(sessionStaffId)) {
                apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "You aren't admin.");
            } else {
                staffsPayrollService.DeleteStaffsPayroll(body.get("staffId"));
                apiResponse = ApiResponse.success("Staff payroll deleted successfully.");
            }
        } catch (Exception e) {
            logger.error("Delete error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
