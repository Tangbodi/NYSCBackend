package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.ClientsFundersDTO;
import com.example.demo.Model.DTO.StaffsLicensesDTO;
import com.example.demo.Model.VO.ClientsFundersVO;
import com.example.demo.Model.VO.StaffsLicensesVO;
import com.example.demo.Service.StaffsLicenses.StaffsLicensesService;
import com.example.demo.Util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/staffs/licenses")
public class StaffsLicensesController {
    private static final Logger logger = LoggerFactory.getLogger(StaffsLicensesController.class);

    @Autowired
    private StaffsLicensesService staffsLicensesService;

    @PostMapping("/new")
    public ResponseEntity<ApiResponse> NewStaffsLicenses(@Validated @RequestBody StaffsLicensesDTO staffsLicensesDTO, HttpServletRequest request) {
        ApiResponse apiResponse;
        try {
            logger.info("staffsLicensesDTO: {}", staffsLicensesDTO);
            staffsLicensesService.CreateStaffsLicenses(staffsLicensesDTO);
            apiResponse = ApiResponse.success("New staff license added successfully.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);

        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);

            // Return *exact* message in API response
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(),
                    "Error: " + e.getMessage());

            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> GetAllLicensesByStaff(@RequestParam(value = "staff") String staffId, HttpServletRequest request) {

        ApiResponse apiResponse;
        try {
            List<StaffsLicensesVO> staffsLicensesVOList = staffsLicensesService.GetLicensesByStaff(staffId);
            apiResponse = ApiResponse.success(staffsLicensesVOList);
        } catch (Exception e) {
            logger.error("Failed to get", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
    @GetMapping("/")
    public ResponseEntity<ApiResponse> GetStaffsLicenses(@RequestParam(value = "license") String licenseId, HttpServletRequest request) {

        ApiResponse apiResponse;
        try{
            StaffsLicensesVO staffsLicensesVO = staffsLicensesService.GetStaffsLicenses(licenseId);
            apiResponse = ApiResponse.success(staffsLicensesVO);
        }catch (Exception e) {
            logger.error("Failed to get", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
