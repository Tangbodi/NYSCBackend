package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.ClientsInfoDTO;
import com.example.demo.Service.ClientsInfo.ClientsInfoService;
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

@RestController
@Validated
@RequestMapping("/clients-info")
public class ClientsInfoController {
    private static final Logger logger = LoggerFactory.getLogger(ClientsInfoController.class);
    @Autowired
    private ClientsInfoService clientsInfoService;

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
    @PostMapping("/update")
    public ResponseEntity<ApiResponse> ClientsInfoUpdate(
            @Validated @RequestBody ClientsInfoDTO clientsInfoDTO,
            HttpServletRequest request) {

        ApiResponse apiResponse;
        try {
            clientsInfoService.UpdateClientsInfo(clientsInfoDTO);
            apiResponse = ApiResponse.success("Clients updated successfully");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);

        } catch (Exception e) {
            logger.error("Update error: {}", e.getMessage(), e);

            // Return *exact* message in API response
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(),
                    "Error: " + e.getMessage());

            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }

    }
}
