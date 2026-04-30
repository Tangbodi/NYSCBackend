package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.ClientReferringProvidersDTO;
import com.example.demo.Model.VO.ClientReferringProvidersVO;
import com.example.demo.Service.ClientsReferringProviders.ClientReferringProvidersService;
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
@RequestMapping("/ClientReferringProviders")
public class ClientReferringProvidersController {
    private static final Logger logger = LoggerFactory.getLogger(ClientReferringProvidersController.class);

    @Autowired
    private ClientReferringProvidersService clientReferringProvidersService;
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> NewClientsReferringProviders(
            @Validated @RequestBody ClientReferringProvidersDTO clientReferringProvidersDTO,
            HttpServletRequest request) {

        ApiResponse apiResponse;
        try {
            logger.info("clientsReferringProvidersDTO: {}", clientReferringProvidersDTO);
            clientReferringProvidersService.CreateClientsReferringProviders(clientReferringProvidersDTO);
            apiResponse = ApiResponse.success("New client referring provider added successfully.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);

        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);

            // Return *exact* message in API response
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(),
                    "Error: " + e.getMessage());

            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
    }
    @GetMapping("/")
    public ResponseEntity<ApiResponse> GetClientsReferringProviders(@RequestParam(value = "provider") String providerId, HttpServletRequest request) {

        ApiResponse apiResponse;
        try{
            ClientReferringProvidersVO clientReferringProvidersVO = clientReferringProvidersService.GetClientsReferringProviders(providerId);
            apiResponse = ApiResponse.success(clientReferringProvidersVO);
        }catch (Exception e) {
            logger.error("Failed to get", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> UpdateClientsReferringProviders(@Validated @RequestBody ClientReferringProvidersDTO clientReferringProvidersDTO, HttpServletRequest request) {

        ApiResponse apiResponse;
        try{
            clientReferringProvidersService.UpdateClientsReferringProviders(clientReferringProvidersDTO);
            apiResponse = ApiResponse.success("Client referring provider updated successfully.");
        }catch (Exception e) {
            logger.error("Update error: {}", e.getMessage(), e);

            apiResponse = ApiResponse.error(
                    ReturnCode.RC500.getCode(),
                    "Error: " + e.getMessage()
            );
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
