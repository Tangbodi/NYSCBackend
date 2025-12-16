package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.ClientsFundersDTO;
import com.example.demo.Model.DTO.ClientsInfoDTO;
import com.example.demo.Util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/clients/funders")
public class ClientsFundersController {
    private static final Logger logger = LoggerFactory.getLogger(ClientsFundersController.class);
    @PostMapping("/new")
    public ResponseEntity<ApiResponse> ClientsInfoRegistration(
            @Validated @RequestBody ClientsFundersDTO clientsFundersDTO,
            HttpServletRequest request) {

        ApiResponse apiResponse;
        try {
            logger.info("clientsInfoDTO: {}", clientsInfoDTO);
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
}
