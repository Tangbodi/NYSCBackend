package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.EventDetailsDTO;
import com.example.demo.Model.DTO.EventUpdateDTO;
import com.example.demo.Model.VO.EventAuditTrailVO;
import com.example.demo.Model.VO.EventDetailsVO;
import com.example.demo.Service.EventDetails.EventDetailsService;
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
@RequestMapping("/EventDetails")
public class EventDetailsController {
    private static final Logger logger = LoggerFactory.getLogger(EventDetailsController.class);

    @Autowired
    private EventDetailsService eventDetailsService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> CreateEvent(
            @Validated @RequestBody EventDetailsDTO dto,
            HttpServletRequest request) {

        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            logger.info("No staffId in session. Access denied.");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            eventDetailsService.CreateEvent(dto, staffId);
            apiResponse = ApiResponse.success("Event created successfully.");
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> UpdateEvent(
            @RequestParam(value = "event") String eventId,
            @Validated @RequestBody EventUpdateDTO dto,
            HttpServletRequest request) {

        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            logger.info("No staffId in session. Access denied.");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            eventDetailsService.UpdateEvent(eventId, dto, staffId);
            apiResponse = ApiResponse.success("Event updated successfully.");
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> GetEventsByClientId(
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
            List<EventDetailsVO> voList = eventDetailsService.GetEventsByClientId(clientId);
            apiResponse = ApiResponse.success(voList);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/staff")
    public ResponseEntity<ApiResponse> GetEventsByStaffId(
            @RequestParam(value = "staff") String staffId,
            HttpServletRequest request) {

        ApiResponse apiResponse;
        Long sessionStaffId = (Long) request.getSession().getAttribute("staffId");
        if (sessionStaffId == null) {
            logger.info("No staffId in session. Access denied.");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            List<EventDetailsVO> voList = eventDetailsService.GetEventsByStaffId(staffId);
            apiResponse = ApiResponse.success(voList);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/audit")
    public ResponseEntity<ApiResponse> GetAuditTrail(
            @RequestParam(value = "event") String eventId,
            HttpServletRequest request) {

        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            logger.info("No staffId in session. Access denied.");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            List<EventAuditTrailVO> voList = eventDetailsService.GetAuditTrail(eventId);
            apiResponse = ApiResponse.success(voList);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> DeleteEvent(
            @RequestParam(value = "event") String eventId,
            HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            eventDetailsService.DeleteEvent(eventId);
            apiResponse = ApiResponse.success("Event deleted successfully.");
        } catch (Exception e) {
            logger.error("Delete error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
