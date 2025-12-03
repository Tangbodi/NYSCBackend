package com.example.demo.Service.UsersInfo;

import com.example.demo.Controller.Users;
import com.example.demo.Repository.UserInfoRepository;
import com.example.demo.Repository.UsersLoginRepository;
import com.example.demo.Model.DTO.UserRegisterDTO;
import com.example.demo.Model.Entity.UsersInfo;
import com.example.demo.Model.VO.UserInfoVO;
import com.example.demo.Util.DateTimeConverter;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;


@Service
public class UserInfoService {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoService.class);
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private UsersLoginRepository usersLoginRepository;

    public UsersInfo CheckUsernameExists(String username) {
        logger.info("Checking if username exists: {}", username);
        try {
            UsersInfo usersInfo = userInfoRepository.findByUsername(username).orElse(null);
            if (usersInfo != null) {
                logger.info("Username: {}" + usersInfo.getUsername());
                return usersInfo;
            } else {
                logger.info("Username does not exist: {}");
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to check username: {}", e.getMessage(), e);
        }
        return null;
    }

    public UsersInfo CheckEmailExists(String email) {
        logger.info("Checking if email exists: {}", email);
        try {
            UsersInfo usersInfo = userInfoRepository.findByEmail(email).orElse(null);
            if (usersInfo != null) {
                logger.info("Email: {}" + usersInfo.getEmail());
                return usersInfo;
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
    public void SaveUserInfo(UserRegisterDTO userRegisterDTO) {
        logger.info("Saving UsersInfo");

        try {
            UsersInfo usersInfo = new UsersInfo();
            usersInfo.setId(userRegisterDTO.getUserId());
            usersInfo.setUsername(userRegisterDTO.getUsername());
            usersInfo.setEmail(userRegisterDTO.getEmail());
            usersInfo.setEmployeeType(userRegisterDTO.getEmployeeType());
            usersInfo.setFirstName(userRegisterDTO.getFirstName());
            usersInfo.setLastName(userRegisterDTO.getLastName());
            usersInfo.setMiddleName(userRegisterDTO.getMiddleName());
            usersInfo.setStatus(userRegisterDTO.getStatus() == 1);
            usersInfo.setPhone(userRegisterDTO.getPhone());
            usersInfo.setSupervisor(userRegisterDTO.getSupervisor());
            usersInfo.setTitle(userRegisterDTO.getTitle());
            usersInfo.setCreatedAt(userRegisterDTO.getCreatedAt());
            usersInfo.setModifiedAt(userRegisterDTO.getCreatedAt());

            userInfoRepository.save(usersInfo);
            logger.info("UsersInfo saved successfully");

        } catch (Exception e) {
            logger.error("Failed to save UsersInfo: {}", e.getMessage(), e);
            throw e;   // <--- rethrow EXACT exception
        }
    }

    public UserInfoVO GetUserInfo(Long userId, HttpServletRequest request) {
        logger.info("Getting UsersInfo: {}" + userId);
        try {
            UsersInfo usersInfo = userInfoRepository.findById(userId).orElse(null);
            if (usersInfo != null) {
                logger.info("Found userInfo: {}" + usersInfo.getUsername());
                UserInfoVO userInfoVO = new UserInfoVO();
                userInfoVO.setLongUid(usersInfo.getId());
                userInfoVO.setUserId(usersInfo.getId().toString());
                userInfoVO.setJSESSIONID(request.getSession().getId());
                logger.info("Session userId: {}", request.getSession().getAttribute("userId"));
                logger.info("Session userName: {}", request.getSession().getAttribute("userName"));
                logger.info("JSESSIONID: {}", request.getSession().getId());

                userInfoVO.setUsername(usersInfo.getUsername());
                userInfoVO.setFirstName(usersInfo.getFirstName());
                userInfoVO.setMiddleName(usersInfo.getMiddleName());
                userInfoVO.setLastName(usersInfo.getLastName());
                userInfoVO.setPhone(usersInfo.getPhone());
                userInfoVO.setTitle(usersInfo.getTitle());
                userInfoVO.setEmail(usersInfo.getEmail());
                userInfoVO.setEmployeeType(usersInfo.getEmployeeType());
                userInfoVO.setSupervisor(usersInfo.getSupervisor());
                String formattedCreatedDateTime = DateTimeConverter.DateTimeConvertFromInstant(usersInfo.getCreatedAt());
                String formattedModifiedDateTime = DateTimeConverter.DateTimeConvertFromInstant(usersInfo.getModifiedAt());
                userInfoVO.setCreatedAt(formattedCreatedDateTime);
                userInfoVO.setModifiedAt(formattedModifiedDateTime);
                return userInfoVO;
            } else {
                logger.info("UserInfo does not exist: {}");
                return null;
            }

        } catch (Exception e) {
            logger.error("Failed to get UsersInfo: {}", e.getMessage(), e);
        }
        return null;
    }


}
