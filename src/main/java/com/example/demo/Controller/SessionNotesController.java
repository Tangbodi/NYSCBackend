package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.SessionNotesDTO;
import com.example.demo.Model.VO.SessionNotesVO;
import com.example.demo.Service.SessionNotes.SessionNotesService;
import com.example.demo.Service.StaffsLogin.StaffsLoginService;
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
@RequestMapping("/SessionNotes")
public class SessionNotesController {
    private static final Logger logger = LoggerFactory.getLogger(SessionNotesController.class);

    @Autowired
    private SessionNotesService sessionNotesService;
    @Autowired
    private StaffsLoginService staffsLoginService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> CreateSessionNote(
            @Validated @RequestBody SessionNotesDTO dto,
            HttpServletRequest request) {

        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            logger.info("No staffId in session. Access denied.");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            String sessionId = sessionNotesService.CreateSessionNote(dto, staffId);
            apiResponse = ApiResponse.success(sessionId);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> UpdateSessionNote(
            @RequestParam(value = "session") String sessionId,
            @Validated @RequestBody SessionNotesDTO dto,
            HttpServletRequest request) {

        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            logger.info("No staffId in session. Access denied.");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            sessionNotesService.UpdateSessionNote(sessionId, dto, staffId);
            apiResponse = ApiResponse.success("Session note updated successfully.");
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> GetSessionNote(
            @RequestParam(value = "session") String sessionId,
            HttpServletRequest request) {

        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            logger.info("No staffId in session. Access denied.");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            SessionNotesVO vo = sessionNotesService.GetSessionNote(sessionId);
            if (vo == null) {
                apiResponse = ApiResponse.error(ReturnCode.RC404.getCode(), "Session note not found.");
            } else {
                apiResponse = ApiResponse.success(vo);
            }
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> DeleteSessionNote(
            @RequestParam(value = "session") String sessionId,
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
                sessionNotesService.DeleteSessionNote(sessionId);
                apiResponse = ApiResponse.success("Session note deleted successfully.");
            }
        } catch (Exception e) {
            logger.error("Delete error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
