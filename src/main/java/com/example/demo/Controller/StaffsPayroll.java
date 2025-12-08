package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.StaffsPayrollDTO;
import com.example.demo.Model.VO.StaffsPayrollVO;
import com.example.demo.Service.StaffsPayroll.StaffsPayrollService;
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
@RequestMapping("/staffs")
public class StaffsPayroll {
    private static final Logger logger = LoggerFactory.getLogger(StaffsPayroll.class);
    @Autowired
    private StaffsPayrollService staffsPayrollService;

    @GetMapping("/payroll")
    public ResponseEntity<ApiResponse> GetStaffsPayroll(HttpServletRequest request) {
        ApiResponse apiResponse;

        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            logger.info("No staffId in session. Access denied");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }

        try {
            StaffsPayrollVO staffsPayrollVO = staffsPayrollService.GetStaffsPayroll(staffId);

            if (staffsPayrollVO == null) {
                logger.info("No existing payroll found for staffId: {}", staffId);
                apiResponse = ApiResponse.error(ReturnCode.RC404.getCode(), "No payroll record found");
            } else {
                apiResponse = ApiResponse.success(staffsPayrollVO);
            }

        } catch (Exception e) {
            logger.error("Failed to get StaffsPayroll: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Failed to get payroll information");
        }

        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
        @PostMapping("/payroll/update")
    public ResponseEntity UpdateStaffsPayroll(@RequestBody StaffsPayrollDTO staffsPayrollDTO, HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            logger.info("No staffId in session. Access denied");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }else{
            try{
                staffsPayrollDTO.setStaffId(staffId);
                staffsPayrollService.UpdateStaffsPayroll(staffsPayrollDTO);
                apiResponse = ApiResponse.success("Staffs Payroll updated successfully");
            }catch (Exception e) {
                logger.error("Failed to update StaffsPayroll", e.getMessage(), e);
                apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
            }
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
