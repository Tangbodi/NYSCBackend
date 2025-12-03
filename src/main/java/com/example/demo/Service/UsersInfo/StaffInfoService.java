package com.example.demo.Service.UsersInfo;

import com.example.demo.Model.DTO.StaffInfoDTO;
import com.example.demo.Model.Entity.StaffInfo;
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
public class StaffInfoService {
    private static final Logger logger = LoggerFactory.getLogger(StaffInfoService.class);
    @Autowired
    private StaffInfoRepository staffInfoRepository;
    @Autowired
    private StaffLoginRepository staffLoginRepository;

    public StaffInfo CheckUsernameExists(String username) {
        logger.info("Checking if username exists: {}", username);
        try {
            StaffInfo staffInfo = staffInfoRepository.findByUsername(username).orElse(null);
            if (staffInfo != null) {
                logger.info("Username: {}" + staffInfo.getUsername());
                return staffInfo;
            } else {
                logger.info("Username does not exist: {}");
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to check username: {}", e.getMessage(), e);
        }
        return null;
    }

    public StaffInfo CheckEmailExists(String email) {
        logger.info("Checking if email exists: {}", email);
        try {
            StaffInfo staffInfo = staffInfoRepository.findByEmail(email).orElse(null);
            if (staffInfo != null) {
                logger.info("Email: {}" + staffInfo.getEmail());
                return staffInfo;
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
            StaffInfo staffInfo = new StaffInfo();
            staffInfo.setId(staffRegisterDTO.getStaffId());
            staffInfo.setUsername(staffRegisterDTO.getUsername());
            staffInfo.setEmail(staffRegisterDTO.getEmail());
            staffInfo.setEmployeeType(staffRegisterDTO.getEmployeeType());
            staffInfo.setFirstName(staffRegisterDTO.getFirstName());
            staffInfo.setLastName(staffRegisterDTO.getLastName());
            staffInfo.setMiddleName(staffRegisterDTO.getMiddleName());
            staffInfo.setStatus(staffRegisterDTO.getStatus() == 1);
            staffInfo.setPhone(staffRegisterDTO.getPhone());
            staffInfo.setSupervisor(staffRegisterDTO.getSupervisor());
            staffInfo.setTitle(staffRegisterDTO.getTitle());
            staffInfo.setCreatedAt(staffRegisterDTO.getCreatedAt());
            staffInfo.setModifiedAt(staffRegisterDTO.getCreatedAt());

            staffInfoRepository.save(staffInfo);
            logger.info("StaffInfo saved successfully");

        } catch (Exception e) {
            logger.error("Failed to save StaffInfo: {}", e.getMessage(), e);
            throw e;   // <--- rethrow EXACT exception
        }
    }

    public StaffInfoVO GetUserInfo(Long userId, HttpServletRequest request) {
        logger.info("Getting StaffInfo: {}" + userId);
        try {
            StaffInfo staffInfo = staffInfoRepository.findById(userId).orElse(null);
            if (staffInfo != null) {
                logger.info("Found staffInfo: {}" + staffInfo.getUsername());
                StaffInfoVO staffInfoVO = new StaffInfoVO();
                staffInfoVO.setLongUid(staffInfo.getId());
                staffInfoVO.setStaffId(staffInfo.getId().toString());
                staffInfoVO.setJSESSIONID(request.getSession().getId());
//                logger.info("Session userId: {}", request.getSession().getAttribute("userId"));
//                logger.info("Session userName: {}", request.getSession().getAttribute("userName"));
//                logger.info("JSESSIONID: {}", request.getSession().getId());

                staffInfoVO.setUsername(staffInfo.getUsername());
                staffInfoVO.setFirstName(staffInfo.getFirstName());
                staffInfoVO.setMiddleName(staffInfo.getMiddleName());
                staffInfoVO.setLastName(staffInfo.getLastName());
                staffInfoVO.setPhone(staffInfo.getPhone());
                staffInfoVO.setTitle(staffInfo.getTitle());
                staffInfoVO.setEmail(staffInfo.getEmail());
                staffInfoVO.setEmployeeType(staffInfo.getEmployeeType());
                staffInfoVO.setSupervisor(staffInfo.getSupervisor());
                staffInfoVO.setStatus(staffInfo.getStatus() ? "1" : "0");
                String formattedCreatedDateTime = DateTimeConverter.DateTimeConvertFromInstant(staffInfo.getCreatedAt());
                String formattedModifiedDateTime = DateTimeConverter.DateTimeConvertFromInstant(staffInfo.getModifiedAt());
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
            StaffInfo staffInfo = staffInfoRepository.findById(staffInfoDTO.getStaffId()).orElse(null);
            if (staffInfo != null) {
                logger.info("Found staffInfo: {}" + staffInfo.getUsername());
                return SaveUserInfo(staffInfo, staffInfoDTO, request);
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
    public StaffInfoVO SaveUserInfo(StaffInfo staffInfo, StaffInfoDTO staffInfoDTO, HttpServletRequest request) {
        logger.info("Saving StaffInfo: {}" + staffInfoDTO.getStaffId());
        try {
//            usersInfo.setId(userInfoDTO.getUserId());
            staffInfo.setUsername(staffInfoDTO.getUsername());
            staffInfo.setEmail(staffInfoDTO.getEmail());
            staffInfo.setEmployeeType(staffInfoDTO.getEmployeeType());
            staffInfo.setFirstName(staffInfoDTO.getFirstName());
            staffInfo.setLastName(staffInfoDTO.getLastName());
            staffInfo.setMiddleName(staffInfoDTO.getMiddleName());
            staffInfo.setStatus(staffInfoDTO.getStatus() == 1);
            staffInfo.setPhone(staffInfoDTO.getPhone());
            staffInfo.setSupervisor(staffInfoDTO.getSupervisor());
            staffInfo.setTitle(staffInfoDTO.getTitle());
            staffInfo.setStatus(staffInfoDTO.getStatus() == 1);
            staffInfo.setModifiedAt(Instant.now());
            staffInfoRepository.save(staffInfo);
            logger.info("StaffInfo updated successfully");

        } catch (Exception e) {
            logger.error("Failed to save StaffInfo: {}", e.getMessage(), e);
            throw e;   // <--- rethrow EXACT exception
        }
       return GetUserInfo(staffInfoDTO.getStaffId(), request);
    }
}
