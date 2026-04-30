package com.example.demo.Service.BehaviorStrategies;

import com.example.demo.Model.Entity.BehaviorStrategies;
import com.example.demo.Model.VO.BehaviorStrategiesVO;
import com.example.demo.Repository.BehaviorStrategiesRepository;
import com.example.demo.Util.DateTimeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BehaviorStrategiesService {
    private static final Logger logger = LoggerFactory.getLogger(BehaviorStrategiesService.class);

    @Autowired
    private BehaviorStrategiesRepository behaviorStrategiesRepository;

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
