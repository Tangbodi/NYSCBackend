package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.DonationDTO;
import com.example.demo.Model.VO.DonationVO;
import com.example.demo.Service.Donation.DonationService;
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
import java.util.Map;

@RestController
@Validated
@RequestMapping("/donations")
public class DonationController {
    private static final Logger logger = LoggerFactory.getLogger(DonationController.class);

    @Autowired
    private DonationService donationService;
    @Autowired
    private StaffsLoginService staffsLoginService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> CreateDonation(
            @Validated @RequestBody DonationDTO dto,
            HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        if (!staffsLoginService.CheckIsAdmin(staffId)) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "You aren't admin.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            DonationVO vo = donationService.CreateDonation(dto);
            apiResponse = ApiResponse.success(vo);
        } catch (Exception e) {
            logger.error("Failed to create donation: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> GetDonation(
            @RequestParam(value = "donation") String donationId,
            HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            DonationVO vo = donationService.GetDonation(donationId);
            if (vo == null) {
                apiResponse = ApiResponse.error(ReturnCode.RC404.getCode(), "Donation not found.");
            } else {
                apiResponse = ApiResponse.success(vo);
            }
        } catch (Exception e) {
            logger.error("Failed to get donation: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> GetAllDonations(HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            List<DonationVO> voList = donationService.GetAllDonations();
            apiResponse = ApiResponse.success(voList);
        } catch (Exception e) {
            logger.error("Failed to get all donations: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @PutMapping("/")
    public ResponseEntity<ApiResponse> UpdateDonation(
            @Validated @RequestBody DonationDTO dto,
            HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        if (!staffsLoginService.CheckIsAdmin(staffId)) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "You aren't admin.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        if (dto.getDonationId() == null || dto.getDonationId().isBlank()) {
            apiResponse = ApiResponse.error(ReturnCode.RC400.getCode(), "donationId is required.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            boolean updated = donationService.UpdateDonation(dto);
            if (updated) {
                apiResponse = ApiResponse.success("Donation updated successfully.");
            } else {
                apiResponse = ApiResponse.error(ReturnCode.RC404.getCode(), "Donation not found.");
            }
        } catch (Exception e) {
            logger.error("Failed to update donation: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @DeleteMapping("/")
    public ResponseEntity<ApiResponse> DeleteDonation(
            @RequestBody Map<String, String> body,
            HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        if (!staffsLoginService.CheckIsAdmin(staffId)) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "You aren't admin.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            boolean deleted = donationService.DeleteDonation(body.get("donationId"));
            if (deleted) {
                apiResponse = ApiResponse.success("Donation deleted successfully.");
            } else {
                apiResponse = ApiResponse.error(ReturnCode.RC404.getCode(), "Donation not found.");
            }
        } catch (Exception e) {
            logger.error("Failed to delete donation: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
