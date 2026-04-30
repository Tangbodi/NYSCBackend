package com.example.demo.Service.FunderSettings;

import com.example.demo.Model.DTO.FunderSettingsDTO;
import com.example.demo.Model.Entity.FunderSettings;
import com.example.demo.Model.VO.FunderSettingsVO;
import com.example.demo.Repository.FunderSettingsRepository;
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

@Service
public class FunderSettingsService {
    private static final Logger logger = LoggerFactory.getLogger(FunderSettingsService.class);

    @Autowired
    private FunderSettingsRepository funderSettingsRepository;

    @Transactional
    public void CreateFunderSettings(FunderSettingsDTO dto) {
        logger.info("Creating FunderSettings: {}", dto.getFunderName());
        try {
            FunderSettings funder = new FunderSettings();
            funder.setFunderType(dto.getFunderType());
            funder.setFunderName(dto.getFunderName());
            funder.setAddress(dto.getAddress());
            funder.setCoverageType(dto.getCoverageType());
            funder.setVendorId(emptyIfNull(dto.getVendorId()));
            funder.setPhone(emptyIfNull(dto.getPhone()));
            funder.setEmail(emptyIfNull(dto.getEmail()));
            funder.setFax(emptyIfNull(dto.getFax()));
            funder.setDefaultBillingProvider(emptyIfNull(dto.getDefaultBillingProvider()));
            funder.setCreatedAt(Instant.now());
            funder.setModifiedAt(Instant.now());
            funderSettingsRepository.save(funder);
            logger.info("FunderSettings created successfully.");
        } catch (Exception e) {
            logger.error("Failed to create FunderSettings: {}", e.getMessage(), e);
            throw e;
        }
    }

    public FunderSettingsVO GetFunderSettings(String funderId) {
        logger.info("Getting FunderSettings: {}", funderId);
        try {
            FunderSettings funder = funderSettingsRepository.findById(Integer.valueOf(funderId)).orElse(null);
            if (funder != null) {
                return ConvertToVO(funder);
            } else {
                logger.info("FunderSettings not found: {}", funderId);
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to get FunderSettings: {}", e.getMessage(), e);
        }
        return null;
    }

    public List<FunderSettingsVO> GetAllFunderSettings() {
        logger.info("Getting all FunderSettings.");
        try {
            List<FunderSettings> funders = funderSettingsRepository.findAll();
            if (!funders.isEmpty()) {
                List<FunderSettingsVO> voList = new ArrayList<>();
                for (FunderSettings funder : funders) {
                    voList.add(ConvertToVO(funder));
                }
                return voList;
            } else {
                logger.info("No FunderSettings found.");
                return Collections.emptyList();
            }
        } catch (Exception e) {
            logger.error("Failed to get all FunderSettings: {}", e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Transactional
    public void UpdateFunderSettings(String funderId, FunderSettingsDTO dto) {
        logger.info("Updating FunderSettings: {}", funderId);
        try {
            funderSettingsRepository.UpdateFunderSettings(
                    Integer.valueOf(funderId),
                    dto.getFunderType(),
                    dto.getFunderName(),
                    dto.getAddress(),
                    dto.getCoverageType(),
                    emptyIfNull(dto.getVendorId()),
                    emptyIfNull(dto.getPhone()),
                    emptyIfNull(dto.getEmail()),
                    emptyIfNull(dto.getFax()),
                    emptyIfNull(dto.getDefaultBillingProvider())
            );
            logger.info("FunderSettings updated successfully.");
        } catch (Exception e) {
            logger.error("Failed to update FunderSettings: {}", e.getMessage(), e);
            throw e;
        }
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private FunderSettingsVO ConvertToVO(FunderSettings funder) {
        FunderSettingsVO vo = new FunderSettingsVO();
        vo.setFunderId(String.valueOf(funder.getId()));
        vo.setFunderType(funder.getFunderType());
        vo.setFunderName(funder.getFunderName());
        vo.setAddress(funder.getAddress());
        vo.setCoverageType(funder.getCoverageType());
        vo.setVendorId(funder.getVendorId());
        vo.setPhone(funder.getPhone());
        vo.setEmail(funder.getEmail());
        vo.setFax(funder.getFax());
        vo.setDefaultBillingProvider(funder.getDefaultBillingProvider());
        vo.setCreatedAt(DateTimeConverter.DateTimeConvertFromInstant(funder.getCreatedAt()));
        vo.setModifiedAt(DateTimeConverter.DateTimeConvertFromInstant(funder.getModifiedAt()));
        return vo;
    }

    private String emptyIfNull(String s) {
        return s == null ? "" : s;
    }
}
