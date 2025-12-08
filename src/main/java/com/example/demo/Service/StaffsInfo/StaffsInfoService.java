package com.example.demo.Service.StaffsInfo;

import com.example.demo.Controller.StaffsLoginController;
import com.example.demo.Model.DTO.StaffsInfoDTO;
import com.example.demo.Model.Entity.StaffsInfo;
import com.example.demo.Repository.StaffsInfoRepository;
import com.example.demo.Repository.StaffsLoginRepository;
import com.example.demo.Model.DTO.StaffsRegisterDTO;
import com.example.demo.Model.VO.StaffsInfoVO;
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
    private StaffsInfoRepository staffsInfoRepository;
    @Autowired
    private StaffsLoginRepository staffsLoginRepository;

    public StaffsInfo CheckUsernameExists(String username) {
        logger.info("Checking if username exists: {}", username);
        try {
            StaffsInfo staffsInfo = staffsInfoRepository.findByUsername(username).orElse(null);
            if (staffsInfo != null) {
                logger.info("Username: {}" + staffsInfo.getUsername());
                return staffsInfo;
            } else {
                logger.info("Username does not exist.");
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
            StaffsInfo staffsInfo = staffsInfoRepository.findByEmail(email).orElse(null);
            if (staffsInfo != null) {
                logger.info("Email: {}" + staffsInfo.getEmail());
                return staffsInfo;
            } else {
                logger.info("Email does not exist.");
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to check email: {}", e.getMessage(), e);
        }
        return null;
    }


    @Transactional
    public void CreateStaffsInfo(StaffsRegisterDTO staffsRegisterDTO) {
        logger.info("Creating StaffInfo: {}" + staffsRegisterDTO.getStaffId());
        try {
            StaffsInfo staffsInfo = new StaffsInfo();
            staffsInfo.setId(staffsRegisterDTO.getStaffId());
            staffsInfo.setUsername(staffsRegisterDTO.getUsername());
            staffsInfo.setEmail(staffsRegisterDTO.getEmail());
            staffsInfo.setEmployeeType(staffsRegisterDTO.getEmployeeType());
            staffsInfo.setFirstName(staffsRegisterDTO.getFirstName());
            staffsInfo.setLastName(staffsRegisterDTO.getLastName());
            staffsInfo.setMiddleName(staffsRegisterDTO.getMiddleName());
            staffsInfo.setStatus(staffsRegisterDTO.getStatus());
            staffsInfo.setPhone(staffsRegisterDTO.getPhone());
            staffsInfo.setSupervisor(staffsRegisterDTO.getSupervisor());
            staffsInfo.setTitle(staffsRegisterDTO.getTitle());
            staffsInfo.setCreatedAt(Instant.now());
            staffsInfo.setModifiedAt(Instant.now());
            logger.info("Saving StaffsInfo:{}", staffsInfo.getUsername());
            if (staffsInfoRepository.save(staffsInfo) == null) {
                throw new StaffsLoginController.UserRegistrationException("Failed to save StaffsLogin.");
            }else{
                logger.info("StaffsInfo saved successfully.");
            }
        } catch (Exception e) {
            logger.error("Failed to save StaffsInfo: {}", e.getMessage(), e);
            throw e;   // <--- rethrow EXACT exception
        }
    }

    public StaffsInfoVO GetStaffsInfo(Long userId, HttpServletRequest request) {
        logger.info("Getting StaffsInfo: {}" + userId);
        try {
            StaffsInfo staffsInfo = staffsInfoRepository.findById(userId).orElse(null);
            if (staffsInfo != null) {
                logger.info("Found StaffsInfo: {}" + staffsInfo.getUsername());
                return ConvertToStaffsInfoVO(staffsInfo, request);
            } else {
                logger.info("StaffsInfo does not exist.");
                return null;
            }

        } catch (Exception e) {
            logger.error("Failed to get StaffsInfo: {}", e.getMessage(), e);
        }
        return null;
    }
    @Transactional
    public StaffsInfoVO UpdateStaffsInfo(StaffsInfoDTO staffsInfoDTO, HttpServletRequest request){
        logger.info("Updating StaffsInfo: {}" + staffsInfoDTO.getStaffId());
        try {
            StaffsInfo staffsInfo = staffsInfoRepository.findById(staffsInfoDTO.getStaffId()).orElse(null);
            if (staffsInfo != null) {
                logger.info("Found StaffsInfo: {}" + staffsInfo.getUsername());
                staffsInfo.setUsername(staffsInfoDTO.getUsername());
                staffsInfo.setEmail(staffsInfoDTO.getEmail());
                staffsInfo.setEmployeeType(staffsInfoDTO.getEmployeeType());
                staffsInfo.setFirstName(staffsInfoDTO.getFirstName());
                staffsInfo.setLastName(staffsInfoDTO.getLastName());
                staffsInfo.setMiddleName(staffsInfoDTO.getMiddleName());
                staffsInfo.setStatus(staffsInfoDTO.getStatus());
                staffsInfo.setPhone(staffsInfoDTO.getPhone());
                staffsInfo.setSupervisor(staffsInfoDTO.getSupervisor());
                staffsInfo.setTitle(staffsInfoDTO.getTitle());
                staffsInfo.setStatus(staffsInfoDTO.getStatus());
                staffsInfo.setModifiedAt(Instant.now());
                staffsInfoRepository.save(staffsInfo);
                logger.info("StaffsInfo updated successfully.");
                return GetStaffsInfo(staffsInfo.getId(), request);
            } else {
                logger.info("StaffsInfo does not exist.");
                return null;
            }

        } catch (Exception e) {
            logger.error("Failed to get StaffsInfo: {}", e.getMessage(), e);
        }
        return null;
    }
    public StaffsInfoVO ConvertToStaffsInfoVO( StaffsInfo staffsInfo, HttpServletRequest request){
        logger.info("Converting to StaffsInfoVO: {}", staffsInfo.getId());
        StaffsInfoVO staffsInfoVO = new StaffsInfoVO();
        staffsInfoVO.setLongUid(staffsInfo.getId());
        staffsInfoVO.setStaffId(staffsInfo.getId().toString());
        staffsInfoVO.setJSESSIONID(request.getSession().getId());
//                logger.info("Session userId: {}", request.getSession().getAttribute("userId"));
//                logger.info("Session userName: {}", request.getSession().getAttribute("userName"));
//                logger.info("JSESSIONID: {}", request.getSession().getId());

        staffsInfoVO.setUsername(staffsInfo.getUsername());
        staffsInfoVO.setFirstName(staffsInfo.getFirstName());
        staffsInfoVO.setMiddleName(staffsInfo.getMiddleName());
        staffsInfoVO.setLastName(staffsInfo.getLastName());
        staffsInfoVO.setPhone(staffsInfo.getPhone());
        staffsInfoVO.setTitle(staffsInfo.getTitle());
        staffsInfoVO.setEmail(staffsInfo.getEmail());
        staffsInfoVO.setEmployeeType(staffsInfo.getEmployeeType());
        staffsInfoVO.setSupervisor(staffsInfo.getSupervisor());
        staffsInfoVO.setStatus(staffsInfo.getStatus());
        String formattedCreatedDateTime = DateTimeConverter.DateTimeConvertFromInstant(staffsInfo.getCreatedAt());
        String formattedModifiedDateTime = DateTimeConverter.DateTimeConvertFromInstant(staffsInfo.getModifiedAt());
        staffsInfoVO.setCreatedAt(formattedCreatedDateTime);
        staffsInfoVO.setModifiedAt(formattedModifiedDateTime);
        logger.info("StaffsInfoVO converted successfully.");
        return staffsInfoVO;
    }
}
