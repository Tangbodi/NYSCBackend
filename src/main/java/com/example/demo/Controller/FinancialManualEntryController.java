package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.FinancialManualEntryDTO;
import com.example.demo.Model.VO.FinancialManualEntryVO;
import com.example.demo.Service.FinancialManualEntry.FinancialManualEntryService;
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
@RequestMapping("/financial-manual-entries")
public class FinancialManualEntryController {
    private static final Logger logger = LoggerFactory.getLogger(FinancialManualEntryController.class);

    @Autowired
    private FinancialManualEntryService entryService;
    @Autowired
    private StaffsLoginService staffsLoginService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> CreateEntry(
            @Validated @RequestBody FinancialManualEntryDTO dto,
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
            FinancialManualEntryVO vo = entryService.CreateEntry(dto);
            apiResponse = ApiResponse.success(vo);
        } catch (Exception e) {
            logger.error("Failed to create entry: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> GetEntry(
            @RequestParam(value = "entry") String entryId,
            HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            FinancialManualEntryVO vo = entryService.GetEntry(entryId);
            if (vo == null) {
                apiResponse = ApiResponse.error(ReturnCode.RC404.getCode(), "Entry not found.");
            } else {
                apiResponse = ApiResponse.success(vo);
            }
        } catch (Exception e) {
            logger.error("Failed to get entry: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> GetAllEntries(HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            List<FinancialManualEntryVO> voList = entryService.GetAllEntries();
            apiResponse = ApiResponse.success(voList);
        } catch (Exception e) {
            logger.error("Failed to get all entries: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @PutMapping("/")
    public ResponseEntity<ApiResponse> UpdateEntry(
            @Validated @RequestBody FinancialManualEntryDTO dto,
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
        if (dto.getEntryId() == null || dto.getEntryId().isBlank()) {
            apiResponse = ApiResponse.error(ReturnCode.RC400.getCode(), "entryId is required.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            boolean updated = entryService.UpdateEntry(dto);
            if (updated) {
                apiResponse = ApiResponse.success("Entry updated successfully.");
            } else {
                apiResponse = ApiResponse.error(ReturnCode.RC404.getCode(), "Entry not found.");
            }
        } catch (Exception e) {
            logger.error("Failed to update entry: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @DeleteMapping("/")
    public ResponseEntity<ApiResponse> DeleteEntry(
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
            boolean deleted = entryService.DeleteEntry(body.get("entryId"));
            if (deleted) {
                apiResponse = ApiResponse.success("Entry deleted successfully.");
            } else {
                apiResponse = ApiResponse.error(ReturnCode.RC404.getCode(), "Entry not found.");
            }
        } catch (Exception e) {
            logger.error("Failed to delete entry: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
