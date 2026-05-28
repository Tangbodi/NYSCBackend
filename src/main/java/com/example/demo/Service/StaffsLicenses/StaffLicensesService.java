package com.example.demo.Service.StaffsLicenses;

import com.example.demo.Model.DTO.StaffLicensesDTO;
import com.example.demo.Model.Entity.StaffLicenses;
import com.example.demo.Model.VO.StaffLicensesVO;
import com.example.demo.Repository.StaffLicensesRepository;
import com.example.demo.Util.DateTimeConverter;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class StaffLicensesService {
    private static final Logger logger = LoggerFactory.getLogger(StaffLicensesService.class);

    @Autowired
    private StaffLicensesRepository staffLicensesRepository;

    @Transactional
    public void CreateStaffsLicenses(StaffLicensesDTO staffLicensesDTO){
        logger.info("Creating StaffsLicenses: {}");
        try{
            StaffLicenses staffLicenses = new StaffLicenses();
            Long snowflakeId = System.currentTimeMillis();
            staffLicenses.setId(snowflakeId);
            staffLicenses.setStaffId(Long.valueOf(staffLicensesDTO.getStaffId()));
            staffLicenses.setLicenseName(staffLicensesDTO.getLicenseName());
            staffLicenses.setLicenseNumber(staffLicensesDTO.getLicenseNumber());
            staffLicenses.setLicenseState(staffLicensesDTO.getLicenseState());
            staffLicenses.setIssueDate(staffLicensesDTO.getIssueDate());
            staffLicenses.setExpiredDate(staffLicensesDTO.getExpiredDate());
            staffLicenses.setNotes(staffLicensesDTO.getNotes());
            staffLicenses.setCreatedAt(Instant.now());
            staffLicenses.setModifiedAt(Instant.now());
            staffLicensesRepository.save(staffLicenses);
            logger.info("StaffsLicenses created successfully.");
        } catch (Exception e) {
            logger.error("Failed to create StaffsLicenses: {}", e.getMessage(), e);
            throw e;
        }
    }
    public List<StaffLicensesVO> GetLicensesByStaff(String staffId){
        logger.info("Getting all licenses by staff: {}");
        try{
            List<Map<String,Object>> licensesList = staffLicensesRepository.findLicensesByStaffId(Long.valueOf(staffId));
            if (!licensesList.isEmpty()) {
                logger.info("Found licensesList.");
                List<StaffLicensesVO> clientsFundersVOList = new ArrayList<>();
                for(Map<String,Object> license: licensesList){
                    StaffLicensesVO staffLicensesVO = new StaffLicensesVO();
                    staffLicensesVO.setLicenseId(String.valueOf(license.get("staff_license_id")));
                    staffLicensesVO.setStaffId(String.valueOf(license.get("staff_id")));
                    staffLicensesVO.setLicenseName(String.valueOf(license.get("license_name")));
                    staffLicensesVO.setLicenseNumber(String.valueOf(license.get("license_number")));
                    staffLicensesVO.setLicenseState(String.valueOf(license.get("license_state")));
                    staffLicensesVO.setIssueDate(String.valueOf(license.get("issue_date")));
                    staffLicensesVO.setExpiredDate(String.valueOf(license.get("expired_date")));
                    staffLicensesVO.setNotes(String.valueOf(license.get("notes")));
                    staffLicensesVO.setCreatedAt(String.valueOf(license.get("created_at")));
                    staffLicensesVO.setModifiedAt(String.valueOf(license.get("modified_at")));
                    clientsFundersVOList.add(staffLicensesVO);
                }
                return clientsFundersVOList;
            }else{
                logger.info("No licensesList found.");
                return Collections.emptyList();
            }
        }catch (Exception e) {
            logger.error("Failed to get licensesList: {}", e.getMessage(), e);
        }
        return Collections.emptyList();
    }
    public StaffLicensesVO GetStaffsLicenses(String licenseId){
        logger.info("Getting StaffsLicenses: {}");
        try{
            StaffLicenses staffLicenses = staffLicensesRepository.findById(Long.valueOf(licenseId)).orElse(null);
            if(staffLicenses != null){
                logger.info("Found StaffsLicenses.");
                return ConvertToStaffsLicensesVO(staffLicenses);
            }else{
                logger.info("No StaffsLicenses found.");
            }
        }catch (Exception e) {
            logger.error("Failed to get StaffsLicenses: {}", e.getMessage(), e);
        }
        return null;
    }
    @Transactional
    public void UpdateStaffsLicenses(StaffLicensesDTO staffLicensesDTO){
        logger.info("Updating StaffsLicenses: {}", "License ID:"+ staffLicensesDTO.getLicenseId(),"Staff ID:"+ staffLicensesDTO.getStaffId());

        try{
            staffLicensesRepository.updateStaffLicenseByStaffIdAndLicenseId(Long.valueOf(staffLicensesDTO.getLicenseId()), Long.valueOf(staffLicensesDTO.getStaffId()),
                    staffLicensesDTO.getLicenseName(),
                    staffLicensesDTO.getLicenseNumber(),
                    staffLicensesDTO.getLicenseState(),
                    staffLicensesDTO.getIssueDate(),
                    staffLicensesDTO.getExpiredDate(),
                    staffLicensesDTO.getNotes()
            );
        }catch (Exception e) {
            logger.error("Failed to update StaffsLicenses: {}", e.getMessage(), e);
        }
    }
    @Transactional
    public void DeleteStaffLicense(String licenseId) {
        logger.info("Deleting StaffLicense: {}", licenseId);
        try {
            Long id = Long.valueOf(licenseId);
            if (!staffLicensesRepository.existsById(id)) {
                throw new RuntimeException("License not found for id: " + licenseId);
            }
            staffLicensesRepository.deleteById(id);
            logger.info("StaffLicense deleted successfully.");
        } catch (Exception e) {
            logger.error("Failed to delete StaffLicense: {}", e.getMessage(), e);
            throw e;
        }
    }

    public StaffLicensesVO ConvertToStaffsLicensesVO(StaffLicenses staffLicenses){
        logger.info("Converting to StaffsLicensesVO.");
        StaffLicensesVO staffLicensesVO = new StaffLicensesVO();
        staffLicensesVO.setLicenseId(String.valueOf(staffLicenses.getId()));
        staffLicensesVO.setStaffId(String.valueOf(staffLicenses.getStaffId()));
        staffLicensesVO.setLicenseName(staffLicenses.getLicenseName());
        staffLicensesVO.setLicenseNumber(staffLicenses.getLicenseNumber());
        staffLicensesVO.setLicenseState(staffLicenses.getLicenseState());
        staffLicensesVO.setIssueDate(staffLicenses.getIssueDate());
        staffLicensesVO.setExpiredDate(staffLicenses.getExpiredDate());
        staffLicensesVO.setNotes(staffLicenses.getNotes());
        String formattedCreatedDateTime = DateTimeConverter.DateTimeConvertFromInstant(staffLicenses.getCreatedAt());
        String formattedModifiedDateTime = DateTimeConverter.DateTimeConvertFromInstant(staffLicenses.getModifiedAt());
        staffLicensesVO.setCreatedAt(formattedCreatedDateTime);
        staffLicensesVO.setModifiedAt(formattedModifiedDateTime);
        logger.info("StaffsLicensesVO converted successfully.");
        return staffLicensesVO;
    }
}
