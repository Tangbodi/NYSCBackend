package com.example.demo.Service.StaffsRegister;

import com.example.demo.Controller.StaffsController;
import com.example.demo.Model.DTO.StaffsRegisterDTO;
import com.example.demo.Model.Entity.StaffsLogin;
import com.example.demo.Repository.StaffsLoginRepository;
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
    private StaffsLoginRepository staffsLoginRepository;
    @Transactional
    public void RegisterStaffs(StaffsRegisterDTO staffsRegisterDTO) {
        logger.info("Registering staffs: {}", staffsRegisterDTO.getUsername());

        try {
            logger.info("Creating UUID for staffs: {}", staffsRegisterDTO.getUsername());
            Long snowflakeId = Snowflake.generateUniqueId();
            staffsRegisterDTO.setStaffId(snowflakeId);

            logger.info("Creating StaffsLogin:{}", staffsRegisterDTO.getUsername());
            StaffsLogin staffsLogin = new StaffsLogin();
            staffsLogin.setId(snowflakeId);
            staffsLogin.setUsername(staffsRegisterDTO.getUsername());

            String encodedPassword = BCrypt.hashpw(staffsRegisterDTO.getPassword(), BCrypt.gensalt());
            staffsLogin.setPassword(encodedPassword);
            staffsLogin.setIsAdmin("0");
            staffsLogin.setCreatedAt(Instant.now());
            staffsLogin.setModifiedAt(Instant.now());

            logger.info("Saving StaffsLogin:{}", staffsRegisterDTO.getUsername());
            if (staffsLoginRepository.save(staffsLogin) == null) {
                throw new StaffsController.UserRegistrationException("Failed to save StaffLogin");
            }
            logger.info("StaffsLogin saved successfully");
            staffsInfoService.SaveStaffsInfo(staffsRegisterDTO);  // <-- may also throw

        }catch (Exception e) {
            logger.error("Failed to register staff: {}", e.getMessage(), e);
            throw e;  // <--- DO NOT wrap, return exact error
        }
    }
}
