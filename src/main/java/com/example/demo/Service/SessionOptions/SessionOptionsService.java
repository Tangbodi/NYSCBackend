package com.example.demo.Service.SessionOptions;

import com.example.demo.Model.Entity.BehaviorStrategies;
import com.example.demo.Model.Entity.PurposeOfSession;
import com.example.demo.Model.Entity.SkillStrategies;
import com.example.demo.Model.Entity.SupervisorSupport;
import com.example.demo.Model.Entity.Templates;
import com.example.demo.Model.VO.BehaviorStrategiesVO;
import com.example.demo.Model.VO.PurposeOfSessionVO;
import com.example.demo.Model.VO.SessionOptionsVO;
import com.example.demo.Model.VO.SkillStrategiesVO;
import com.example.demo.Model.VO.SupervisorSupportVO;
import com.example.demo.Model.VO.TemplatesVO;
import com.example.demo.Repository.BehaviorStrategiesRepository;
import com.example.demo.Repository.PurposeOfSessionRepository;
import com.example.demo.Repository.SkillStrategiesRepository;
import com.example.demo.Repository.SupervisorSupportRepository;
import com.example.demo.Repository.TemplatesRepository;
import com.example.demo.Util.DateTimeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SessionOptionsService {
    private static final Logger logger = LoggerFactory.getLogger(SessionOptionsService.class);

    @Autowired
    private TemplatesRepository templatesRepository;
    @Autowired
    private PurposeOfSessionRepository purposeOfSessionRepository;
    @Autowired
    private SkillStrategiesRepository skillStrategiesRepository;
    @Autowired
    private BehaviorStrategiesRepository behaviorStrategiesRepository;
    @Autowired
    private SupervisorSupportRepository supervisorSupportRepository;

    public SessionOptionsVO GetAllSessionOptions() {
        logger.info("Getting all session options.");
        SessionOptionsVO vo = new SessionOptionsVO();
        vo.setTemplates(getAllTemplates());
        vo.setPurposeOfSession(getAllPurposes());
        vo.setSkillStrategies(getAllSkillStrategies());
        vo.setBehaviorStrategies(getAllBehaviorStrategies());
        vo.setSupervisorSupport(getAllSupervisorSupport());
        return vo;
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private List<TemplatesVO> getAllTemplates() {
        List<TemplatesVO> list = new ArrayList<>();
        for (Templates t : templatesRepository.findAll()) {
            TemplatesVO v = new TemplatesVO();
            v.setTemplateId(String.valueOf(t.getId()));
            v.setTemplate(t.getTemplate());
            v.setCreatedAt(DateTimeConverter.DateTimeConvertFromInstant(t.getCreatedAt()));
            v.setModifiedAt(DateTimeConverter.DateTimeConvertFromInstant(t.getModifiedAt()));
            list.add(v);
        }
        return list;
    }

    private List<PurposeOfSessionVO> getAllPurposes() {
        List<PurposeOfSessionVO> list = new ArrayList<>();
        for (PurposeOfSession p : purposeOfSessionRepository.findAll()) {
            PurposeOfSessionVO v = new PurposeOfSessionVO();
            v.setPurposeId(String.valueOf(p.getId()));
            v.setPurpose(p.getPurpose());
            v.setCreatedAt(DateTimeConverter.DateTimeConvertFromInstant(p.getCreatedAt()));
            v.setModifiedAt(DateTimeConverter.DateTimeConvertFromInstant(p.getModifiedAt()));
            list.add(v);
        }
        return list;
    }

    private List<SkillStrategiesVO> getAllSkillStrategies() {
        List<SkillStrategiesVO> list = new ArrayList<>();
        for (SkillStrategies s : skillStrategiesRepository.findAll()) {
            SkillStrategiesVO v = new SkillStrategiesVO();
            v.setSkillId(String.valueOf(s.getId()));
            v.setSkillStrategy(s.getSkillStrategy());
            v.setCreatedAt(DateTimeConverter.DateTimeConvertFromInstant(s.getCreatedAt()));
            v.setModifiedAt(DateTimeConverter.DateTimeConvertFromInstant(s.getModifiedAt()));
            list.add(v);
        }
        return list;
    }

    private List<BehaviorStrategiesVO> getAllBehaviorStrategies() {
        List<BehaviorStrategiesVO> list = new ArrayList<>();
        for (BehaviorStrategies b : behaviorStrategiesRepository.findAll()) {
            BehaviorStrategiesVO v = new BehaviorStrategiesVO();
            v.setBehaviorId(String.valueOf(b.getId()));
            v.setBehaviorStrategy(b.getBehaviorStrategy());
            v.setCreatedAt(DateTimeConverter.DateTimeConvertFromInstant(b.getCreatedAt()));
            v.setModifiedAt(DateTimeConverter.DateTimeConvertFromInstant(b.getModifiedAt()));
            list.add(v);
        }
        return list;
    }

    private List<SupervisorSupportVO> getAllSupervisorSupport() {
        List<SupervisorSupportVO> list = new ArrayList<>();
        for (SupervisorSupport s : supervisorSupportRepository.findAll()) {
            SupervisorSupportVO v = new SupervisorSupportVO();
            v.setSupervisionId(String.valueOf(s.getId()));
            v.setSupervisorSupport(s.getSupervisorSupport());
            v.setCreatedAt(DateTimeConverter.DateTimeConvertFromInstant(s.getCreatedAt()));
            v.setModifiedAt(DateTimeConverter.DateTimeConvertFromInstant(s.getModifiedAt()));
            list.add(v);
        }
        return list;
    }
}
