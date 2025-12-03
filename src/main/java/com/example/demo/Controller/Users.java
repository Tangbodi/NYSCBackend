package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.UserLoginDTO;
import com.example.demo.Model.DTO.UserRegisterDTO;
import com.example.demo.Model.Entity.UsersLogin;
import com.example.demo.Model.VO.UserInfoVO;
import com.example.demo.Service.UserLogin.UserLoginService;
import com.example.demo.Service.UsersInfo.UserInfoService;
import com.example.demo.Service.UsersRegister.UserRegistrationService;
import com.example.demo.Util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;

@RestController
@Validated
@RequestMapping("/user")
public class Users {
    private static final Logger logger = LoggerFactory.getLogger(Users.class);
    @Autowired
    private UserRegistrationService userRegistrationService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserLoginService userLoginService;
    @PostMapping("/registration")
    public ResponseEntity<ApiResponse> UserRegistration(
            @Validated @RequestBody UserRegisterDTO userRegisterDTO,
            HttpServletRequest request) {

        ApiResponse apiResponse;

        // Encode email for avoiding email scraping and spam bots
        String encodedEmail = HtmlUtils.htmlEscape(userRegisterDTO.getEmail());
        logger.info("Encoded email: {}", encodedEmail);
        userRegisterDTO.setEmail(encodedEmail);

        logger.info("user status: {}",userRegisterDTO.getStatus());
        // 1) Basic validations
        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            apiResponse = ApiResponse.error(ReturnCode.RC400.getCode(),
                    "Password and confirm password must be the same");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        } else if (userInfoService.CheckUsernameExists(userRegisterDTO.getUsername()) != null) {
            apiResponse = ApiResponse.error(ReturnCode.RC409.getCode(),
                    "Username already exists");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        } else if (userInfoService.CheckEmailExists(userRegisterDTO.getEmail()) != null) {
            apiResponse = ApiResponse.error(ReturnCode.RC409.getCode(),
                    "Email already exists");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }

        // 2) Call service – let it throw if anything goes wrong
        try {
            userRegistrationService.RegisterUser(userRegisterDTO);
            apiResponse = ApiResponse.success("User registered successfully");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);

        } catch (Exception e) {
            logger.error("Registration error: {}", e.getMessage(), e);

            // Return *exact* message in API response
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(),
                    "Error: " + e.getMessage());

            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }

    }
    @PostMapping("/login")
    public ResponseEntity UserLogin(@Validated @RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request) {
        ApiResponse apiResponse;
        if(userLoginService.CheckCredentials(userLoginDTO,request)){
            apiResponse = ApiResponse.success("Login Successfully");
        }else{
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(),"Username or Password is wrong");
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
    @PostMapping("/logout")
    public ResponseEntity UserLogout(HttpServletRequest request) throws IOException {
        logger.info("Logging out");
        ApiResponse apiResponse = ApiResponse.success("Logged out");
        request.getSession().invalidate();
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
    public static class UserRegistrationException extends RuntimeException {
        public UserRegistrationException(String message) {
            super(message);
        }

        public UserRegistrationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
