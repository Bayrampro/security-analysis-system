package com.byprogger.security_analysis_system.service;

import com.byprogger.security_analysis_system.dto.DashboardMetricsDto;
import com.byprogger.security_analysis_system.entity.RiskLevel;
import com.byprogger.security_analysis_system.entity.Threat;
import com.byprogger.security_analysis_system.repository.AssetRepository;
import com.byprogger.security_analysis_system.repository.ThreatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final AssetRepository assetRepository;
    private final ThreatRepository threatRepository;

    public DashboardService(AssetRepository assetRepository, ThreatRepository threatRepository) {
        this.assetRepository = assetRepository;
        this.threatRepository = threatRepository;
    }

    @Transactional(readOnly = true)
    public DashboardMetricsDto buildMetrics() {
        DashboardMetricsDto metrics = new DashboardMetricsDto();

        List<Threat> threats = threatRepository.findAll();
        metrics.setAssetCount(assetRepository.count());
        metrics.setThreatCount(threats.size());

        metrics.setRiskLevels(buildRiskLevels(threats));
        metrics.setThreatsByAsset(buildThreatsByAsset(threats));
        metrics.setThreatDynamics(buildThreatDynamics(threats));

        return metrics;
    }

    private Map<String, Long> buildRiskLevels(List<Threat> threats) {
        Map<RiskLevel, Long> grouped = threats.stream()
                .collect(Collectors.groupingBy(Threat::getRiskLevel, () -> new EnumMap<>(RiskLevel.class), Collectors.counting()));

        Map<String, Long> riskLevels = new LinkedHashMap<>();
        riskLevels.put(RiskLevel.LOW.name(), grouped.getOrDefault(RiskLevel.LOW, 0L));
        riskLevels.put(RiskLevel.MEDIUM.name(), grouped.getOrDefault(RiskLevel.MEDIUM, 0L));
        riskLevels.put(RiskLevel.HIGH.name(), grouped.getOrDefault(RiskLevel.HIGH, 0L));
        return riskLevels;
    }

    private Map<String, Long> buildThreatsByAsset(List<Threat> threats) {
        return threats.stream()
                .collect(Collectors.groupingBy(t -> t.getAsset().getName(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (left, right) -> left,
                        LinkedHashMap::new
                ));
    }

    private Map<String, Long> buildThreatDynamics(List<Threat> threats) {
        Map<String, Long> groupedByDate = threats.stream()
                .sorted(Comparator.comparing(Threat::getCreatedAt))
                .collect(Collectors.groupingBy(
                        t -> t.getCreatedAt().toLocalDate().format(DATE_FORMATTER),
                        TreeMap::new,
                        Collectors.counting()
                ));

        return new LinkedHashMap<>(groupedByDate);
    }
}
