package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.StaffsInfoDTO;
import com.example.demo.Model.VO.StaffsInfoVO;
import com.example.demo.Service.StaffsInfo.StaffsInfoService;
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
@RequestMapping("/staffs/info")
public class StaffsInfoController {
    private static final Logger logger = LoggerFactory.getLogger(StaffsInfoController.class);

    @Autowired
    private StaffsInfoService staffsInfoService;
    @Autowired
    private StaffsLoginService staffsLoginService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> GetStaffsInfo(@RequestParam(value = "staff") String staffId, HttpServletRequest request) {
        ApiResponse apiResponse;
        Long userId = (Long) request.getSession().getAttribute("staffId");
        if (userId == null) {
            logger.info("No staffId in session. Access denied.");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }else{
            try{
                StaffsInfoVO staffsInfoVO = staffsInfoService.GetStaffsInfo(staffId, request);
                apiResponse = ApiResponse.success(staffsInfoVO);

            }catch (Exception e) {
                logger.error("Failed to login", e.getMessage(), e);
                apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
            }
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> GetAllStaffsInfo(HttpServletRequest request) {
        ApiResponse apiResponse;
        Long userId = (Long) request.getSession().getAttribute("staffId");
        if (userId == null) {
            logger.info("No staffId in session. Access denied.");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }else{
            try{
                List<StaffsInfoVO> staffsInfoVOList = staffsInfoService.GetAllStaffsInfo(request);
                apiResponse = ApiResponse.success(staffsInfoVOList);

            }catch (Exception e) {
                logger.error("Failed to login", e.getMessage(), e);
                apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
            }
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
    @PutMapping(value = "/")
    public ResponseEntity UpdateStaffsInfo(@Validated @RequestBody StaffsInfoDTO staffsInfoDTO,
                                           HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("staffId");
        ApiResponse apiResponse;
        if (userId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
        } else if (!staffsLoginService.CheckIsAdmin(userId)) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "You aren't admin.");
        } else {
            try {
                if (staffsInfoService.UpdateStaffsInfo(staffsInfoDTO, request) != null) {
                    apiResponse = ApiResponse.success("User info updated successfully.");
                } else {
                    apiResponse = ApiResponse.error(ReturnCode.RC400.getCode(), "User info hasn't been updated.");
                }
            } catch (Exception e) {
                logger.error("Update error: {}", e.getMessage(), e);
                apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
            }
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @DeleteMapping("/")
    public ResponseEntity<ApiResponse> DeleteStaffsInfo(
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
                staffsInfoService.DeleteStaffsInfo(body.get("staffId"));
                apiResponse = ApiResponse.success("Staff deleted successfully.");
            }
        } catch (Exception e) {
            logger.error("Delete error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

}

