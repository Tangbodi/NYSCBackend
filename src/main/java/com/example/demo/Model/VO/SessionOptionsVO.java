package com.example.demo.Model.VO;

import lombok.Data;

import java.util.List;

@Data
public class SessionOptionsVO {
    private List<TemplatesVO> templates;
    private List<PurposeOfSessionVO> purposeOfSession;
    private List<SkillStrategiesVO> skillStrategies;
    private List<BehaviorStrategiesVO> behaviorStrategies;
    private List<SupervisorSupportVO> supervisorSupport;
}
