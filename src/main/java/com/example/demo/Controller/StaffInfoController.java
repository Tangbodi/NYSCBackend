package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.StaffInfoDTO;
import com.example.demo.Model.Entity.StaffInfo;
import com.example.demo.Model.VO.StaffInfoVO;
import com.example.demo.Service.UsersInfo.StaffInfoService;
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
@RequestMapping("/staff-info")
public class StaffInfoController {
    private static final Logger logger = LoggerFactory.getLogger(StaffInfo.class);

    @Autowired
    private StaffInfoService staffInfoService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getUserInfo(HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("staffId");
        ApiResponse apiResponse;

        if (userId == null) {
            logger.info("No staffId in session. Access denied");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }

        StaffInfoVO staffInfoVO = staffInfoService.GetUserInfo(userId, request);
        apiResponse = ApiResponse.success(staffInfoVO);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @PostMapping(value = "/update")
    public ResponseEntity UpdateUserInfo( @Validated @RequestBody StaffInfoDTO staffInfoDTO,
                                          HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("staffId");
        ApiResponse apiResponse;
        if (userId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page");
        } else {
            try {
                staffInfoDTO.setStaffId(userId);
                if (staffInfoService.UpdateUserInfo(staffInfoDTO, request) != null) {
                    apiResponse = ApiResponse.success("User info has been updated");
                } else {
                    apiResponse = ApiResponse.error(ReturnCode.RC400.getCode(), "User info hasn't been updated");
                }
            } catch (Exception e) {
                logger.error("Failed to update user info", e.getMessage(), e);
                apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
            }
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

}

