package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.ClientStaffAssignmentsDTO;
import com.example.demo.Model.VO.ClientStaffAssignmentsVO;
import com.example.demo.Service.ClientStaffAssignments.ClientStaffAssignmentsService;
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
@RequestMapping("/client-assignments")
public class ClientStaffAssignmentsController {

    private static final Logger logger = LoggerFactory.getLogger(ClientStaffAssignmentsController.class);

    @Autowired
    private ClientStaffAssignmentsService clientStaffAssignmentsService;
    @PostMapping("/")
    public ResponseEntity<ApiResponse> NewClientStaffAssignments(@RequestBody ClientStaffAssignmentsDTO clientStaffAssignmentsDTO, HttpServletRequest request){
        ApiResponse apiResponse;
        Long userId = (Long) request.getSession().getAttribute("staffId");
        if (userId == null) {
            logger.info("No staffId in session. Access denied");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }else{
            try{
                boolean created = clientStaffAssignmentsService.CreateClientStaffAssignments(clientStaffAssignmentsDTO);
                if (created) {
                    apiResponse = ApiResponse.success("Staff assigned to client successfully.");
                } else {
                    apiResponse = ApiResponse.error(ReturnCode.RC409.getCode(), "This staff is already assigned to the client.");
                }
            }catch (Exception e) {
                logger.error("Failed to create", e.getMessage(), e);
                apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
            }
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
    @GetMapping("/")
    public ResponseEntity<ApiResponse> AllClientStaffAssignments(HttpServletRequest request){
        ApiResponse apiResponse;
        Long userId = (Long) request.getSession().getAttribute("staffId");
        if (userId == null) {
            logger.info("No staffId in session. Access denied");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }else{
            try{
                List<Map<String, Object>> clientStaffAssignmentsVOList = clientStaffAssignmentsService.GetAllClientStaffAssignments(request);
                apiResponse = ApiResponse.success(clientStaffAssignmentsVOList);

            }catch (Exception e) {
                logger.error("Failed to create", e.getMessage(), e);
                apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
            }
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @DeleteMapping("/")
    public ResponseEntity<ApiResponse> RemoveClientStaffAssignment(
            @Validated @RequestBody ClientStaffAssignmentsDTO dto,
            HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            boolean removed = clientStaffAssignmentsService.RemoveClientStaffAssignment(dto);
            if (removed) {
                apiResponse = ApiResponse.success("Staff unassigned successfully.");
            } else {
                apiResponse = ApiResponse.error(ReturnCode.RC404.getCode(), "Assignment not found.");
            }
        } catch (Exception e) {
            logger.error("Unassign error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
