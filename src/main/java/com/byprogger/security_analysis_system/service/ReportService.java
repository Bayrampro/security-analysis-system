package com.byprogger.security_analysis_system.service;

import com.byprogger.security_analysis_system.dto.ReportFilterDto;
import com.byprogger.security_analysis_system.dto.ThreatViewDto;
import com.byprogger.security_analysis_system.entity.RiskLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final ThreatService threatService;

    public ReportService(ThreatService threatService) {
        this.threatService = threatService;
    }

    @Transactional(readOnly = true)
    public List<ThreatViewDto> getThreatReport(ReportFilterDto filterDto) {
        return threatService.getThreatsByDateRange(filterDto.getFromDate(), filterDto.getToDate());
    }

    @Transactional(readOnly = true)
    public Map<String, Long> buildRiskSummary(List<ThreatViewDto> reportRows) {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put(RiskLevel.LOW.name(), reportRows.stream().filter(r -> r.getRiskLevel() == RiskLevel.LOW).count());
        summary.put(RiskLevel.MEDIUM.name(), reportRows.stream().filter(r -> r.getRiskLevel() == RiskLevel.MEDIUM).count());
        summary.put(RiskLevel.HIGH.name(), reportRows.stream().filter(r -> r.getRiskLevel() == RiskLevel.HIGH).count());
        return summary;
    }
}
