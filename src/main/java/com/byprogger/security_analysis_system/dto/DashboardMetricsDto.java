package com.byprogger.security_analysis_system.dto;

import java.util.LinkedHashMap;
import java.util.Map;

public class DashboardMetricsDto {

    private long assetCount;
    private long threatCount;
    private Map<String, Long> riskLevels = new LinkedHashMap<>();
    private Map<String, Long> threatsByAsset = new LinkedHashMap<>();
    private Map<String, Long> threatDynamics = new LinkedHashMap<>();

    public long getAssetCount() {
        return assetCount;
    }

    public void setAssetCount(long assetCount) {
        this.assetCount = assetCount;
    }

    public long getThreatCount() {
        return threatCount;
    }

    public void setThreatCount(long threatCount) {
        this.threatCount = threatCount;
    }

    public Map<String, Long> getRiskLevels() {
        return riskLevels;
    }

    public void setRiskLevels(Map<String, Long> riskLevels) {
        this.riskLevels = riskLevels;
    }

    public Map<String, Long> getThreatsByAsset() {
        return threatsByAsset;
    }

    public void setThreatsByAsset(Map<String, Long> threatsByAsset) {
        this.threatsByAsset = threatsByAsset;
    }

    public Map<String, Long> getThreatDynamics() {
        return threatDynamics;
    }

    public void setThreatDynamics(Map<String, Long> threatDynamics) {
        this.threatDynamics = threatDynamics;
    }
}
