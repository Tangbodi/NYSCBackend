package com.example.demo.Service.UsersRegister;

import com.example.demo.Controller.Users;
import com.example.demo.Model.DTO.UserRegisterDTO;
import com.example.demo.Model.Entity.UsersLogin;
import com.example.demo.Repository.UsersLoginRepository;
import com.example.demo.Service.UsersInfo.UserInfoService;
import com.example.demo.Util.Snowflake;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
@Service
public class UserRegistrationService {
    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationService.class);
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UsersLoginRepository usersLoginRepository;
    @Transactional
    public UsersLogin RegisterUser(UserRegisterDTO userRegisterDTO) {
        logger.info("Registering user: {}", userRegisterDTO.getUsername());

        try {
            logger.info("Creating UUID for user: {}", userRegisterDTO.getUsername());
            Long snowflakeId = Snowflake.generateUniqueId();
            userRegisterDTO.setUserId(snowflakeId);
            userRegisterDTO.setCreatedAt(Instant.now());

            logger.info("Saving User :{}", userRegisterDTO.getUsername());
            UsersLogin usersLogin = new UsersLogin();
            usersLogin.setId(snowflakeId);
            usersLogin.setUsername(userRegisterDTO.getUsername());

            String encodedPassword = BCrypt.hashpw(userRegisterDTO.getPassword(), BCrypt.gensalt());
            usersLogin.setPassword(encodedPassword);
            usersLogin.setIsAdmin(false);
            usersLogin.setCreatedAt(userRegisterDTO.getCreatedAt());
            usersLogin.setModifiedAt(userRegisterDTO.getCreatedAt());

            UsersLogin savedUser = usersLoginRepository.save(usersLogin);
            if (savedUser == null) {
                throw new Users.UserRegistrationException("Failed to save UsersLogin");
            }

            logger.info("UsersLogin saved successfully");
            userInfoService.SaveUserInfo(userRegisterDTO);  // <-- may also throw

            return savedUser;
        }catch (Exception e) {
            logger.error("Failed to register user: {}", e.getMessage(), e);
            throw e;  // <--- DO NOT wrap, return exact error
        }
    }
}
