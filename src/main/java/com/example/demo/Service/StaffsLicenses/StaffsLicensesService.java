package com.example.demo.Service.StaffsLicenses;

import com.example.demo.Model.Entity.StaffsLicenses;
import com.example.demo.Repository.StaffsLicensesRepository;
import com.example.demo.Service.ClientsReferringProviders.ClientsReferringProvidersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class StaffsLicensesService {
    private static final Logger logger = LoggerFactory.getLogger(StaffsLicensesService.class);

    @Autowired
    private StaffsLicensesRepository staffsLicensesRepository;

    public void CreateStaffsLicenses(){
        logger.info("Creating StaffsLicenses: {}");
        try{
            StaffsLicenses staffsLicenses = new StaffsLicenses();
            staffsLicenses.setStaffId();
            staffsLicenses.setLicenseName();
            staffsLicenses.setLicenseNumber();
            staffsLicenses.setLicenseState();
            staffsLicenses.setIssueDate();
            staffsLicenses.setExpiredDate();
            staffsLicenses.setNotes();
            staffsLicenses.setCreatedAt(Instant.now());
            staffsLicenses.setModifiedAt(Instant.now());
            staffsLicensesRepository.save(staffsLicenses);
            logger.info("StaffsLicenses created successfully.");
        } catch (Exception e) {
            logger.error("Failed to register ClientsInfo: {}", e.getMessage(), e);
            throw e;
        }
    }
}
