package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.ClientsContactsDTO;
import com.example.demo.Model.DTO.ClientsFundersDTO;
import com.example.demo.Model.VO.ClientsContactsVO;
import com.example.demo.Model.VO.ClientsInfoVO;
import com.example.demo.Service.ClientsContacts.ClientsContactsService;
import com.example.demo.Util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/clients/contacts")
public class ClientsContactsController {
    private static final Logger logger = LoggerFactory.getLogger(ClientsContactsController.class);

    @Autowired
    private ClientsContactsService clientsContactsService;
    @PostMapping("/new")
    public ResponseEntity<ApiResponse> NewClientsContacts(
            @Validated @RequestBody ClientsContactsDTO clientsContactsDTO,
            HttpServletRequest request) {

        ApiResponse apiResponse;
        try {
            logger.info("clientsContactsDTO: {}", clientsContactsDTO);
            clientsContactsService.CreateClientsContacts(clientsContactsDTO);
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
            ClientsContactsVO clientsContactsVO = clientsContactsService.GetClientsContacts(clientId);
            apiResponse = ApiResponse.success(clientsContactsVO);
        }catch (Exception e) {
            logger.error("Failed to get", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> UpdateClientsContacts(@Validated @RequestBody ClientsContactsDTO clientsContactsDTO, HttpServletRequest request){
        ApiResponse apiResponse;
        try{
            clientsContactsService.UpdateClientsContacts(clientsContactsDTO);
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
}
