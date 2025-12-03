package com.example.demo.Service.UserLogin;

import com.example.demo.Model.DTO.UserLoginDTO;
import com.example.demo.Model.Entity.UsersInfo;
import com.example.demo.Model.Entity.UsersLogin;
import com.example.demo.Model.VO.UserInfoVO;
import com.example.demo.Repository.UsersLoginRepository;
import com.example.demo.Service.UsersInfo.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);
    @Autowired
    private UsersLoginRepository usersLoginRepository;
    public Boolean CheckCredentials(UserLoginDTO userLoginDTO, HttpServletRequest request) {
        logger.info("Checking credentials for username: {}", userLoginDTO.getUsername());

        // Do NOT wrap in a broad try/catch unless you rethrow
        UsersLogin usersLogin = usersLoginRepository
                .findByUsername(userLoginDTO.getUsername())
                .orElse(null);

        if (usersLogin == null) {
            logger.info("Username does not exist: {}", userLoginDTO.getUsername());
            return false;
        }

        logger.info("Found username: {}", usersLogin.getUsername());
        logger.info("Checking password for user: {}", userLoginDTO.getUsername());

        if (!BCrypt.checkpw(userLoginDTO.getPassword(), usersLogin.getPassword())) {
            logger.info("Password is incorrect for user: {}", usersLogin.getUsername());
            return false;
        }

        logger.info("Password is correct for user: {}", usersLogin.getUsername());

        // set session
        var session = request.getSession(true);
        session.setAttribute("userId", usersLogin.getId());
        session.setAttribute("userName", usersLogin.getUsername());
        logger.info("Session userId: {}", session.getAttribute("userId"));
        logger.info("Session userName: {}", session.getAttribute("userName"));

        return true;
    }

}
