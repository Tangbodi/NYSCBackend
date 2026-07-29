package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.VO.MedicaidRemittanceWeeklyPaidVO;
import com.example.demo.Service.MedicaidRemittance.MedicaidRemittanceService;
import com.example.demo.Util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicaid-remittance")
public class MedicaidRemittanceController {
    private static final Logger logger = LoggerFactory.getLogger(MedicaidRemittanceController.class);

    @Autowired
    private MedicaidRemittanceService medicaidRemittanceService;

    @GetMapping("/weekly-paid")
    public ResponseEntity<ApiResponse> getWeeklyPaid(
            @RequestParam(value = "year") String year,
            HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        if (year == null || year.isBlank()) {
            apiResponse = ApiResponse.error(ReturnCode.RC400.getCode(), "year is required.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            List<MedicaidRemittanceWeeklyPaidVO> data = medicaidRemittanceService.getWeeklyPaid(Integer.parseInt(year));
            apiResponse = ApiResponse.success(data);
        } catch (NumberFormatException e) {
            apiResponse = ApiResponse.error(ReturnCode.RC400.getCode(), "year must be a valid number.");
        } catch (Exception e) {
            logger.error("Failed to get weekly paid: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
