package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.VO.BehaviorStrategiesVO;
import com.example.demo.Service.BehaviorStrategies.BehaviorStrategiesService;
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
@RequestMapping("/BehaviorStrategies")
public class BehaviorStrategiesController {
    private static final Logger logger = LoggerFactory.getLogger(BehaviorStrategiesController.class);

    @Autowired
    private BehaviorStrategiesService behaviorStrategiesService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> GetAllStrategies(HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            logger.info("No staffId in session. Access denied.");
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            List<BehaviorStrategiesVO> voList = behaviorStrategiesService.GetAllStrategies();
            apiResponse = ApiResponse.success(voList);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
