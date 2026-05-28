package com.example.demo.Controller;

import com.example.demo.Constant.Enum.ReturnCode;
import com.example.demo.Model.VO.FileCabinetVO;
import com.example.demo.Service.FileCabinet.FileCabinetService;
import com.example.demo.Util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/FileCabinet")
public class FileCabinetController {
    private static final Logger logger = LoggerFactory.getLogger(FileCabinetController.class);
    private static final int MAX_FILES = 9;

    @Autowired
    private FileCabinetService fileCabinetService;

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> UploadFiles(
            @RequestParam("clientId") String clientId,
            @RequestParam("files") MultipartFile[] files,
            HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        if (files == null || files.length == 0) {
            apiResponse = ApiResponse.error(ReturnCode.RC400.getCode(), "No files provided.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        if (files.length > MAX_FILES) {
            apiResponse = ApiResponse.error(ReturnCode.RC400.getCode(), "Too many files. Maximum allowed is " + MAX_FILES + ".");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            List<FileCabinetVO> saved = fileCabinetService.SaveFiles(clientId, files, staffId);
            apiResponse = ApiResponse.success(saved);
        } catch (Exception e) {
            logger.error("Failed to upload files: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Failed to upload files: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> DeleteFile(
            @RequestBody Map<String, String> body,
            HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            boolean deleted = fileCabinetService.DeleteFile(body.get("fileId"));
            if (deleted) {
                apiResponse = ApiResponse.success("File deleted successfully.");
            } else {
                apiResponse = ApiResponse.error(ReturnCode.RC404.getCode(), "File not found.");
            }
        } catch (Exception e) {
            logger.error("Failed to delete file: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Failed to delete file: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> GetFilesByClient(
            @RequestParam("client") String clientId,
            HttpServletRequest request) {
        ApiResponse apiResponse;
        Long staffId = (Long) request.getSession().getAttribute("staffId");
        if (staffId == null) {
            apiResponse = ApiResponse.error(ReturnCode.RC401.getCode(), "Please login to access this page.");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }
        try {
            List<FileCabinetVO> files = fileCabinetService.GetFilesByClient(clientId);
            apiResponse = ApiResponse.success(files);
        } catch (Exception e) {
            logger.error("Failed to get files: {}", e.getMessage(), e);
            apiResponse = ApiResponse.error(ReturnCode.RC500.getCode(), "Failed to get files: " + e.getMessage());
        }
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
