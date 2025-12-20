package com.example.demo.Service.StaffsLicenses;

import com.example.demo.Model.DTO.StaffsLicensesDTO;
import com.example.demo.Model.Entity.StaffsLicenses;
import com.example.demo.Model.VO.StaffsLicensesVO;
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

    public void CreateStaffsLicenses(StaffsLicensesDTO staffsLicensesDTO){
        logger.info("Creating StaffsLicenses: {}");
        try{
            StaffsLicenses staffsLicenses = new StaffsLicenses();

            staffsLicenses.setStaffId(Long.valueOf(staffsLicensesDTO.getStaffId()));
            staffsLicenses.setLicenseName(staffsLicensesDTO.getLicenseName());
            staffsLicenses.setLicenseNumber(staffsLicensesDTO.getLicenseNumber());
            staffsLicenses.setLicenseState(staffsLicensesDTO.getLicenseState());
            staffsLicenses.setIssueDate(staffsLicensesDTO.getIssueDate());
            staffsLicenses.setExpiredDate(staffsLicensesDTO.getExpiredDate());
            staffsLicenses.setNotes(staffsLicensesDTO.getNotes());
            staffsLicenses.setCreatedAt(Instant.now());
            staffsLicenses.setModifiedAt(Instant.now());
            staffsLicensesRepository.save(staffsLicenses);
            logger.info("StaffsLicenses created successfully.");
        } catch (Exception e) {
            logger.error("Failed to create StaffsLicenses: {}", e.getMessage(), e);
            throw e;
        }
    }
    public StaffsLicensesVO GetStaffsLicenses(String licenseId){
        logger.info("Getting StaffsLicenses: {}");
        try{
            StaffsLicenses staffsLicenses = staffsLicensesRepository.findById(Long.valueOf(licenseId)).orElse(null);
            if(staffsLicenses != null){
                logger.info("Found StaffsLicenses.");
                return ConvertToStaffsLicensesVO(staffsLicenses);
            }else{
                logger.info("No StaffsLicenses found.");
            }
        }
    }
    public StaffsLicensesVO ConvertToStaffsLicensesVO(StaffsLicenses staffsLicenses){
        logger.info("Converting to StaffsLicensesVO.");
        StaffsLicensesVO staffsLicensesVO = new StaffsLicensesVO();

    }
}
