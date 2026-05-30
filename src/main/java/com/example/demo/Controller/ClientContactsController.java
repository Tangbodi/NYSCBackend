package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.ClientContactsDTO;
import com.example.demo.Model.VO.ClientContactsVO;
import com.example.demo.Service.ClientsContacts.ClientContactsService;
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
@RequestMapping("/client-contacts")
public class ClientContactsController {
    private static final Logger logger = LoggerFactory.getLogger(ClientContactsController.class);

    @Autowired
    private ClientContactsService clientContactsService;
    @Autowired
    private StaffsLoginService staffsLoginService;
    @PostMapping("/")
    public ResponseEntity<ApiResponse> NewClientsContacts(
            @Validated @RequestBody ClientContactsDTO clientContactsDTO,
            HttpServletRequest request) {

        ApiResponse apiResponse;
        try {
            logger.info("clientsContactsDTO: {}", clientContactsDTO);
            clientContactsService.CreateClientsContacts(clientContactsDTO);
            apiResponse = ApiResponse.success("New client contact added successfully.");
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
    public ResponseEntity<ApiResponse> GetClientsContacts(@RequestParam(value = "client") String clientId, HttpServletRequest request){
        ApiResponse apiResponse;
        try{
            ClientContactsVO clientContactsVO = clientContactsService.GetClientsContacts(clientId);
            apiResponse = ApiResponse.success(clientContactsVO);
        }catch (Exception e) {
            logger.error("Failed to get", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
    @PutMapping("/")
    public ResponseEntity<ApiResponse> UpdateClientsContacts(@Validated @RequestBody ClientContactsDTO clientContactsDTO, HttpServletRequest request){
        ApiResponse apiResponse;
        try{
            clientContactsService.UpdateClientsContacts(clientContactsDTO);
            apiResponse = ApiResponse.success("Client contact updated successfully.");
        }catch (Exception e) {
            logger.error("Update error: {}", e.getMessage(), e);

            apiResponse = ApiResponse.error(
                    ReturnCode.RC500.getCode(),
                    "Error: " + e.getMessage()
            );
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @DeleteMapping("/")
    public ResponseEntity<ApiResponse> DeleteClientsContacts(
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
                clientContactsService.DeleteClientsContacts(body.get("clientId"));
                apiResponse = ApiResponse.success("Client contact deleted successfully.");
            }
        } catch (Exception e) {
            logger.error("Delete error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
