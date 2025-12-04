package com.example.demo.Service.StaffsInfo;

import com.example.demo.Model.DTO.StaffInfoDTO;
import com.example.demo.Model.Entity.StaffsInfo;
import com.example.demo.Repository.StaffInfoRepository;
import com.example.demo.Repository.StaffLoginRepository;
import com.example.demo.Model.DTO.StaffRegisterDTO;
import com.example.demo.Model.VO.StaffInfoVO;
import com.example.demo.Util.DateTimeConverter;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.Instant;


@Service
public class StaffsInfoService {
    private static final Logger logger = LoggerFactory.getLogger(StaffsInfoService.class);
    @Autowired
    private StaffInfoRepository staffInfoRepository;
    @Autowired
    private StaffLoginRepository staffLoginRepository;

    public StaffsInfo CheckUsernameExists(String username) {
        logger.info("Checking if username exists: {}", username);
        try {
            StaffsInfo staffsInfo = staffInfoRepository.findByUsername(username).orElse(null);
            if (staffsInfo != null) {
                logger.info("Username: {}" + staffsInfo.getUsername());
                return staffsInfo;
            } else {
                logger.info("Username does not exist: {}");
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to check username: {}", e.getMessage(), e);
        }
        return null;
    }

    public StaffsInfo CheckEmailExists(String email) {
        logger.info("Checking if email exists: {}", email);
        try {
            StaffsInfo staffsInfo = staffInfoRepository.findByEmail(email).orElse(null);
            if (staffsInfo != null) {
                logger.info("Email: {}" + staffsInfo.getEmail());
                return staffsInfo;
            } else {
                logger.info("Email does not exist: {}");
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to check email: {}", e.getMessage(), e);
        }
        return null;
    }


    @Transactional
    public void SaveUserInfo(StaffRegisterDTO staffRegisterDTO) {
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

    public StaffInfoVO GetUserInfo(Long userId, HttpServletRequest request) {
        logger.info("Getting StaffInfo: {}" + userId);
        try {
            StaffsInfo staffsInfo = staffInfoRepository.findById(userId).orElse(null);
            if (staffsInfo != null) {
                logger.info("Found staffInfo: {}" + staffsInfo.getUsername());
                StaffInfoVO staffInfoVO = new StaffInfoVO();
                staffInfoVO.setLongUid(staffsInfo.getId());
                staffInfoVO.setStaffId(staffsInfo.getId().toString());
                staffInfoVO.setJSESSIONID(request.getSession().getId());
//                logger.info("Session userId: {}", request.getSession().getAttribute("userId"));
//                logger.info("Session userName: {}", request.getSession().getAttribute("userName"));
//                logger.info("JSESSIONID: {}", request.getSession().getId());

                staffInfoVO.setUsername(staffsInfo.getUsername());
                staffInfoVO.setFirstName(staffsInfo.getFirstName());
                staffInfoVO.setMiddleName(staffsInfo.getMiddleName());
                staffInfoVO.setLastName(staffsInfo.getLastName());
                staffInfoVO.setPhone(staffsInfo.getPhone());
                staffInfoVO.setTitle(staffsInfo.getTitle());
                staffInfoVO.setEmail(staffsInfo.getEmail());
                staffInfoVO.setEmployeeType(staffsInfo.getEmployeeType());
                staffInfoVO.setSupervisor(staffsInfo.getSupervisor());
                staffInfoVO.setStatus(staffsInfo.getStatus());
                String formattedCreatedDateTime = DateTimeConverter.DateTimeConvertFromInstant(staffsInfo.getCreatedAt());
                String formattedModifiedDateTime = DateTimeConverter.DateTimeConvertFromInstant(staffsInfo.getModifiedAt());
                staffInfoVO.setCreatedAt(formattedCreatedDateTime);
                staffInfoVO.setModifiedAt(formattedModifiedDateTime);
                return staffInfoVO;
            } else {
                logger.info("StaffInfo does not exist: {}");
                return null;
            }

        } catch (Exception e) {
            logger.error("Failed to get StaffInfo: {}", e.getMessage(), e);
        }
        return null;
    }
    public StaffInfoVO UpdateUserInfo(StaffInfoDTO staffInfoDTO, HttpServletRequest request){
        logger.info("Updating StaffInfo: {}" + staffInfoDTO.getStaffId());
        try {
            StaffsInfo staffsInfo = staffInfoRepository.findById(staffInfoDTO.getStaffId()).orElse(null);
            if (staffsInfo != null) {
                logger.info("Found staffInfo: {}" + staffsInfo.getUsername());
                return SaveUserInfo(staffsInfo, staffInfoDTO, request);
            } else {
                logger.info("StaffInfo does not exist: {}");
                return null;
            }

        } catch (Exception e) {
            logger.error("Failed to get StaffInfo: {}", e.getMessage(), e);
        }
        return null;
    }
    @Transactional
    public StaffInfoVO SaveUserInfo(StaffsInfo staffsInfo, StaffInfoDTO staffInfoDTO, HttpServletRequest request) {
        logger.info("Saving StaffInfo: {}" + staffInfoDTO.getStaffId());
        try {
//            usersInfo.setId(userInfoDTO.getUserId());
            staffsInfo.setUsername(staffInfoDTO.getUsername());
            staffsInfo.setEmail(staffInfoDTO.getEmail());
            staffsInfo.setEmployeeType(staffInfoDTO.getEmployeeType());
            staffsInfo.setFirstName(staffInfoDTO.getFirstName());
            staffsInfo.setLastName(staffInfoDTO.getLastName());
            staffsInfo.setMiddleName(staffInfoDTO.getMiddleName());
            staffsInfo.setStatus(staffInfoDTO.getStatus());
            staffsInfo.setPhone(staffInfoDTO.getPhone());
            staffsInfo.setSupervisor(staffInfoDTO.getSupervisor());
            staffsInfo.setTitle(staffInfoDTO.getTitle());
            staffsInfo.setStatus(staffInfoDTO.getStatus());
            staffsInfo.setModifiedAt(Instant.now());
            staffInfoRepository.save(staffsInfo);
            logger.info("StaffInfo updated successfully");

        } catch (Exception e) {
            logger.error("Failed to save StaffInfo: {}", e.getMessage(), e);
            throw e;   // <--- rethrow EXACT exception
        }
       return GetUserInfo(staffInfoDTO.getStaffId(), request);
    }
}
