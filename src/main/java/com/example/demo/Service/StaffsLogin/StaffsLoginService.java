package com.example.demo.Service.StaffsLogin;

import com.example.demo.Model.DTO.StaffsLoginDTO;
import com.example.demo.Model.Entity.StaffsLogin;
import com.example.demo.Repository.StaffsLoginRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffsLoginService {
    private static final Logger logger = LoggerFactory.getLogger(StaffsLoginService.class);
    @Autowired
    private StaffsLoginRepository staffsLoginRepository;
    public Boolean CheckCredentials(StaffsLoginDTO staffsLoginDTO, HttpServletRequest request) {
        logger.info("Checking credentials for username: {}", staffsLoginDTO.getUsername());

        // Do NOT wrap in a broad try/catch unless you rethrow
        StaffsLogin staffsLogin = staffsLoginRepository
                .findByUsername(staffsLoginDTO.getUsername())
                .orElse(null);

        if (staffsLogin == null) {
            logger.info("Username does not exist: {}", staffsLoginDTO.getUsername());
            return false;
        }

        logger.info("Found username: {}", staffsLogin.getUsername());
        logger.info("Checking password for user: {}", staffsLoginDTO.getUsername());

        if (!BCrypt.checkpw(staffsLoginDTO.getPassword(), staffsLogin.getPassword())) {
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
    public Boolean CheckIsAdmin(Long staffId){
        logger.info("Checking admin status: {}", staffId);
        StaffsLogin staffsLogin = staffsLoginRepository.findById(staffId).orElse(null);
        if(staffsLogin != null){
            logger.info("Found staff: {}", staffId);
            if(staffsLogin.getIsAdmin()=="1"){
                logger.info("You are admin.");
                return true;
            }
        }else{
            logger.info("The staff doesn't exist.");
            return false;
        }
        return false;
    }
}
