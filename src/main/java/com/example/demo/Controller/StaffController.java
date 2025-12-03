package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.StaffLoginDTO;
import com.example.demo.Model.DTO.StaffRegisterDTO;
import com.example.demo.Service.UserLogin.StaffLoginService;
import com.example.demo.Service.UsersInfo.StaffInfoService;
import com.example.demo.Service.UsersRegister.StaffRegistrationService;
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
@RequestMapping("/staff")
public class StaffController {
    private static final Logger logger = LoggerFactory.getLogger(StaffController.class);
    @Autowired
    private StaffRegistrationService staffRegistrationService;
    @Autowired
    private StaffInfoService staffInfoService;
    @Autowired
    private StaffLoginService staffLoginService;
    @PostMapping("/registration")
    public ResponseEntity<ApiResponse> UserRegistration(
            @Validated @RequestBody StaffRegisterDTO staffRegisterDTO,
            HttpServletRequest request) {

        ApiResponse apiResponse;

        // Encode email for avoiding email scraping and spam bots
        String encodedEmail = HtmlUtils.htmlEscape(staffRegisterDTO.getEmail());
        logger.info("Encoded email: {}", encodedEmail);
        staffRegisterDTO.setEmail(encodedEmail);

        logger.info("staff status: {}", staffRegisterDTO.getStatus());
        // 1) Basic validations
        if (!staffRegisterDTO.getPassword().equals(staffRegisterDTO.getConfirmPassword())) {
            apiResponse = ApiResponse.error(ReturnCode.RC400.getCode(),
                    "Password and confirm password must be the same");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        } else if (staffInfoService.CheckUsernameExists(staffRegisterDTO.getUsername()) != null) {
            apiResponse = ApiResponse.error(ReturnCode.RC409.getCode(),
                    "Username already exists");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        } else if (staffInfoService.CheckEmailExists(staffRegisterDTO.getEmail()) != null) {
            apiResponse = ApiResponse.error(ReturnCode.RC409.getCode(),
                    "Email already exists");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }

        // 2) Call service – let it throw if anything goes wrong
        try {
            staffRegistrationService.RegisterUser(staffRegisterDTO);
            apiResponse = ApiResponse.success("Staff registered successfully");
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
    public ResponseEntity UserLogin(@Validated @RequestBody StaffLoginDTO staffLoginDTO, HttpServletRequest request) {
        ApiResponse apiResponse;
        if(staffLoginService.CheckCredentials(staffLoginDTO,request)){
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
