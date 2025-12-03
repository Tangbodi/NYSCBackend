package com.example.demo.Service.UserLogin;

import com.example.demo.Model.DTO.StaffLoginDTO;
import com.example.demo.Model.Entity.StaffsLogin;
import com.example.demo.Repository.StaffLoginRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffLoginService {
    private static final Logger logger = LoggerFactory.getLogger(StaffLoginService.class);
    @Autowired
    private StaffLoginRepository staffLoginRepository;
    public Boolean CheckCredentials(StaffLoginDTO staffLoginDTO, HttpServletRequest request) {
        logger.info("Checking credentials for username: {}", staffLoginDTO.getUsername());

        // Do NOT wrap in a broad try/catch unless you rethrow
        StaffsLogin staffsLogin = staffLoginRepository
                .findByUsername(staffLoginDTO.getUsername())
                .orElse(null);

        if (staffsLogin == null) {
            logger.info("Username does not exist: {}", staffLoginDTO.getUsername());
            return false;
        }

        logger.info("Found username: {}", staffsLogin.getUsername());
        logger.info("Checking password for user: {}", staffLoginDTO.getUsername());

        if (!BCrypt.checkpw(staffLoginDTO.getPassword(), staffsLogin.getPassword())) {
            logger.info("Password is incorrect for user: {}", staffsLogin.getUsername());
            return false;
        }

        logger.info("Password is correct for user: {}", staffsLogin.getUsername());

        // set session
        var session = request.getSession(true);
        session.setAttribute("staffId", staffsLogin.getId());
        session.setAttribute("userName", staffsLogin.getUsername());
        logger.info("Session staffId: {}", session.getAttribute("staffId"));
        logger.info("Session userName: {}", session.getAttribute("userName"));

        return true;
    }

}
