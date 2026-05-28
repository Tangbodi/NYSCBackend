package com.example.demo.Service.FileCabinet;

import com.example.demo.Model.Entity.FileCabinet;
import com.example.demo.Model.Entity.StaffsInfo;
import com.example.demo.Model.VO.FileCabinetVO;
import com.example.demo.Repository.FileCabinetRepository;
import com.example.demo.Repository.StaffsInfoRepository;
import com.example.demo.Util.DateTimeConverter;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileCabinetService {
    private static final Logger logger = LoggerFactory.getLogger(FileCabinetService.class);
    private static final String TOMCAT_CLIENT_FILES_PATH = "/opt/tomcat/webapps/Clients/Files/";
    private static final String CLIENT_FILES_URL = "https://www.nyspecialcare.org/Clients/Files";

    @Autowired
    private FileCabinetRepository fileCabinetRepository;
    @Autowired
    private StaffsInfoRepository staffsInfoRepository;

    @Transactional
    public List<FileCabinetVO> SaveFiles(String clientId, MultipartFile[] files, Long staffId) throws IOException {
        logger.info("Saving {} file(s) for clientId: {}, uploadedBy staffId: {}", files.length, clientId, staffId);

        // Create client directory if it doesn't exist
        String clientDirPath = TOMCAT_CLIENT_FILES_PATH + clientId + "/";
        File clientDir = new File(clientDirPath);
        if (!clientDir.exists()) {
            clientDir.mkdirs();
            logger.info("Created directory: {}", clientDirPath);
        }

        List<FileCabinetVO> savedFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                logger.warn("Skipping empty file.");
                continue;
            }

            String originalFilename = file.getOriginalFilename();
            // Build unique filename: timestamp_originalName to avoid collisions
            String uniqueFilename = System.currentTimeMillis() + "_" + originalFilename;

            // Save file bytes to server disk
            Path targetPath = Paths.get(clientDirPath + uniqueFilename);
            Files.write(targetPath, file.getBytes());
            logger.info("File saved to disk: {}", targetPath);

            String fileType = file.getContentType() != null ? file.getContentType() : "application/octet-stream";
            String filePath = clientDirPath + uniqueFilename;
            String fileUrl  = CLIENT_FILES_URL + "/" + clientId + "/" + uniqueFilename;

            FileCabinet record = new FileCabinet();
            record.setClientId(Long.valueOf(clientId));
            record.setFileName(originalFilename);
            record.setFileType(fileType);
            record.setFilePath(filePath);
            record.setFileUrl(fileUrl);
            record.setAddedBy(staffId);
            record.setCreatedAt(Instant.now());
            record.setModifiedAt(Instant.now());
            fileCabinetRepository.save(record);
            logger.info("File record saved to DB: {}", fileUrl);

            savedFiles.add(ConvertToVO(record));
        }

        return savedFiles;
    }

    public List<FileCabinetVO> GetFilesByClient(String clientId) {
        logger.info("Getting files for clientId: {}", clientId);
        try {
            List<FileCabinet> files = fileCabinetRepository.findByClientIdOrderByCreatedAtDesc(Long.valueOf(clientId));
            List<FileCabinetVO> voList = new ArrayList<>();
            for (FileCabinet file : files) {
                voList.add(ConvertToVO(file));
            }
            return voList;
        } catch (Exception e) {
            logger.error("Failed to get files for clientId {}: {}", clientId, e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    public FileCabinetVO ConvertToVO(FileCabinet file) {
        FileCabinetVO vo = new FileCabinetVO();
        vo.setFileId(String.valueOf(file.getId()));
        vo.setClientId(String.valueOf(file.getClientId()));
        vo.setFileName(file.getFileName());
        vo.setFileType(file.getFileType());
        vo.setFilePath(file.getFilePath());
        vo.setFileUrl(file.getFileUrl());
        vo.setAddedBy(resolveStaffName(file.getAddedBy()));
        vo.setCreatedAt(DateTimeConverter.DateTimeConvertFromInstant(file.getCreatedAt()));
        vo.setModifiedAt(DateTimeConverter.DateTimeConvertFromInstant(file.getModifiedAt()));
        return vo;
    }

    private String resolveStaffName(Long staffId) {
        if (staffId == null) return "";
        StaffsInfo staff = staffsInfoRepository.findById(staffId).orElse(null);
        if (staff != null) {
            return staff.getStaffFirstName() + " " + staff.getStaffLastName();
        }
        logger.warn("Staff not found for staffId: {}", staffId);
        return String.valueOf(staffId);
    }
}
