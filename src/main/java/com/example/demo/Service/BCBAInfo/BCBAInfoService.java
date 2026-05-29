package com.example.demo.Service.BCBAInfo;

import com.example.demo.Model.DTO.BCBAInfoDTO;
import com.example.demo.Model.Entity.BCBAInfo;
import com.example.demo.Model.VO.BCBAInfoVO;
import com.example.demo.Repository.BCBAInfoRepository;
import com.example.demo.Util.DateTimeConverter;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BCBAInfoService {
    private static final Logger logger = LoggerFactory.getLogger(BCBAInfoService.class);

    @Autowired
    private BCBAInfoRepository bcbaInfoRepository;

    public BCBAInfoVO GetBCBAInfo(String staffId) {
        logger.info("Getting BCBA info for staffId: {}", staffId);
        try {
            BCBAInfo bcba = bcbaInfoRepository.findById(Long.valueOf(staffId)).orElse(null);
            if (bcba != null) {
                return ConvertToVO(bcba);
            } else {
                logger.info("No BCBA record found for staffId: {}", staffId);
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to get BCBA info: {}", e.getMessage(), e);
        }
        return null;
    }

    @Transactional
    public boolean UpdateBCBAInfo(BCBAInfoDTO dto) {
        logger.info("Updating BCBA info for staffId: {}", dto.getStaffId());
        try {
            Long staffId = Long.valueOf(dto.getStaffId());
            if (!bcbaInfoRepository.existsById(staffId)) {
                logger.warn("No BCBA record found for staffId: {}", staffId);
                return false;
            }
            bcbaInfoRepository.updateBCBAInfo(
                    staffId,
                    dto.getNpiNumber(),
                    dto.getMedicaidId()
            );
            logger.info("BCBA info updated successfully for staffId: {}", staffId);
            return true;
        } catch (Exception e) {
            logger.error("Failed to update BCBA info: {}", e.getMessage(), e);
            throw e;
        }
    }

    public BCBAInfoVO ConvertToVO(BCBAInfo bcba) {
        BCBAInfoVO vo = new BCBAInfoVO();
        vo.setStaffId(String.valueOf(bcba.getId()));
        vo.setNpiNumber(bcba.getNpiNumber());
        vo.setMedicaidId(bcba.getMedicaidId());
        vo.setCreatedAt(DateTimeConverter.DateTimeConvertFromInstant(bcba.getCreatedAt()));
        vo.setModifiedAt(DateTimeConverter.DateTimeConvertFromInstant(bcba.getModifiedAt()));
        return vo;
    }
}
