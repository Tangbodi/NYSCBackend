package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.ClientFundersDTO;
import com.example.demo.Model.VO.ClientFundersVO;
import com.example.demo.Model.VO.ClientServicesVO;
import com.example.demo.Service.ClientsFundersService.ClientFundersService;
import com.example.demo.Service.StaffsLogin.StaffsLoginService;
import com.example.demo.Util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/ClientFunders")
public class ClientFundersController {
    private static final Logger logger = LoggerFactory.getLogger(ClientFundersController.class);

    @Autowired
    private ClientFundersService clientFundersService;
    @Autowired
    private StaffsLoginService staffsLoginService;
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> NewClientsFunders(
            @Validated @RequestBody ClientFundersDTO clientFundersDTO,
            HttpServletRequest request) {

        ApiResponse apiResponse;
        try {
            logger.info("clientsFundersDTO: {}", clientFundersDTO);
            clientFundersService.CreateClientsFunders(clientFundersDTO);
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
    public ResponseEntity<ApiResponse> GetClientsFunders(
            @RequestParam(value = "client") String clientId,
            @RequestParam(value = "funder") String funderId,
            HttpServletRequest request) {

        ApiResponse apiResponse;
        try {
            ClientFundersVO clientFundersVO = clientFundersService.GetClientsFunders(clientId, funderId);
            apiResponse = ApiResponse.success(clientFundersVO);
        } catch (Exception e) {
            logger.error("Failed to get", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> GetAllFundersByClient(@RequestParam(value = "client") String clientId, HttpServletRequest request) {

        ApiResponse apiResponse;
        try{
            List<ClientFundersVO> clientFundersVOList = clientFundersService.GetAllFundersByClient(clientId);
            apiResponse = ApiResponse.success(clientFundersVOList);
        }catch (Exception e) {
            logger.error("Failed to get", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
    @GetMapping("/services")
    public ResponseEntity<ApiResponse> GetAllServicesByClient(@RequestParam(value = "client") String clientId, HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            logger.info("No staffId in session. Access denied.");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            ClientServicesVO vo = clientFundersService.GetAllServicesByClient(clientId);
            apiResponse = ApiResponse.success(vo);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> UpdateClientsFunders(@Validated @RequestBody ClientFundersDTO clientFundersDTO, HttpServletRequest request) {

        ApiResponse apiResponse;
        try{
            clientFundersService.UpdateClientsFunders(clientFundersDTO);
            apiResponse = ApiResponse.success("Client funder updated successfully.");
        }catch (Exception e) {
            logger.error("Update error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> DeleteClientsFunders(
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
                clientFundersService.DeleteClientsFunders(body.get("clientId"), body.get("funderId"));
                apiResponse = ApiResponse.success("Client funder deleted successfully.");
            }
        } catch (Exception e) {
            logger.error("Delete error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
