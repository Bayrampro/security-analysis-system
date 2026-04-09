package com.byprogger.security_analysis_system.repository;

import com.byprogger.security_analysis_system.entity.Threat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ThreatRepository extends JpaRepository<Threat, Long> {
    List<Threat> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
}
