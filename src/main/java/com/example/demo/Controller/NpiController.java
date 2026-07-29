package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.VO.NpiProviderVO;
import com.example.demo.Service.Npi.NpiService;
import com.example.demo.Util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/npi")
public class NpiController {
    private static final Logger logger = LoggerFactory.getLogger(NpiController.class);

    @Autowired
    private NpiService npiService;

    @GetMapping("/provider")
    public ResponseEntity<ApiResponse> getProvider(
            @RequestParam(value = "npi") String npi,
            HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        if (npi == null || !npi.replaceAll("\\D", "").matches("\\d{10}")) {
            apiResponse = ApiResponse.error(ReturnCode.RC400.getCode(), "npi must be exactly 10 digits.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        String cleanNpi = npi.replaceAll("\\D", "");
        try {
            NpiProviderVO vo = npiService.lookupProvider(cleanNpi);
            if (vo == null) {
                apiResponse = ApiResponse.error(ReturnCode.RC404.getCode(), "No provider found for NPI: " + cleanNpi);
            } else {
                apiResponse = ApiResponse.success(vo);
            }
        } catch (Exception e) {
            logger.error("NPI lookup error: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Error: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
