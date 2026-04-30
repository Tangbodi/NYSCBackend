package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.ClientProgramAssignmentsDTO;
import com.example.demo.Model.VO.ClientProgramAssignmentsVO;
import com.example.demo.Service.ClientProgramAssignments.ClientProgramAssignmentsService;
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
@RequestMapping("/ClientProgramAssignments")
public class ClientProgramAssignmentsController {
    private static final Logger logger = LoggerFactory.getLogger(ClientProgramAssignmentsController.class);

    @Autowired
    private ClientProgramAssignmentsService clientProgramAssignmentsService;

    @PostMapping("/assign")
    public ResponseEntity<ApiResponse> AssignClientToProgram(
            @Validated @RequestBody ClientProgramAssignmentsDTO dto,
            HttpServletRequest request) {

        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            logger.info("No staffId in session. Access denied.");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            clientProgramAssignmentsService.AssignClientToProgram(dto);
            apiResponse = ApiResponse.success("Client assigned to program successfully.");
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> GetAssignment(
            @RequestParam(value = "client") String clientId,
            HttpServletRequest request) {

        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            logger.info("No staffId in session. Access denied.");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            ClientProgramAssignmentsVO vo = clientProgramAssignmentsService.GetAssignment(clientId);
            apiResponse = ApiResponse.success(vo);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @DeleteMapping("/unassign")
    public ResponseEntity<ApiResponse> UnassignProgram(
            @Validated @RequestBody ClientProgramAssignmentsDTO dto,
            HttpServletRequest request) {

        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            logger.info("No staffId in session. Access denied.");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            clientProgramAssignmentsService.UnassignProgram(dto);
            apiResponse = ApiResponse.success("Program unassigned from client successfully.");
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
