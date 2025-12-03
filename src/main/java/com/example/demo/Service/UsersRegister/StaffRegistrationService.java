package com.example.demo.Service.UsersRegister;

import com.example.demo.Controller.StaffController;
import com.example.demo.Model.DTO.StaffRegisterDTO;
import com.example.demo.Model.Entity.StaffsLogin;
import com.example.demo.Repository.StaffLoginRepository;
import com.example.demo.Service.UsersInfo.StaffInfoService;
import com.example.demo.Util.Snowflake;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
@Service
public class StaffRegistrationService {
    private static final Logger logger = LoggerFactory.getLogger(StaffRegistrationService.class);
    @Autowired
    private StaffInfoService staffInfoService;
    @Autowired
    private StaffLoginRepository staffLoginRepository;
    @Transactional
    public StaffsLogin RegisterUser(StaffRegisterDTO staffRegisterDTO) {
        logger.info("Registering staff: {}", staffRegisterDTO.getUsername());

        try {
            logger.info("Creating UUID for staff: {}", staffRegisterDTO.getUsername());
            Long snowflakeId = Snowflake.generateUniqueId();
            staffRegisterDTO.setStaffId(snowflakeId);
            staffRegisterDTO.setCreatedAt(Instant.now());

            logger.info("Saving staff:{}", staffRegisterDTO.getUsername());
            StaffsLogin staffsLogin = new StaffsLogin();
            staffsLogin.setId(snowflakeId);
            staffsLogin.setUsername(staffRegisterDTO.getUsername());

            String encodedPassword = BCrypt.hashpw(staffRegisterDTO.getPassword(), BCrypt.gensalt());
            staffsLogin.setPassword(encodedPassword);
            staffsLogin.setIsAdmin(false);
            staffsLogin.setCreatedAt(staffRegisterDTO.getCreatedAt());
            staffsLogin.setModifiedAt(staffRegisterDTO.getCreatedAt());

            StaffsLogin savedStaff= staffLoginRepository.save(staffsLogin);
            if (savedStaff == null) {
                throw new StaffController.UserRegistrationException("Failed to save StaffLogin");
            }

            logger.info("StaffLogin saved successfully");
            staffInfoService.SaveUserInfo(staffRegisterDTO);  // <-- may also throw

            return savedStaff;
        }catch (Exception e) {
            logger.error("Failed to register staff: {}", e.getMessage(), e);
            throw e;  // <--- DO NOT wrap, return exact error
        }
    }
}
