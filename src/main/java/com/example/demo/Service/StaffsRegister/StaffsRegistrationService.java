package com.example.demo.Service.StaffsRegister;

import com.example.demo.Controller.StaffsLogin;
import com.example.demo.Model.DTO.StaffsRegisterDTO;
import com.example.demo.Repository.StaffsLoginRepository;
import com.example.demo.Service.StaffsInfo.StaffsInfoService;
import com.example.demo.Service.StaffsPayroll.StaffsPayrollService;
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
    @Autowired
    private StaffsPayrollService staffsPayrollService;
    @Transactional
    public void RegisterStaffsLogin(StaffsRegisterDTO staffsRegisterDTO) {
        logger.info("Registering StaffsLogin: {}", staffsRegisterDTO.getUsername());

        try {
            logger.info("Creating UUID for StaffsLogin: {}", staffsRegisterDTO.getUsername());
            Long snowflakeId = Snowflake.generateUniqueId();
            staffsRegisterDTO.setStaffId(snowflakeId);

            logger.info("Creating StaffsLogin:{}", staffsRegisterDTO.getUsername());
            com.example.demo.Model.Entity.StaffsLogin staffsLogin = new com.example.demo.Model.Entity.StaffsLogin();
            staffsLogin.setId(snowflakeId);
            staffsLogin.setUsername(staffsRegisterDTO.getUsername());

            String encodedPassword = BCrypt.hashpw(staffsRegisterDTO.getPassword(), BCrypt.gensalt());
            staffsLogin.setPassword(encodedPassword);
            staffsLogin.setIsAdmin("0");
            staffsLogin.setCreatedAt(Instant.now());
            staffsLogin.setModifiedAt(Instant.now());

            logger.info("Saving StaffsLogin:{}", staffsRegisterDTO.getUsername());

            if (staffsLoginRepository.save(staffsLogin) == null) {
                throw new StaffsLogin.UserRegistrationException("Failed to register StaffsLogin.");
            }else{
                logger.info("StaffsLogin registered successfully.");
            }

            staffsInfoService.CreateStaffsInfo(staffsRegisterDTO);
            staffsPayrollService.CreateStaffsPayroll(snowflakeId);

        }catch (Exception e) {
            logger.error("Failed to register StaffsLogin: {}", e.getMessage(), e);
            throw e;  // <--- DO NOT wrap, return exact error
        }
    }
}
