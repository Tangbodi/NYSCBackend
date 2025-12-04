package com.example.demo.Service.StaffsRegister;

import com.example.demo.Controller.StaffController;
import com.example.demo.Model.DTO.StaffRegisterDTO;
import com.example.demo.Model.Entity.StaffsLogin;
import com.example.demo.Repository.StaffLoginRepository;
import com.example.demo.Service.StaffsInfo.StaffsInfoService;
import com.example.demo.Util.Snowflake;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
@Service
public class StaffsRegistrationService {
    private static final Logger logger = LoggerFactory.getLogger(StaffsRegistrationService.class);
    @Autowired
    private StaffsInfoService staffsInfoService;
    @Autowired
    private StaffLoginRepository staffLoginRepository;
    @Transactional
    public StaffsLogin RegisterUser(StaffRegisterDTO staffRegisterDTO) {
        logger.info("Registering staff: {}", staffRegisterDTO.getUsername());

        try {
            logger.info("Creating UUID for staff: {}", staffRegisterDTO.getUsername());
            Long snowflakeId = Snowflake.generateUniqueId();
            staffRegisterDTO.setStaffId(snowflakeId);

            logger.info("Creating StaffLogin:{}", staffRegisterDTO.getUsername());
            StaffsLogin staffsLogin = new StaffsLogin();
            staffsLogin.setId(snowflakeId);
            staffsLogin.setUsername(staffRegisterDTO.getUsername());

            String encodedPassword = BCrypt.hashpw(staffRegisterDTO.getPassword(), BCrypt.gensalt());
            staffsLogin.setPassword(encodedPassword);
            staffsLogin.setIsAdmin("0");
            staffsLogin.setCreatedAt(Instant.now());
            staffsLogin.setModifiedAt(Instant.now());

            logger.info("Saving StaffLogin:{}", staffRegisterDTO.getUsername());
            StaffsLogin savedStaff= staffLoginRepository.save(staffsLogin);
            if (savedStaff == null) {
                throw new StaffController.UserRegistrationException("Failed to save StaffLogin");
            }
            logger.info("StaffLogin saved successfully");
            staffsInfoService.SaveStaffInfo(staffRegisterDTO);  // <-- may also throw
            return savedStaff;
        }catch (Exception e) {
            logger.error("Failed to register staff: {}", e.getMessage(), e);
            throw e;  // <--- DO NOT wrap, return exact error
        }
    }
}
