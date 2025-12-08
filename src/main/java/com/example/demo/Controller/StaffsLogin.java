package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.StaffsLoginDTO;
import com.example.demo.Model.DTO.StaffsRegisterDTO;
import com.example.demo.Service.StaffsLogin.StaffsLoginService;
import com.example.demo.Service.StaffsInfo.StaffsInfoService;
import com.example.demo.Service.StaffsRegister.StaffsRegistrationService;
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
@RequestMapping("/staffs")
public class StaffsLogin {
    private static final Logger logger = LoggerFactory.getLogger(StaffsLogin.class);
    @Autowired
    private StaffsRegistrationService staffsRegistrationService;
    @Autowired
    private StaffsInfoService staffsInfoService;
    @Autowired
    private StaffsLoginService staffsLoginService;
    @PostMapping("/registration")
    public ResponseEntity<ApiResponse> StaffsRegistration(
            @Validated @RequestBody StaffsRegisterDTO staffsRegisterDTO,
            HttpServletRequest request) {

        ApiResponse apiResponse;

        // Encode email for avoiding email scraping and spam bots
        String encodedEmail = HtmlUtils.htmlEscape(staffsRegisterDTO.getEmail());
        logger.info("Encoded email: {}", encodedEmail);
        staffsRegisterDTO.setEmail(encodedEmail);

        logger.info("staff status: {}", staffsRegisterDTO.getStatus());
        // 1) Basic validations
        if (!staffsRegisterDTO.getPassword().equals(staffsRegisterDTO.getConfirmPassword())) {
            apiResponse = ApiResponse.error(ReturnCode.RC400.getCode(),
                    "Password and confirm password must be the same");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        } else if (staffsInfoService.CheckUsernameExists(staffsRegisterDTO.getUsername()) != null) {
            apiResponse = ApiResponse.error(ReturnCode.RC409.getCode(),
                    "Username already exists");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        } else if (staffsInfoService.CheckEmailExists(staffsRegisterDTO.getEmail()) != null) {
            apiResponse = ApiResponse.error(ReturnCode.RC409.getCode(),
                    "Email already exists");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }

        // 2) Call service – let it throw if anything goes wrong
        try {
            staffsRegistrationService.RegisterStaffsLogin(staffsRegisterDTO);
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
    public ResponseEntity StaffsLogin(@Validated @RequestBody StaffsLoginDTO staffsLoginDTO, HttpServletRequest request) {
        ApiResponse apiResponse;
        if(staffsLoginService.CheckCredentials(staffsLoginDTO,request)){
            apiResponse = ApiResponse.success("Login Successfully");
        }else{
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(),"Username or Password is wrong");
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
    @PostMapping("/logout")
    public ResponseEntity StaffsLogout(HttpServletRequest request) throws IOException {
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
