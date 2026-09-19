package com.example.demo.Service.StaffsPayroll;

import com.example.demo.Model.DTO.StaffsPayrollDTO;
import com.example.demo.Model.Entity.StaffsPayroll;
import com.example.demo.Model.VO.StaffsPayrollVO;
import com.example.demo.Repository.StaffsPayrollRepository;
import com.example.demo.Util.DateTimeConverter;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class StaffsPayrollService {
    private static final Logger logger = LoggerFactory.getLogger(StaffsPayrollService.class);

    @Autowired
    private StaffsPayrollRepository staffsPayrollRepository;

    // Used during staff registration — creates the first payroll record with defaults
    @Transactional
    public void CreateStaffsPayroll(Long staffId){
        logger.info("Creating StaffsPayroll: {}", staffId);
        try{
            StaffsPayroll staffsPayroll = new StaffsPayroll();
            staffsPayroll.setStaffId(staffId);
            staffsPayroll.setHourlyRate(new BigDecimal("0.00"));
            staffsPayroll.setCreatedAt(DateTimeConverter.nowNyc());
            staffsPayroll.setModifiedAt(DateTimeConverter.nowNyc());
            staffsPayrollRepository.save(staffsPayroll);
            logger.info("Created StaffsPayroll successfully.");
        }catch (Exception e) {
            logger.error("Failed to create StaffsPayroll: {}", e.getMessage(), e);
            throw e;
        }
    }

    // Used by admin API — adds a new payroll/rate record for a staff member.
    // staff_id is no longer unique: each call inserts a new row (rate history),
    // distinguished by effective_start_date/effective_end_date.
    @Transactional
    public StaffsPayrollVO CreateStaffsPayrollFromDTO(StaffsPayrollDTO dto){
        logger.info("Creating StaffsPayroll from DTO for staffId: {}", dto.getStaffId());
        try{
            Long staffId = Long.valueOf(dto.getStaffId());
            StaffsPayroll staffsPayroll = new StaffsPayroll();
            staffsPayroll.setStaffId(staffId);
            staffsPayroll.setHourlyRate(new BigDecimal(dto.getHourlyRate()));
            staffsPayroll.setPayCode(dto.getPayCode());
            staffsPayroll.setEffectiveStartDate(dto.getEffectiveStartDate());
            staffsPayroll.setEffectiveEndDate(dto.getEffectiveEndDate());
            staffsPayroll.setNotes(dto.getNotes());
            staffsPayroll.setCreatedAt(DateTimeConverter.nowNyc());
            staffsPayroll.setModifiedAt(DateTimeConverter.nowNyc());
            StaffsPayroll saved = staffsPayrollRepository.save(staffsPayroll);
            logger.info("Created StaffsPayroll from DTO successfully.");
            return ConvertToStaffsPayrollVO(saved);
        }catch (Exception e) {
            logger.error("Failed to create StaffsPayroll from DTO: {}", e.getMessage(), e);
            throw e;
        }
    }

    // Returns the full payroll/rate history for a staff member (staff_id is no
    // longer unique — one row per rate period), most recent record first.
    @Transactional
    public List<StaffsPayrollVO> GetStaffsPayroll(Long staffId){
        logger.info("Getting StaffsPayroll list for staffId: {}", staffId);
        try{
            List<StaffsPayroll> staffsPayrollList = staffsPayrollRepository.findByStaffIdOrderByIdDesc(staffId);
            List<StaffsPayrollVO> staffsPayrollVOList = new ArrayList<>();
            for (StaffsPayroll staffsPayroll : staffsPayrollList) {
                staffsPayrollVOList.add(ConvertToStaffsPayrollVO(staffsPayroll));
            }
            return staffsPayrollVOList;
        }catch (Exception e) {
            logger.error("Failed to get StaffsPayroll list: {}", e.getMessage(), e);
            throw e;
        }
    }
    // Updates one specific payroll/rate row, identified by its own id.
    @Transactional
    public void UpdateStaffsPayroll(StaffsPayrollDTO staffsPayrollDTO){
        logger.info("Updating StaffsPayroll: {}", staffsPayrollDTO.getId());
        try{
            BigDecimal hourlyRate = new BigDecimal(staffsPayrollDTO.getHourlyRate());
            Instant modifiedAt = DateTimeConverter.nowNyc();
            staffsPayrollRepository.updateStaffsPayroll(
                    Long.valueOf(staffsPayrollDTO.getId()),
                    hourlyRate,
                    staffsPayrollDTO.getPayCode(),
                    staffsPayrollDTO.getEffectiveStartDate(),
                    staffsPayrollDTO.getEffectiveEndDate(),
                    staffsPayrollDTO.getNotes(),
                    modifiedAt
            );
            logger.info("StaffsPayroll updated successfully.");
        }catch (Exception e) {
            logger.error("Failed to save StaffsPayroll: {}", e.getMessage(), e);
            throw e;
        }
    }

    // Deletes one specific payroll/rate row, identified by its own id.
    @Transactional
    public void DeleteStaffsPayroll(String id) {
        logger.info("Deleting StaffsPayroll: {}", id);
        try {
            Long rowId = Long.valueOf(id);
            if (!staffsPayrollRepository.existsById(rowId)) {
                throw new RuntimeException("Payroll record not found for id: " + id);
            }
            staffsPayrollRepository.deleteById(rowId);
            logger.info("StaffsPayroll deleted successfully.");
        } catch (Exception e) {
            logger.error("Failed to delete StaffsPayroll: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public StaffsPayrollVO ConvertToStaffsPayrollVO(StaffsPayroll savedStaffsPayroll){
        logger.info("Converting to StaffsPayrollVO: {}", savedStaffsPayroll.getId());
        try{
            StaffsPayrollVO staffsPayrollVO = new StaffsPayrollVO();
            staffsPayrollVO.setId(savedStaffsPayroll.getId().toString());
            staffsPayrollVO.setStaffId(savedStaffsPayroll.getStaffId().toString());
            staffsPayrollVO.setHourlyRate(savedStaffsPayroll.getHourlyRate().toString());
            staffsPayrollVO.setPayCode(savedStaffsPayroll.getPayCode());
            staffsPayrollVO.setEffectiveStartDate(savedStaffsPayroll.getEffectiveStartDate());
            staffsPayrollVO.setEffectiveEndDate(savedStaffsPayroll.getEffectiveEndDate());
            staffsPayrollVO.setNotes(savedStaffsPayroll.getNotes());
            staffsPayrollVO.setCreatedAt(DateTimeConverter.DateTimeConvertFromInstant(savedStaffsPayroll.getCreatedAt()));
            staffsPayrollVO.setModifiedAt(DateTimeConverter.DateTimeConvertFromInstant(savedStaffsPayroll.getModifiedAt()));
            logger.info("Converted to StaffsPayrollVO successfully.");
            return staffsPayrollVO;
        }catch (Exception e) {
            logger.error("Failed to convert to StaffsPayrollVO: {}", e.getMessage(), e);
            throw e;
        }
    }
}
