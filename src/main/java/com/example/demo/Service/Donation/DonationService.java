package com.example.demo.Service.Donation;

import com.example.demo.Model.DTO.DonationDTO;
import com.example.demo.Model.Entity.Donation;
import com.example.demo.Model.VO.DonationVO;
import com.example.demo.Repository.DonationRepository;
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
public class DonationService {
    private static final Logger logger = LoggerFactory.getLogger(DonationService.class);

    @Autowired
    private DonationRepository donationRepository;

    @Transactional
    public DonationVO CreateDonation(DonationDTO dto) {
        logger.info("Creating donation for donor: {}", dto.getDonor());
        try {
            Donation donation = new Donation();
            donation.setDonationDate(dto.getDonationDate());
            donation.setDonor(dto.getDonor());
            donation.setAmount(new BigDecimal(dto.getAmount()));
            donation.setDonationType(dto.getDonationType());
            donation.setNote(dto.getNote() != null ? dto.getNote() : "");
            donation.setCreatedAt(Instant.now());
            donation.setUpdatedAt(Instant.now());
            donationRepository.save(donation);
            logger.info("Donation created successfully with id: {}", donation.getId());
            return ConvertToVO(donation);
        } catch (Exception e) {
            logger.error("Failed to create donation: {}", e.getMessage(), e);
            throw e;
        }
    }

    public DonationVO GetDonation(String donationId) {
        logger.info("Getting donation: {}", donationId);
        try {
            Donation donation = donationRepository.findById(Long.valueOf(donationId)).orElse(null);
            if (donation == null) {
                logger.info("Donation not found for id: {}", donationId);
                return null;
            }
            return ConvertToVO(donation);
        } catch (Exception e) {
            logger.error("Failed to get donation: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<DonationVO> GetAllDonations() {
        logger.info("Getting all donations");
        try {
            List<Donation> donations = donationRepository.findAll();
            if (donations.isEmpty()) {
                return Collections.emptyList();
            }
            return donations.stream().map(this::ConvertToVO).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to get all donations: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public boolean UpdateDonation(DonationDTO dto) {
        logger.info("Updating donation: {}", dto.getDonationId());
        try {
            Long id = Long.valueOf(dto.getDonationId());
            if (!donationRepository.existsById(id)) {
                logger.warn("Donation not found for id: {}", dto.getDonationId());
                return false;
            }
            donationRepository.updateDonation(
                    id,
                    dto.getDonationDate(),
                    dto.getDonor(),
                    new BigDecimal(dto.getAmount()),
                    dto.getDonationType(),
                    dto.getNote() != null ? dto.getNote() : ""
            );
            logger.info("Donation updated successfully.");
            return true;
        } catch (Exception e) {
            logger.error("Failed to update donation: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public boolean DeleteDonation(String donationId) {
        logger.info("Deleting donation: {}", donationId);
        try {
            Long id = Long.valueOf(donationId);
            if (!donationRepository.existsById(id)) {
                logger.warn("Donation not found for id: {}", donationId);
                return false;
            }
            donationRepository.deleteById(id);
            logger.info("Donation deleted successfully.");
            return true;
        } catch (Exception e) {
            logger.error("Failed to delete donation: {}", e.getMessage(), e);
            throw e;
        }
    }

    private DonationVO ConvertToVO(Donation donation) {
        DonationVO vo = new DonationVO();
        vo.setDonationId(String.valueOf(donation.getId()));
        vo.setDonationDate(donation.getDonationDate());
        vo.setDonor(donation.getDonor());
        vo.setAmount(donation.getAmount().toString());
        vo.setDonationType(donation.getDonationType());
        vo.setNote(donation.getNote());
        vo.setCreatedAt(DateTimeConverter.DateTimeConvertFromInstant(donation.getCreatedAt()));
        vo.setUpdatedAt(DateTimeConverter.DateTimeConvertFromInstant(donation.getUpdatedAt()));
        return vo;
    }
}
