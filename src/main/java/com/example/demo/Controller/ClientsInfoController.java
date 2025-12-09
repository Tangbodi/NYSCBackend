package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.ClientsInfoDTO;
import com.example.demo.Model.VO.ClientsInfoVO;
import com.example.demo.Service.ClientsInfo.ClientsInfoService;
import com.example.demo.Service.StaffsLogin.StaffsLoginService;
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
@RequestMapping("/clients")
public class ClientsInfoController {
    private static final Logger logger = LoggerFactory.getLogger(ClientsInfoController.class);
    @Autowired
    private ClientsInfoService clientsInfoService;
    @Autowired
    private StaffsLoginService staffsLoginService;

    @PostMapping("/registration")
    public ResponseEntity<ApiResponse> ClientsInfoRegistration(
            @Validated @RequestBody ClientsInfoDTO clientsInfoDTO,
            HttpServletRequest request) {

        ApiResponse apiResponse;
        try {
            clientsInfoService.RegisterClientsInfo(clientsInfoDTO);
            apiResponse = ApiResponse.success("Clients registered successfully");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);

        } catch (Exception e) {
            logger.error("Registration error: {}", e.getMessage(), e);

            // Return *exact* message in API response
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(),
                    "Error: " + e.getMessage());

            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }

    }
    @GetMapping("/info")
    public ResponseEntity<ApiResponse> GetClientsInfo(@RequestParam(value = "client") String clientId, HttpServletRequest request){
        ApiResponse apiResponse;
        Long userId = (Long) request.getSession().getAttribute("staffId");
        if (userId == null) {
            logger.info("No staffId in session. Access denied.");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }else{
            try{
                ClientsInfoVO clientsInfoVO = clientsInfoService.GetClientsInfo(clientId);
                apiResponse = ApiResponse.success(clientsInfoVO);
            }catch (Exception e) {
                logger.error("Failed to login", e.getMessage(), e);
                apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
            }
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
    @PostMapping("/info/update")
    public ResponseEntity<ApiResponse> UpdateClientsInfo(
            @Validated @RequestBody ClientsInfoDTO clientsInfoDTO,
            HttpServletRequest request) {

        ApiResponse apiResponse;

        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            logger.info("No staffId in session. Access denied.");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }

        try {
            // check admin
            if (!staffsLoginService.CheckIsAdmin(staffId)) {
                // RC401 in your enum, but semantically this is closer to 403 Forbidden
                apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "You aren't admin.");
            } else {
                clientsInfoService.UpdateClientsInfo(clientsInfoDTO);
                apiResponse = ApiResponse.success("Clients updated successfully.");
            }
        } catch (Exception e) {
            logger.error("Update error: {}", e.getMessage(), e);

            apiResponse = ApiResponse.error(
                    ReturnCode.RC500.getCode(),
                    "Error: " + e.getMessage()
            );
        }
        // single unified return point
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

}
