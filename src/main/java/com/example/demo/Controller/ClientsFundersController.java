package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.ClientsFundersDTO;
import com.example.demo.Model.DTO.ClientsInfoDTO;
import com.example.demo.Model.VO.ClientsContactsVO;
import com.example.demo.Model.VO.ClientsFundersVO;
import com.example.demo.Service.ClientsFundersService.ClientsFundersService;
import com.example.demo.Util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/clients/funders")
public class ClientsFundersController {
    private static final Logger logger = LoggerFactory.getLogger(ClientsFundersController.class);

    @Autowired
    private ClientsFundersService clientsFundersService;

    @PostMapping("/new")
    public ResponseEntity<ApiResponse> NewClientsFunders(
            @Validated @RequestBody ClientsFundersDTO clientsFundersDTO,
            HttpServletRequest request) {

        ApiResponse apiResponse;
        try {
            logger.info("clientsFundersDTO: {}", clientsFundersDTO);
            clientsFundersService.CreateClientsFunders(clientsFundersDTO);
            apiResponse = ApiResponse.success("New client funder added successfully.");
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
    public ResponseEntity<ApiResponse> GetClientsFunders(@RequestParam(value = "funder") String funderId, HttpServletRequest request) {

        ApiResponse apiResponse;
        try{
            ClientsFundersVO clientsFundersVO = clientsFundersService.GetClientsFunders(funderId);
            apiResponse = ApiResponse.success(clientsFundersVO);
        }catch (Exception e) {
            logger.error("Failed to get", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> GetAllFundersByClientId(@RequestParam(value = "client") String clientId, HttpServletRequest request) {

        ApiResponse apiResponse;
        try{
            List<ClientsFundersVO> clientsFundersVO = clientsFundersService.GetAllFundersByClient(clientId);
            apiResponse = ApiResponse.success(clientsFundersVO);
        }catch (Exception e) {
            logger.error("Failed to get", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
    @PostMapping("/update")
    public ResponseEntity<ApiResponse> UpdateClientsFunders(@Validated @RequestBody ClientsFundersDTO clientsFundersDTO, HttpServletRequest request) {

        ApiResponse apiResponse;
        try{
            clientsFundersService.UpdateClientsFunders(clientsFundersDTO);
            apiResponse = ApiResponse.success("Client funder updated successfully.");
        }catch (Exception e) {
            logger.error("Failed to get", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
