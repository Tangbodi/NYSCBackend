package com.example.demo.Service.ClientsInfo;

import com.example.demo.Model.DTO.StaffRegisterDTO;
import com.example.demo.Model.Entity.StaffsInfo;
import com.example.demo.Service.StaffsInfo.StaffsInfoService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ClientsInfoService {
    private static final Logger logger = LoggerFactory.getLogger(ClientsInfoService.class);

    @Transactional
    public void SaveClientInfo(StaffRegisterDTO staffRegisterDTO) {
        logger.info("Saving StaffInfo: {}" + staffRegisterDTO.getStaffId());
        try {
            StaffsInfo staffsInfo = new StaffsInfo();
            staffsInfo.setId(staffRegisterDTO.getStaffId());
            staffsInfo.setUsername(staffRegisterDTO.getUsername());
            staffsInfo.setEmail(staffRegisterDTO.getEmail());
            staffsInfo.setEmployeeType(staffRegisterDTO.getEmployeeType());
            staffsInfo.setFirstName(staffRegisterDTO.getFirstName());
            staffsInfo.setLastName(staffRegisterDTO.getLastName());
            staffsInfo.setMiddleName(staffRegisterDTO.getMiddleName());
            staffsInfo.setStatus(staffRegisterDTO.getStatus());
            staffsInfo.setPhone(staffRegisterDTO.getPhone());
            staffsInfo.setSupervisor(staffRegisterDTO.getSupervisor());
            staffsInfo.setTitle(staffRegisterDTO.getTitle());
            staffsInfo.setCreatedAt(staffRegisterDTO.getCreatedAt());
            staffsInfo.setModifiedAt(staffRegisterDTO.getCreatedAt());

            staffInfoRepository.save(staffsInfo);
            logger.info("StaffInfo saved successfully");

        } catch (Exception e) {
            logger.error("Failed to save StaffInfo: {}", e.getMessage(), e);
            throw e;   // <--- rethrow EXACT exception
        }
    }
}
