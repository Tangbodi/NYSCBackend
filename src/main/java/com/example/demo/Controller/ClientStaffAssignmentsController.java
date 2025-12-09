package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.ClientStaffAssignmentsDTO;
import com.example.demo.Service.ClientStaffAssignments.ClientStaffAssignmentsService;
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
@RequestMapping("/clients-assignment")
public class ClientStaffAssignmentsController {

    private static final Logger logger = LoggerFactory.getLogger(ClientStaffAssignmentsController.class);

    @Autowired
    private ClientStaffAssignmentsService clientStaffAssignmentsService;
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> CreateClientStaffAssignments(@RequestBody ClientStaffAssignmentsDTO clientStaffAssignmentsDTO, HttpServletRequest request){
        ApiResponse apiResponse;
        Long userId = (Long) request.getSession().getAttribute("staffId");
        if (userId == null) {
            logger.info("No staffId in session. Access denied");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }else{
            try{
                clientStaffAssignmentsService.CreateClientStaffAssignments(clientStaffAssignmentsDTO);
                apiResponse = ApiResponse.success("ClientStaffAssignments created successfully.");
            }catch (Exception e) {
                logger.error("Failed to create", e.getMessage(), e);
                apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
            }
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

}
