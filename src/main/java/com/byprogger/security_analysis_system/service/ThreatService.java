/**
 * Сервис угроз: CRUD, расчет уровня риска, рекомендации и выборки для отчетности.
 */

package com.byprogger.security_analysis_system.service;

import com.byprogger.security_analysis_system.dto.ThreatDto;
import com.byprogger.security_analysis_system.dto.ThreatViewDto;
import com.byprogger.security_analysis_system.entity.Asset;
import com.byprogger.security_analysis_system.entity.RiskLevel;
import com.byprogger.security_analysis_system.entity.Threat;
import com.byprogger.security_analysis_system.repository.AssetRepository;
import com.byprogger.security_analysis_system.repository.ThreatRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ThreatService {

    private final ThreatRepository threatRepository;
    private final AssetRepository assetRepository;

    public ThreatService(ThreatRepository threatRepository, AssetRepository assetRepository) {
        this.threatRepository = threatRepository;
        this.assetRepository = assetRepository;
    }

    @Transactional(readOnly = true)
    public List<Threat> getAllThreatEntities() {
        return threatRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    @Transactional(readOnly = true)
    public List<ThreatViewDto> getAllThreats() {
        return getAllThreatEntities().stream()
                .map(this::toViewDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ThreatDto getThreatDtoById(Long id) {
        Threat threat = getThreatEntityById(id);
        ThreatDto dto = new ThreatDto();
        dto.setId(threat.getId());
        dto.setName(threat.getName());
        dto.setDescription(threat.getDescription());
        dto.setProbability(threat.getProbability());
        dto.setImpact(threat.getImpact());
        dto.setAssetId(threat.getAsset().getId());
        return dto;
    }

    @Transactional(readOnly = true)
    public List<ThreatViewDto> getThreatsByDateRange(LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null && toDate == null) {
            return getAllThreats();
        }

        LocalDateTime from = fromDate != null
                ? fromDate.atStartOfDay()
                : LocalDate.of(1970, 1, 1).atStartOfDay();

        LocalDateTime to = toDate != null
                ? toDate.plusDays(1).atStartOfDay().minusNanos(1)
                : LocalDateTime.now().plusYears(50);

        if (from.isAfter(to)) {
            return List.of();
        }

        return threatRepository.findByCreatedAtBetween(from, to).stream()
                .sorted((left, right) -> right.getCreatedAt().compareTo(left.getCreatedAt()))
                .map(this::toViewDto)
                .toList();
    }

    @Transactional
    public Threat createThreat(ThreatDto dto) {
        Threat threat = new Threat();
        applyThreatData(threat, dto);
        return threatRepository.save(threat);
    }

    @Transactional
    public Threat updateThreat(Long id, ThreatDto dto) {
        Threat threat = getThreatEntityById(id);
        applyThreatData(threat, dto);
        return threatRepository.save(threat);
    }

    @Transactional
    public void deleteThreat(Long id) {
        if (!threatRepository.existsById(id)) {
            throw new IllegalArgumentException("Угроза не найдена");
        }
        threatRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Threat getThreatEntityById(Long id) {
        return threatRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Угроза не найдена"));
    }

    @Transactional(readOnly = true)
    public ThreatViewDto getThreatById(Long id) {
        return toViewDto(getThreatEntityById(id));
    }

    // Расчет уровня риска выполняется по формуле из ТЗ: Risk = Probability * Impact.
    public RiskLevel calculateRiskLevel(double probability, double impact) {
        double riskScore = probability * impact;
        if (riskScore >= 7.0) {
            return RiskLevel.HIGH;
        }
        if (riskScore >= 3.0) {
            return RiskLevel.MEDIUM;
        }
        return RiskLevel.LOW;
    }

    public String buildRecommendation(Threat threat) {
        return switch (threat.getRiskLevel()) {
            case HIGH -> "Немедленно внедрить компенсирующие меры, усилить контроль доступа и мониторинг.";
            case MEDIUM -> "Запланировать меры снижения риска, обновить регламенты и провести аудит настроек.";
            case LOW -> "Поддерживать текущие меры защиты и выполнять периодический контроль.";
        };
    }

    private void applyThreatData(Threat threat, ThreatDto dto) {
        Asset asset = assetRepository.findById(dto.getAssetId())
                .orElseThrow(() -> new IllegalArgumentException("Объект защиты не найден"));

        threat.setName(dto.getName());
        threat.setDescription(dto.getDescription());
        threat.setProbability(dto.getProbability());
        threat.setImpact(dto.getImpact());
        threat.setRiskLevel(calculateRiskLevel(dto.getProbability(), dto.getImpact()));
        threat.setAsset(asset);
    }

    private ThreatViewDto toViewDto(Threat threat) {
        ThreatViewDto dto = new ThreatViewDto();
        dto.setId(threat.getId());
        dto.setName(threat.getName());
        dto.setDescription(threat.getDescription());
        dto.setProbability(threat.getProbability());
        dto.setImpact(threat.getImpact());
        dto.setRiskScore(threat.getRiskScore());
        dto.setRiskLevel(threat.getRiskLevel());
        dto.setAssetId(threat.getAsset().getId());
        dto.setAssetName(threat.getAsset().getName());
        dto.setCreatedAt(threat.getCreatedAt());
        dto.setRecommendation(buildRecommendation(threat));
        return dto;
    }
}
