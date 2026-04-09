package com.byprogger.security_analysis_system.repository;

import com.byprogger.security_analysis_system.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    Optional<Asset> findByName(String name);
}
