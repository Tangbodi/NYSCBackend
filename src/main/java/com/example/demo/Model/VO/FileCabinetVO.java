package com.example.demo.Model.VO;

import lombok.Data;

@Data
public class FileCabinetVO {
    private String fileId;
    private String clientId;
    private String fileName;
    private String tag;
    private String fileType;
    private String filePath;
    private String fileUrl;
    private String addedBy;
    private String createdAt;
    private String modifiedAt;
}
