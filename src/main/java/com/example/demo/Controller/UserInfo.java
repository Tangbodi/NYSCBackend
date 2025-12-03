package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.VO.UserInfoVO;
import com.example.demo.Service.UsersInfo.UserInfoService;
import com.example.demo.Util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/user-info")
public class UserInfo {
    private static final Logger logger = LoggerFactory.getLogger(UserInfo.class);

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping
    public ResponseEntity<ApiResponse> getUserInfo(HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        ApiResponse apiResponse;

        if (userId == null) {
            logger.info("No userId in session. Access denied to /user-info");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }

        UserInfoVO userInfoVO = userInfoService.GetUserInfo(userId, request);
        apiResponse = ApiResponse.success(userInfoVO);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
