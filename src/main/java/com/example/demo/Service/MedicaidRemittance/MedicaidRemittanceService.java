package com.example.demo.Service.MedicaidRemittance;

import com.example.demo.Model.Entity.MedicaidRemittanceRecord;
import com.example.demo.Model.VO.MedicaidRemittanceWeeklyPaidVO;
import com.example.demo.Repository.MedicaidRemittanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MedicaidRemittanceService {
    private static final Logger logger = LoggerFactory.getLogger(MedicaidRemittanceService.class);

    @Autowired
    private MedicaidRemittanceRepository medicaidRemittanceRepository;

    public List<MedicaidRemittanceWeeklyPaidVO> getWeeklyPaid(int year) {
        logger.info("Fetching medicaid remittance weekly paid for year: {}", year);
        List<MedicaidRemittanceRecord> records = medicaidRemittanceRepository.findByYear(year);

        // Group by cycle
        Map<String, List<MedicaidRemittanceRecord>> byCycle = new LinkedHashMap<>();
        for (MedicaidRemittanceRecord r : records) {
            String key = r.getCycle() != null ? r.getCycle() : "";
            byCycle.computeIfAbsent(key, k -> new ArrayList<>()).add(r);
        }

        List<MedicaidRemittanceWeeklyPaidVO> result = new ArrayList<>();
        for (Map.Entry<String, List<MedicaidRemittanceRecord>> entry : byCycle.entrySet()) {
            List<MedicaidRemittanceRecord> group = entry.getValue();
            MedicaidRemittanceWeeklyPaidVO vo = new MedicaidRemittanceWeeklyPaidVO();
            vo.setCycle(entry.getKey());
            vo.setWeekStart(group.get(0).getWeekStart());
            vo.setWeekEnd(group.get(0).getWeekEnd());

            BigDecimal totalPaid = group.stream()
                    .map(r -> r.getPaid() != null ? r.getPaid() : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            vo.setPaidAmount(totalPaid.toPlainString());

            List<String> remittanceDates = group.stream()
                    .map(MedicaidRemittanceRecord::getRemittanceDate)
                    .filter(Objects::nonNull)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());
            vo.setRemittanceDates(remittanceDates);

            // Service lines
            List<MedicaidRemittanceWeeklyPaidVO.ServiceLine> serviceLines = group.stream()
                    .map(r -> {
                        MedicaidRemittanceWeeklyPaidVO.ServiceLine sl = new MedicaidRemittanceWeeklyPaidVO.ServiceLine();
                        sl.setDateOfService(r.getDateOfService());
                        sl.setClientName(r.getClientName());
                        sl.setMedicaidClientId(r.getMedicaidClientId());
                        sl.setServiceCode(r.getServiceCode());
                        sl.setUnits(r.getUnits());
                        sl.setCharged(r.getCharged() != null ? r.getCharged().toPlainString() : null);
                        sl.setPaid(r.getPaid() != null ? r.getPaid().toPlainString() : null);
                        sl.setStatus(r.getStatus());
                        sl.setOfficeAccount(r.getOfficeAccount());
                        sl.setTcn(r.getTcn());
                        sl.setLineNo(r.getLineNo());
                        return sl;
                    })
                    .collect(Collectors.toList());
            vo.setServiceLines(serviceLines);

            // Service breakdown (group by serviceCode)
            Map<String, List<MedicaidRemittanceRecord>> byService = group.stream()
                    .collect(Collectors.groupingBy(r -> r.getServiceCode() != null ? r.getServiceCode() : ""));
            List<MedicaidRemittanceWeeklyPaidVO.BreakdownRow> serviceBreakdown = byService.entrySet().stream()
                    .map(e -> buildBreakdown(e.getKey(), e.getValue()))
                    .sorted(Comparator.comparing(MedicaidRemittanceWeeklyPaidVO.BreakdownRow::getLabel))
                    .collect(Collectors.toList());
            vo.setServiceBreakdown(serviceBreakdown);

            // Client breakdown (group by clientName)
            Map<String, List<MedicaidRemittanceRecord>> byClient = group.stream()
                    .collect(Collectors.groupingBy(r -> r.getClientName() != null ? r.getClientName() : ""));
            List<MedicaidRemittanceWeeklyPaidVO.BreakdownRow> clientBreakdown = byClient.entrySet().stream()
                    .map(e -> buildBreakdown(e.getKey(), e.getValue()))
                    .sorted(Comparator.comparing(MedicaidRemittanceWeeklyPaidVO.BreakdownRow::getLabel))
                    .collect(Collectors.toList());
            vo.setClientBreakdown(clientBreakdown);

            result.add(vo);
        }
        return result;
    }

    private MedicaidRemittanceWeeklyPaidVO.BreakdownRow buildBreakdown(
            String label, List<MedicaidRemittanceRecord> rows) {
        MedicaidRemittanceWeeklyPaidVO.BreakdownRow row = new MedicaidRemittanceWeeklyPaidVO.BreakdownRow();
        row.setLabel(label);
        BigDecimal paid = rows.stream()
                .map(r -> r.getPaid() != null ? r.getPaid() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        row.setPaidAmount(paid.toPlainString());
        int units = rows.stream().mapToInt(r -> r.getUnits() != null ? r.getUnits() : 0).sum();
        row.setUnits(units);
        row.setLineCount(rows.size());
        return row;
    }
}
