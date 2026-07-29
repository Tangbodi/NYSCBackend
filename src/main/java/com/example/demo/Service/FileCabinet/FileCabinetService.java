package com.example.demo.Service.FileCabinet;

import com.example.demo.Model.Entity.ClientsInfo;
import com.example.demo.Model.Entity.FileCabinet;
import com.example.demo.Model.Entity.StaffsInfo;
import com.example.demo.Model.VO.FileCabinetVO;
import com.example.demo.Repository.ClientsInfoRepository;
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
    @Autowired
    private ClientsInfoRepository clientsInfoRepository;

    @Transactional
    public List<FileCabinetVO> SaveFiles(String clientId, String tag, MultipartFile[] files, Long staffId) throws IOException {
        logger.info("Saving {} file(s) for clientId: {}, uploadedBy staffId: {}", files.length, clientId, staffId);

        // Resolve client name for folder
        String folderName = resolveClientFolderName(clientId);

        // Create client directory if it doesn't exist
        String clientDirPath = TOMCAT_CLIENT_FILES_PATH + folderName + "/";
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
            String fileUrl  = CLIENT_FILES_URL + "/" + folderName + "/" + uniqueFilename;

            FileCabinet record = new FileCabinet();
            record.setClientId(Long.valueOf(clientId));
            record.setFileName(originalFilename);
            record.setTag(tag);
            record.setFileType(fileType);
            record.setFilePath(filePath);
            record.setFileUrl(fileUrl);
            record.setAddedBy(staffId);
            record.setCreatedAt(DateTimeConverter.nowNyc());
            record.setModifiedAt(DateTimeConverter.nowNyc());
            fileCabinetRepository.save(record);
            logger.info("File record saved to DB: {}", fileUrl);

            savedFiles.add(ConvertToVO(record));
        }

        return savedFiles;
    }

    @Transactional
    public boolean DeleteFile(String fileId) {
        logger.info("Deleting file with fileId: {}", fileId);
        try {
            Integer id = Integer.valueOf(fileId);
            FileCabinet record = fileCabinetRepository.findById(id).orElse(null);
            if (record == null) {
                logger.warn("File record not found for fileId: {}", fileId);
                return false;
            }

            // Delete physical file from disk
            Path filePath = Paths.get(record.getFilePath());
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                logger.info("File deleted from disk: {}", filePath);
            } else {
                logger.warn("File not found on disk, removing DB record only: {}", filePath);
            }

            // Delete DB record
            fileCabinetRepository.deleteById(id);
            logger.info("File record deleted from DB: {}", fileId);
            return true;
        } catch (Exception e) {
            logger.error("Failed to delete file {}: {}", fileId, e.getMessage(), e);
            throw new RuntimeException(e);
        }
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
        vo.setTag(file.getTag());
        vo.setFileType(file.getFileType());
        vo.setFilePath(file.getFilePath());
        vo.setFileUrl(file.getFileUrl());
        vo.setAddedBy(resolveStaffName(file.getAddedBy()));
        vo.setCreatedAt(DateTimeConverter.DateTimeConvertFromInstant(file.getCreatedAt()));
        vo.setModifiedAt(DateTimeConverter.DateTimeConvertFromInstant(file.getModifiedAt()));
        return vo;
    }

    private String resolveClientFolderName(String clientId) {
        try {
            ClientsInfo client = clientsInfoRepository.findById(Long.valueOf(clientId)).orElse(null);
            if (client != null) {
                String name = client.getClientFirstName() + "_" + client.getClientLastName();
                // Sanitize: keep only letters, digits, underscores and hyphens
                return name.replaceAll("[^a-zA-Z0-9_\\-]", "_");
            }
        } catch (Exception e) {
            logger.warn("Could not resolve client name for clientId {}, falling back to ID: {}", clientId, e.getMessage());
        }
        return clientId;
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
