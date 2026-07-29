package com.example.demo.Service.StaffsRegister;

import com.example.demo.Controller.StaffsLoginController;
import com.example.demo.Model.DTO.StaffsRegisterDTO;
import com.example.demo.Model.Entity.BCBAInfo;
import com.example.demo.Model.Entity.StaffsLogin;
import com.example.demo.Repository.BCBAInfoRepository;
import com.example.demo.Repository.StaffsLoginRepository;
import com.example.demo.Service.StaffsInfo.StaffsInfoService;
import com.example.demo.Service.StaffsPayroll.StaffsPayrollService;

import com.example.demo.Util.DateTimeConverter;
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
    @Autowired
    private BCBAInfoRepository bcbaInfoRepository;
    @Transactional
    public void RegisterStaffsLogin(StaffsRegisterDTO staffsRegisterDTO) {
        logger.info("Registering StaffsLogin: {}", staffsRegisterDTO.getUsername());

        try {
            logger.info("Creating UUID for StaffsLogin: {}", staffsRegisterDTO.getUsername());
            Long snowflakeId = System.currentTimeMillis();
            staffsRegisterDTO.setStaffId(snowflakeId);

            logger.info("Creating StaffsLogin:{}", staffsRegisterDTO.getUsername());
            StaffsLogin staffsLogin = new StaffsLogin();
            staffsLogin.setId(snowflakeId);
            staffsLogin.setUsername(staffsRegisterDTO.getUsername());

            String encodedPassword = BCrypt.hashpw(staffsRegisterDTO.getPassword(), BCrypt.gensalt());
            staffsLogin.setPassword(encodedPassword);
            staffsLogin.setIsAdmin("0");
            staffsLogin.setCreatedAt(DateTimeConverter.nowNyc());
            staffsLogin.setModifiedAt(DateTimeConverter.nowNyc());

            logger.info("Saving StaffsLogin:{}", staffsRegisterDTO.getUsername());

            if (staffsLoginRepository.save(staffsLogin) == null) {
                throw new StaffsLoginController.UserRegistrationException("Failed to register StaffsLogin.");
            }else{
                logger.info("StaffsLogin registered successfully.");
            }

            staffsInfoService.CreateStaffsInfo(staffsRegisterDTO);
            staffsPayrollService.CreateStaffsPayroll(snowflakeId);

            // If title is BCBA, also register in bcba_info
            if ("BCBA".equalsIgnoreCase(staffsRegisterDTO.getTitle())) {
                logger.info("Title is BCBA — registering staff {} in bcba_info.", snowflakeId);
                BCBAInfo bcbaInfo = new BCBAInfo();
                bcbaInfo.setId(snowflakeId);
                bcbaInfo.setNpiNumber("");
                bcbaInfo.setMedicaidId("");
                bcbaInfo.setCreatedAt(Instant.now());
                bcbaInfo.setModifiedAt(Instant.now());
                bcbaInfoRepository.save(bcbaInfo);
                logger.info("Staff registered in bcba_info successfully.");
            }

        }catch (Exception e) {
            logger.error("Failed to register StaffsLogin: {}", e.getMessage(), e);
            throw e;  // <--- DO NOT wrap, return exact error
        }
    }
}
