package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.DTO.EventDetailsDTO;
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
}
