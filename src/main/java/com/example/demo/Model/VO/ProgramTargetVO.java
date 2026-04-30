package com.example.demo.Model.VO;

import lombok.Data;

@Data
public class ProgramTargetVO {
    private String targetId;
    private String targetName;
    private String objective;
    private String status;
    private String dateOpened;
    private String dateMastered;
    private String createdAt;
    private String modifiedAt;
}
