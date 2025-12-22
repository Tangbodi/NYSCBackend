package com.example.demo.Service.StaffsLicenses;

import com.example.demo.Model.DTO.StaffsLicensesDTO;
import com.example.demo.Model.Entity.StaffsLicenses;
import com.example.demo.Model.VO.ClientsFundersVO;
import com.example.demo.Model.VO.StaffsLicensesVO;
import com.example.demo.Repository.StaffsLicensesRepository;
import com.example.demo.Service.ClientsReferringProviders.ClientsReferringProvidersService;
import com.example.demo.Util.DateTimeConverter;
import com.example.demo.Util.Snowflake;
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
public class StaffsLicensesService {
    private static final Logger logger = LoggerFactory.getLogger(StaffsLicensesService.class);

    @Autowired
    private StaffsLicensesRepository staffsLicensesRepository;

    @Transactional
    public void CreateStaffsLicenses(StaffsLicensesDTO staffsLicensesDTO){
        logger.info("Creating StaffsLicenses: {}");
        try{
            StaffsLicenses staffsLicenses = new StaffsLicenses();
            Long snowflakeId = Snowflake.generateUniqueId();
            staffsLicenses.setId(snowflakeId);
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
    public List<StaffsLicensesVO> GetLicensesByStaff(String staffId){
        logger.info("Getting all licenses by staff: {}");
        try{
            List<Map<String,Object>> licensesList = staffsLicensesRepository.findLicensesByStaffId(Long.valueOf(staffId));
            if (!licensesList.isEmpty()) {
                logger.info("Found licensesList.");
                List<StaffsLicensesVO> clientsFundersVOList = new ArrayList<>();
                for(Map<String,Object> license: licensesList){
                    StaffsLicensesVO staffsLicensesVO = new StaffsLicensesVO();
                    staffsLicensesVO.setLicenseId(String.valueOf(license.get("staff_license_id")));
                    staffsLicensesVO.setStaffId(String.valueOf(license.get("staff_id")));
                    staffsLicensesVO.setLicenseName(String.valueOf(license.get("license_name")));
                    staffsLicensesVO.setLicenseNumber(String.valueOf(license.get("license_number")));
                    staffsLicensesVO.setLicenseState(String.valueOf(license.get("license_state")));
                    staffsLicensesVO.setIssueDate(String.valueOf(license.get("issue_date")));
                    staffsLicensesVO.setExpiredDate(String.valueOf(license.get("expired_date")));
                    staffsLicensesVO.setNotes(String.valueOf(license.get("notes")));
                    staffsLicensesVO.setCreatedAt(String.valueOf(license.get("created_at")));
                    staffsLicensesVO.setModifiedAt(String.valueOf(license.get("modified_at")));
                    clientsFundersVOList.add(staffsLicensesVO);
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
        }catch (Exception e) {
            logger.error("Failed to get StaffsLicenses: {}", e.getMessage(), e);
        }
        return null;
    }
    @Transactional
    public void UpdateStaffsLicenses(StaffsLicensesDTO staffsLicensesDTO){
        logger.info("Updating StaffsLicenses: {}", "License ID:"+staffsLicensesDTO.getLicenseId(),"Staff ID:"+staffsLicensesDTO.getStaffId());

        try{
            staffsLicensesRepository.updateStaffLicenseByStaffIdAndLicenseId(Long.valueOf(staffsLicensesDTO.getLicenseId()), Long.valueOf(staffsLicensesDTO.getStaffId()),
                    staffsLicensesDTO.getLicenseName(),
                    staffsLicensesDTO.getLicenseNumber(),
                    staffsLicensesDTO.getLicenseState(),
                    staffsLicensesDTO.getIssueDate(),
                    staffsLicensesDTO.getExpiredDate(),
                    staffsLicensesDTO.getNotes()
            );
        }catch (Exception e) {
            logger.error("Failed to update StaffsLicenses: {}", e.getMessage(), e);
        }
    }
    public StaffsLicensesVO ConvertToStaffsLicensesVO(StaffsLicenses staffsLicenses){
        logger.info("Converting to StaffsLicensesVO.");
        StaffsLicensesVO staffsLicensesVO = new StaffsLicensesVO();
        staffsLicensesVO.setLicenseId(String.valueOf(staffsLicenses.getId()));
        staffsLicensesVO.setStaffId(String.valueOf(staffsLicenses.getStaffId()));
        staffsLicensesVO.setLicenseName(staffsLicenses.getLicenseName());
        staffsLicensesVO.setLicenseNumber(staffsLicenses.getLicenseNumber());
        staffsLicensesVO.setLicenseState(staffsLicenses.getLicenseState());
        staffsLicensesVO.setIssueDate(staffsLicenses.getIssueDate());
        staffsLicensesVO.setExpiredDate(staffsLicenses.getExpiredDate());
        staffsLicensesVO.setNotes(staffsLicenses.getNotes());
        String formattedCreatedDateTime = DateTimeConverter.DateTimeConvertFromInstant(staffsLicenses.getCreatedAt());
        String formattedModifiedDateTime = DateTimeConverter.DateTimeConvertFromInstant(staffsLicenses.getModifiedAt());
        staffsLicensesVO.setCreatedAt(formattedCreatedDateTime);
        staffsLicensesVO.setModifiedAt(formattedModifiedDateTime);
        logger.info("StaffsLicensesVO converted successfully.");
        return staffsLicensesVO;
    }
}
