package com.example.demo.Service.ClientsInfo;

import com.example.demo.Controller.StaffController;
import com.example.demo.Model.DTO.ClientRegisterDTO;
import com.example.demo.Model.DTO.StaffRegisterDTO;
import com.example.demo.Model.Entity.ClientsInfo;
import com.example.demo.Model.Entity.StaffsInfo;
import com.example.demo.Model.Entity.StaffsLogin;
import com.example.demo.Service.StaffsInfo.StaffsInfoService;
import com.example.demo.Util.Snowflake;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ClientsInfoService {
    private static final Logger logger = LoggerFactory.getLogger(ClientsInfoService.class);

    @Transactional
    public void SaveClientInfo(ClientRegisterDTO clientRegisterDTO) {
        logger.info("Registering client: {}", clientRegisterDTO.getClientFirstName() + "."+clientRegisterDTO.getClientFirstName());

        try {
            logger.info("Creating UUID for client: {}", clientRegisterDTO.getClientFirstName());
            Long snowflakeId = Snowflake.generateUniqueId();

            logger.info("Creating ClientInfo:{}", clientRegisterDTO.getClientFirstName());
            ClientsInfo clientsInfo = new ClientsInfo();
            clientsInfo.setId(snowflakeId);

            String encodedPassword = BCrypt.hashpw(staffRegisterDTO.getPassword(), BCrypt.gensalt());
            staffsLogin.setPassword(encodedPassword);
            staffsLogin.setIsAdmin("0");
            staffsLogin.setCreatedAt(staffRegisterDTO.getCreatedAt());
            staffsLogin.setModifiedAt(staffRegisterDTO.getCreatedAt());

            StaffsLogin savedStaff= staffLoginRepository.save(staffsLogin);
            if (savedStaff == null) {
                throw new StaffController.UserRegistrationException("Failed to save StaffLogin");
            }

            logger.info("StaffLogin saved successfully");
            staffsInfoService.SaveUserInfo(staffRegisterDTO);  // <-- may also throw

            return savedStaff;
        }catch (Exception e) {
            logger.error("Failed to register staff: {}", e.getMessage(), e);
            throw e;  // <--- DO NOT wrap, return exact error
        }
}
