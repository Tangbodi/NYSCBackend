package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.StaffLicensesDTO;
import com.example.demo.Model.VO.StaffLicensesVO;
import com.example.demo.Service.StaffsLicenses.StaffLicensesService;
import com.example.demo.Service.StaffsLogin.StaffsLoginService;
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
public class StaffLicensesController {
    private static final Logger logger = LoggerFactory.getLogger(StaffLicensesController.class);

    @Autowired
    private StaffLicensesService staffLicensesService;
    @Autowired
    private StaffsLoginService staffsLoginService;

    @PostMapping("/new")
    public ResponseEntity<ApiResponse> NewStaffsLicenses(@Validated @RequestBody StaffLicensesDTO staffLicensesDTO, HttpServletRequest request) {
        ApiResponse apiResponse;
        try {
            logger.info("staffsLicensesDTO: {}", staffLicensesDTO);
            staffLicensesService.CreateStaffsLicenses(staffLicensesDTO);
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
            List<StaffLicensesVO> staffLicensesVOList = staffLicensesService.GetLicensesByStaff(staffId);
            apiResponse = ApiResponse.success(staffLicensesVOList);
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
            StaffLicensesVO staffLicensesVO = staffLicensesService.GetStaffsLicenses(licenseId);
            apiResponse = ApiResponse.success(staffLicensesVO);
        }catch (Exception e) {
            logger.error("Failed to get", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> UpdateStaffsLicenses(@Validated @RequestBody StaffLicensesDTO staffLicensesDTO, HttpServletRequest request) {

        ApiResponse apiResponse;
        try{
            staffLicensesService.UpdateStaffsLicenses(staffLicensesDTO);
            apiResponse = ApiResponse.success("Staff license updated successfully.");
        }catch (Exception e) {
            logger.error("Update error: {}", e.getMessage(), e);

            apiResponse = ApiResponse.error(
                    ReturnCode.RC500.getCode(),
                    "Error: " + e.getMessage()
            );
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> DeleteStaffLicense(
            @RequestParam(value = "license") String licenseId,
            HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            if (!staffsLoginService.CheckIsAdmin(staffId)) {
                apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "You aren't admin.");
            } else {
                staffLicensesService.DeleteStaffLicense(licenseId);
                apiResponse = ApiResponse.success("Staff license deleted successfully.");
            }
        } catch (Exception e) {
            logger.error("Delete error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
