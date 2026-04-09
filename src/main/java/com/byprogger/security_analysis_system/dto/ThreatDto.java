package com.byprogger.security_analysis_system.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ThreatDto {

    private Long id;

    @NotBlank(message = "Название угрозы обязательно")
    @Size(max = 200, message = "Название должно быть до 200 символов")
    private String name;

    @NotBlank(message = "Описание угрозы обязательно")
    @Size(max = 1500, message = "Описание должно быть до 1500 символов")
    private String description;

    @NotNull(message = "Вероятность обязательна")
    @DecimalMin(value = "0.0", message = "Вероятность должна быть не меньше 0")
    @DecimalMax(value = "1.0", message = "Вероятность должна быть не больше 1")
    private Double probability;

    @NotNull(message = "Потенциальный ущерб обязателен")
    @DecimalMin(value = "0.0", message = "Ущерб должен быть не меньше 0")
    @DecimalMax(value = "10.0", message = "Ущерб должен быть не больше 10")
    private Double impact;

    @NotNull(message = "Объект защиты обязателен")
    private Long assetId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public Double getImpact() {
        return impact;
    }

    public void setImpact(Double impact) {
        this.impact = impact;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }
}
