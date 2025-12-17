package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.ClientsFundersDTO;
import com.example.demo.Model.DTO.ClientsReferringProvidersDTO;
import com.example.demo.Model.VO.ClientsFundersVO;
import com.example.demo.Model.VO.ClientsReferringProvidersVO;
import com.example.demo.Service.ClientsReferringProviders.ClientsReferringProvidersService;
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
@RequestMapping("/clients/referring-providers")
public class ClientsReferringProvidersController {
    private static final Logger logger = LoggerFactory.getLogger(ClientsReferringProvidersController.class);

    @Autowired
    private ClientsReferringProvidersService clientsReferringProvidersService;
    @PostMapping("/new")
    public ResponseEntity<ApiResponse> NewClientsReferringProviders(
            @Validated @RequestBody ClientsReferringProvidersDTO clientsReferringProvidersDTO,
            HttpServletRequest request) {

        ApiResponse apiResponse;
        try {
            logger.info("clientsReferringProvidersDTO: {}", clientsReferringProvidersDTO);
            clientsReferringProvidersService.CreateClientsReferringProviders(clientsReferringProvidersDTO);
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
            ClientsReferringProvidersVO clientsReferringProvidersVO = clientsReferringProvidersService.GetClientsReferringProviders(providerId);
            apiResponse = ApiResponse.success(clientsReferringProvidersVO);
        }catch (Exception e) {
            logger.error("Failed to get", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
