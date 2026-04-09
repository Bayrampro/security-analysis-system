package com.byprogger.security_analysis_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AssetDto {

    private Long id;

    @NotBlank(message = "Название объекта обязательно")
    @Size(max = 150, message = "Название должно быть до 150 символов")
    private String name;

    @Size(max = 1000, message = "Описание должно быть до 1000 символов")
    private String description;

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
}
