package com.example.demo.Service.FinancialManualEntry;

import com.example.demo.Model.DTO.FinancialManualEntryDTO;
import com.example.demo.Model.Entity.FinancialManualEntry;
import com.example.demo.Model.VO.FinancialManualEntryVO;
import com.example.demo.Repository.FinancialManualEntryRepository;
import com.example.demo.Util.DateTimeConverter;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinancialManualEntryService {
    private static final Logger logger = LoggerFactory.getLogger(FinancialManualEntryService.class);

    @Autowired
    private FinancialManualEntryRepository financialManualEntryRepository;

    @Transactional
    public FinancialManualEntryVO CreateEntry(FinancialManualEntryDTO dto) {
        logger.info("Creating financial manual entry for section: {}", dto.getSection());
        try {
            FinancialManualEntry entry = new FinancialManualEntry();
            entry.setEntryDate(dto.getEntryDate());
            entry.setSection(dto.getSection());
            entry.setItem(dto.getItem());
            entry.setCategory(dto.getCategory());
            entry.setAmount(dto.getAmount() != null && !dto.getAmount().isBlank()
                    ? new BigDecimal(dto.getAmount()) : null);
            entry.setNote(dto.getNote());
            entry.setCreatedAt(DateTimeConverter.nowNyc());
            entry.setUpdatedAt(DateTimeConverter.nowNyc());
            financialManualEntryRepository.save(entry);
            logger.info("Financial manual entry created successfully with id: {}", entry.getId());
            return ConvertToVO(entry);
        } catch (Exception e) {
            logger.error("Failed to create financial manual entry: {}", e.getMessage(), e);
            throw e;
        }
    }

    public FinancialManualEntryVO GetEntry(String entryId) {
        logger.info("Getting financial manual entry: {}", entryId);
        try {
            FinancialManualEntry entry = financialManualEntryRepository.findById(Long.valueOf(entryId)).orElse(null);
            if (entry == null) {
                logger.info("Entry not found for id: {}", entryId);
                return null;
            }
            return ConvertToVO(entry);
        } catch (Exception e) {
            logger.error("Failed to get financial manual entry: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<FinancialManualEntryVO> GetAllEntries() {
        logger.info("Getting all financial manual entries");
        try {
            List<FinancialManualEntry> entries = financialManualEntryRepository.findAll();
            if (entries.isEmpty()) {
                return Collections.emptyList();
            }
            return entries.stream().map(this::ConvertToVO).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to get all financial manual entries: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public boolean UpdateEntry(FinancialManualEntryDTO dto) {
        logger.info("Updating financial manual entry: {}", dto.getEntryId());
        try {
            Long id = Long.valueOf(dto.getEntryId());
            if (!financialManualEntryRepository.existsById(id)) {
                logger.warn("Entry not found for id: {}", dto.getEntryId());
                return false;
            }
            BigDecimal amount = dto.getAmount() != null && !dto.getAmount().isBlank()
                    ? new BigDecimal(dto.getAmount()) : null;
            Instant modifiedAt = DateTimeConverter.nowNyc();
            financialManualEntryRepository.updateEntry(
                    id,
                    dto.getEntryDate(),
                    dto.getSection(),
                    dto.getItem(),
                    dto.getCategory(),
                    amount,
                    dto.getNote(),
                    modifiedAt
            );
            logger.info("Financial manual entry updated successfully.");
            return true;
        } catch (Exception e) {
            logger.error("Failed to update financial manual entry: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public boolean DeleteEntry(String entryId) {
        logger.info("Deleting financial manual entry: {}", entryId);
        try {
            Long id = Long.valueOf(entryId);
            if (!financialManualEntryRepository.existsById(id)) {
                logger.warn("Entry not found for id: {}", entryId);
                return false;
            }
            financialManualEntryRepository.deleteById(id);
            logger.info("Financial manual entry deleted successfully.");
            return true;
        } catch (Exception e) {
            logger.error("Failed to delete financial manual entry: {}", e.getMessage(), e);
            throw e;
        }
    }

    private FinancialManualEntryVO ConvertToVO(FinancialManualEntry entry) {
        FinancialManualEntryVO vo = new FinancialManualEntryVO();
        vo.setEntryId(String.valueOf(entry.getId()));
        vo.setEntryDate(entry.getEntryDate());
        vo.setSection(entry.getSection());
        vo.setItem(entry.getItem());
        vo.setCategory(entry.getCategory());
        vo.setAmount(entry.getAmount() != null ? entry.getAmount().toString() : null);
        vo.setNote(entry.getNote());
        vo.setCreatedAt(DateTimeConverter.DateTimeConvertFromInstant(entry.getCreatedAt()));
        vo.setUpdatedAt(DateTimeConverter.DateTimeConvertFromInstant(entry.getUpdatedAt()));
        return vo;
    }
}
