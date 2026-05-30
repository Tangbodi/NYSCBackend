package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.ClientReferringProvidersDTO;
import com.example.demo.Model.VO.ClientReferringProvidersVO;

import java.util.List;
import com.example.demo.Service.ClientsReferringProviders.ClientReferringProvidersService;
import com.example.demo.Service.StaffsLogin.StaffsLoginService;
import com.example.demo.Util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Validated
@RequestMapping("/client-referring-providers")
public class ClientReferringProvidersController {
    private static final Logger logger = LoggerFactory.getLogger(ClientReferringProvidersController.class);

    @Autowired
    private ClientReferringProvidersService clientReferringProvidersService;
    @Autowired
    private StaffsLoginService staffsLoginService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> NewClientsReferringProviders(
            @Validated @RequestBody ClientReferringProvidersDTO clientReferringProvidersDTO,
            HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            logger.info("clientsReferringProvidersDTO: {}", clientReferringProvidersDTO);
            boolean created = clientReferringProvidersService.CreateClientsReferringProviders(clientReferringProvidersDTO);
            if (created) {
                apiResponse = ApiResponse.success("New client referring provider added successfully.");
            } else {
                apiResponse = ApiResponse.error(ReturnCode.RC409.getCode(), "Client is already linked with NPI number: " + clientReferringProvidersDTO.getNpiNumber());
            }
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> GetAllClientsReferringProviders(
            @RequestParam(value = "client") String clientId,
            HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            List<ClientReferringProvidersVO> list = clientReferringProvidersService.GetAllClientsReferringProviders(clientId);
            apiResponse = ApiResponse.success(list);
        } catch (Exception e) {
            logger.error("Failed to get: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @PutMapping("/")
    public ResponseEntity<ApiResponse> UpdateClientsReferringProviders(
            @Validated @RequestBody ClientReferringProvidersDTO clientReferringProvidersDTO,
            HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            clientReferringProvidersService.UpdateClientsReferringProviders(clientReferringProvidersDTO);
            apiResponse = ApiResponse.success("Client referring provider updated successfully.");
        } catch (Exception e) {
            logger.error("Update error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @DeleteMapping("/")
    public ResponseEntity<ApiResponse> DeleteClientsReferringProviders(
            @RequestBody Map<String, String> body,
            HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            if (!staffsLoginService.CheckIsAdmin(staffId)) {
                apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "You aren't admin.");
            } else {
                clientReferringProvidersService.DeleteClientsReferringProviders(body.get("clientId"), body.get("npiNumber"));
                apiResponse = ApiResponse.success("Referring provider deleted successfully.");
            }
        } catch (Exception e) {
            logger.error("Delete error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
