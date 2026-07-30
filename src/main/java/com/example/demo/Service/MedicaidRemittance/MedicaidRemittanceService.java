package com.example.demo.Service.MedicaidRemittance;

import com.example.demo.Model.Entity.MedicaidRemittanceRecord;
import com.example.demo.Model.VO.MedicaidRemittanceWeeklyPaidVO;
import com.example.demo.Repository.MedicaidRemittanceRepository;
import com.example.demo.Util.DateTimeConverter;
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

            BigDecimal totalPaid = group.stream()
                    .map(r -> r.getPaid() != null ? r.getPaid() : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            vo.setPaidAmount(totalPaid.toPlainString());

            List<String> remittanceDates = group.stream()
                    .map(r -> r.getRemittanceDate() != null ? r.getRemittanceDate().toString() : null)
                    .filter(Objects::nonNull)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());
            vo.setRemittanceDates(remittanceDates);

            // Service lines
            List<MedicaidRemittanceWeeklyPaidVO.ServiceLine> serviceLines = group.stream()
                    .map(this::toServiceLine)
                    .collect(Collectors.toList());
            vo.setServiceLines(serviceLines);

            // Service breakdown (group by procCode)
            Map<String, List<MedicaidRemittanceRecord>> byProc = group.stream()
                    .collect(Collectors.groupingBy(r -> r.getProcCode() != null ? r.getProcCode() : ""));
            List<MedicaidRemittanceWeeklyPaidVO.BreakdownRow> serviceBreakdown = byProc.entrySet().stream()
                    .map(e -> buildBreakdown(e.getKey(), e.getValue()))
                    .sorted(Comparator.comparing(MedicaidRemittanceWeeklyPaidVO.BreakdownRow::getLabel))
                    .collect(Collectors.toList());
            vo.setServiceBreakdown(serviceBreakdown);

            // Client breakdown (group by clientFullName)
            Map<String, List<MedicaidRemittanceRecord>> byClient = group.stream()
                    .collect(Collectors.groupingBy(r -> r.getClientFullName() != null ? r.getClientFullName() : r.getClientLastNamePdf()));
            List<MedicaidRemittanceWeeklyPaidVO.BreakdownRow> clientBreakdown = byClient.entrySet().stream()
                    .map(e -> buildBreakdown(e.getKey(), e.getValue()))
                    .sorted(Comparator.comparing(MedicaidRemittanceWeeklyPaidVO.BreakdownRow::getLabel))
                    .collect(Collectors.toList());
            vo.setClientBreakdown(clientBreakdown);

            result.add(vo);
        }
        return result;
    }

    private MedicaidRemittanceWeeklyPaidVO.ServiceLine toServiceLine(MedicaidRemittanceRecord r) {
        MedicaidRemittanceWeeklyPaidVO.ServiceLine sl = new MedicaidRemittanceWeeklyPaidVO.ServiceLine();
        sl.setRemittanceNo(r.getRemittanceNo());
        sl.setRemittanceDate(r.getRemittanceDate() != null ? r.getRemittanceDate().toString() : null);
        sl.setLineNo(r.getLineNo());
        sl.setOfficeAccount(r.getOfficeAccount());
        sl.setClientLastNamePdf(r.getClientLastNamePdf());
        sl.setMedicaidClientId(r.getMedicaidClientId());
        sl.setClientFullName(r.getClientFullName());
        sl.setTcn(r.getTcn());
        sl.setDateOfService(r.getDateOfService() != null ? r.getDateOfService().toString() : null);
        sl.setProcCode(r.getProcCode());
        sl.setUnits(r.getUnits() != null ? r.getUnits().toPlainString() : null);
        sl.setCharged(r.getCharged() != null ? r.getCharged().toPlainString() : null);
        sl.setPaid(r.getPaid() != null ? r.getPaid().toPlainString() : null);
        sl.setStatus(r.getStatus());
        sl.setErrorsOrNotes(r.getErrorsOrNotes());
        return sl;
    }

    private MedicaidRemittanceWeeklyPaidVO.BreakdownRow buildBreakdown(
            String label, List<MedicaidRemittanceRecord> rows) {
        MedicaidRemittanceWeeklyPaidVO.BreakdownRow row = new MedicaidRemittanceWeeklyPaidVO.BreakdownRow();
        row.setLabel(label);
        BigDecimal paid = rows.stream()
                .map(r -> r.getPaid() != null ? r.getPaid() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        row.setPaidAmount(paid.toPlainString());
        BigDecimal units = rows.stream()
                .map(r -> r.getUnits() != null ? r.getUnits() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        row.setUnits(units.toPlainString());
        row.setLineCount(rows.size());
        return row;
    }
}
