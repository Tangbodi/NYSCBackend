package com.example.demo.Service.StaffsPayroll;

import com.example.demo.Model.DTO.StaffsPayrollDTO;
import com.example.demo.Model.Entity.StaffsPayroll;
import com.example.demo.Model.VO.StaffsPayrollVO;
import com.example.demo.Repository.StaffsPayrollRepository;
import com.example.demo.Service.StaffsLogin.StaffsLoginService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class StaffsPayrollService {
    private static final Logger logger = LoggerFactory.getLogger(StaffsPayrollService.class);

    @Autowired
    private StaffsPayrollRepository staffsPayrollRepository;

    @Transactional
    public void CreateStaffsPayroll(Long staffId){
        logger.info("Creating StaffsPayroll: {}", staffId);
        try{
            StaffsPayroll staffsPayroll = new StaffsPayroll();
            staffsPayroll.setId(staffId);
            staffsPayroll.setHourlyRate(new BigDecimal("0.00"));
            staffsPayroll.setPayCode("");
            staffsPayroll.setCreatedAt(Instant.now());
            staffsPayroll.setModifiedAt(Instant.now());
            staffsPayrollRepository.save(staffsPayroll);
            logger.info("Created StaffsPayroll successfully.");
        }catch (Exception e) {
            logger.error("Failed to create StaffsPayroll: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public StaffsPayrollVO GetStaffsPayroll(Long staffId){
        logger.info("Getting StaffsPayroll:{} ", staffId);
        try{
            StaffsPayroll staffsPayroll = staffsPayrollRepository.findById(staffId).orElse(null);
            if (staffsPayroll == null) {
                logger.info("No existing payroll found.");
            }else{
                return ConvertToStaffsPayrollVO(staffsPayroll);
            }
        }catch (Exception e) {
            logger.error("Failed to save StaffsPayroll: {}", e.getMessage(), e);
            throw e;
        }
        return null;
    }
    @Transactional
    public void UpdateStaffsPayroll(StaffsPayrollDTO staffsPayrollDTO){
        logger.info("Updating StaffsPayroll: {}", staffsPayrollDTO.getStaffId());
        try{
            BigDecimal hourlyRate = new BigDecimal(staffsPayrollDTO.getHourlyRate());
            staffsPayrollRepository.updateStaffsPayroll(
                    staffsPayrollDTO.getStaffId(),
                    hourlyRate,
                    staffsPayrollDTO.getPayCode(),
                    staffsPayrollDTO.getEffectiveStartDate(),
                    staffsPayrollDTO.getEffectiveEndDate(),
                    staffsPayrollDTO.getNotes()
            );
            logger.info("StaffsPayroll updated successfully.");
        }catch (Exception e) {
            logger.error("Failed to save StaffsPayroll: {}", e.getMessage(), e);
            throw e;
        }
    }
    @Transactional
    public StaffsPayrollVO ConvertToStaffsPayrollVO(StaffsPayroll savedStaffsPayroll){
        logger.info("Converting to StaffsPayrollVO: {}");
        try{
            StaffsPayrollVO staffsPayrollVO = new StaffsPayrollVO();
            staffsPayrollVO.setStaffId(savedStaffsPayroll.getId().toString());
            staffsPayrollVO.setHourlyRate(savedStaffsPayroll.getHourlyRate().toString());
            staffsPayrollVO.setPayCode(savedStaffsPayroll.getPayCode());
            staffsPayrollVO.setEffectiveStartDate(savedStaffsPayroll.getEffectiveStartDate());
            staffsPayrollVO.setEffectiveEndDate(savedStaffsPayroll.getEffectiveEndDate());
            logger.info("Converted to StaffsPayrollVO successfully.");
            return staffsPayrollVO;
        }catch (Exception e) {
            logger.error("Failed to convert to StaffsPayrollVO: {}", e.getMessage(), e);
            throw e;
        }
    }
}
