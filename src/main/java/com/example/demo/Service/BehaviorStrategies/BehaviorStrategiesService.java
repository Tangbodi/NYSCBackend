package com.example.demo.Service.BehaviorStrategies;

import com.example.demo.Model.DTO.BehaviorStrategiesDTO;
import com.example.demo.Model.Entity.BehaviorStrategies;
import com.example.demo.Model.VO.BehaviorStrategiesVO;
import com.example.demo.Repository.BehaviorStrategiesRepository;
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
public class BehaviorStrategiesService {
    private static final Logger logger = LoggerFactory.getLogger(BehaviorStrategiesService.class);

    @Autowired
    private BehaviorStrategiesRepository behaviorStrategiesRepository;

    @Transactional
    public void CreateStrategy(BehaviorStrategiesDTO dto) {
        logger.info("Creating behavior strategy.");
        try {
            BehaviorStrategies entity = new BehaviorStrategies();
            entity.setBehaviorStrategy(dto.getBehaviorStrategy());
            entity.setCreatedAt(Instant.now());
            entity.setModifiedAt(Instant.now());
            behaviorStrategiesRepository.save(entity);
            logger.info("Behavior strategy created successfully.");
        } catch (Exception e) {
            logger.error("Failed to create behavior strategy: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void UpdateStrategy(String strategyId, BehaviorStrategiesDTO dto) {
        logger.info("Updating behavior strategy: {}", strategyId);
        try {
            Integer id = Integer.valueOf(strategyId);
            BehaviorStrategies entity = behaviorStrategiesRepository.findById(id).orElse(null);
            if (entity == null) {
                throw new RuntimeException("Behavior strategy not found for id: " + strategyId);
            }
            entity.setBehaviorStrategy(dto.getBehaviorStrategy());
            entity.setModifiedAt(Instant.now());
            behaviorStrategiesRepository.save(entity);
            logger.info("Behavior strategy updated successfully.");
        } catch (Exception e) {
            logger.error("Failed to update behavior strategy: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void DeleteStrategy(String strategyId) {
        logger.info("Deleting behavior strategy: {}", strategyId);
        try {
            Integer id = Integer.valueOf(strategyId);
            if (!behaviorStrategiesRepository.existsById(id)) {
                throw new RuntimeException("Behavior strategy not found for id: " + strategyId);
            }
            behaviorStrategiesRepository.deleteById(id);
            logger.info("Behavior strategy deleted successfully.");
        } catch (Exception e) {
            logger.error("Failed to delete behavior strategy: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<BehaviorStrategiesVO> GetAllStrategies() {
        logger.info("Getting all behavior strategies.");
        try {
            List<BehaviorStrategies> strategies = behaviorStrategiesRepository.findAll();
            if (!strategies.isEmpty()) {
                List<BehaviorStrategiesVO> voList = new ArrayList<>();
                for (BehaviorStrategies strategy : strategies) {
                    voList.add(ConvertToVO(strategy));
                }
                return voList;
            } else {
                logger.info("No behavior strategies found.");
                return Collections.emptyList();
            }
        } catch (Exception e) {
            logger.error("Failed to get behavior strategies: {}", e.getMessage(), e);
            throw e;
        }
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private BehaviorStrategiesVO ConvertToVO(BehaviorStrategies strategy) {
        BehaviorStrategiesVO vo = new BehaviorStrategiesVO();
        vo.setBehaviorId(String.valueOf(strategy.getId()));
        vo.setBehaviorStrategy(strategy.getBehaviorStrategy());
        vo.setCreatedAt(DateTimeConverter.DateTimeConvertFromInstant(strategy.getCreatedAt()));
        vo.setModifiedAt(DateTimeConverter.DateTimeConvertFromInstant(strategy.getModifiedAt()));
        return vo;
    }
}
