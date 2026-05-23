package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.CustomProgramsDTO;
import com.example.demo.Model.VO.CustomProgramsVO;
import com.example.demo.Service.CustomProgram.CustomProgramsService;
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
@RequestMapping("/CustomPrograms")
public class CustomProgramsController {
    private static final Logger logger = LoggerFactory.getLogger(CustomProgramsController.class);

    @Autowired
    private CustomProgramsService customProgramsService;
    @Autowired
    private StaffsLoginService staffsLoginService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> NewCustomProgram(
            @Validated @RequestBody CustomProgramsDTO customProgramsDTO,
            HttpServletRequest request) {

        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            logger.info("No staffId in session. Access denied.");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            logger.info("customProgramsDTO: {}", customProgramsDTO);
            customProgramsService.CreateCustomProgram(customProgramsDTO);
            apiResponse = ApiResponse.success("New custom program created successfully.");
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> GetCustomProgram(
            @RequestParam(value = "program") String programId,
            HttpServletRequest request) {

        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            logger.info("No staffId in session. Access denied.");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            CustomProgramsVO vo = customProgramsService.GetCustomProgram(programId);
            apiResponse = ApiResponse.success(vo);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> GetAllCustomPrograms(HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            logger.info("No staffId in session. Access denied.");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            List<CustomProgramsVO> voList = customProgramsService.GetAllCustomPrograms();
            apiResponse = ApiResponse.success(voList);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> UpdateCustomProgram(
            @RequestParam(value = "program") String programId,
            @Validated @RequestBody CustomProgramsDTO customProgramsDTO,
            HttpServletRequest request) {

        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            logger.info("No staffId in session. Access denied.");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            if (!staffsLoginService.CheckIsAdmin(staffId)) {
                apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "You aren't admin.");
            } else {
                customProgramsService.UpdateCustomProgram(programId, customProgramsDTO);
                apiResponse = ApiResponse.success("Custom program updated successfully.");
            }
        } catch (Exception e) {
            logger.error("Update error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> DeleteCustomProgram(
            @RequestParam(value = "program") String programId,
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
                customProgramsService.DeleteCustomProgram(programId);
                apiResponse = ApiResponse.success("Custom program deleted successfully.");
            }
        } catch (Exception e) {
            logger.error("Delete error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
