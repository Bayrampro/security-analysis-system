/**
 * Сервис объектов защиты: бизнес-логика CRUD и преобразование сущности в DTO.
 */

package com.byprogger.security_analysis_system.service;

import com.byprogger.security_analysis_system.dto.AssetDto;
import com.byprogger.security_analysis_system.entity.Asset;
import com.byprogger.security_analysis_system.repository.AssetRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssetService {

    private final AssetRepository assetRepository;

    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @Transactional(readOnly = true)
    public List<Asset> getAllAssets() {
        return assetRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Transactional(readOnly = true)
    public AssetDto getAssetDtoById(Long id) {
        Asset asset = getAssetById(id);
        AssetDto dto = new AssetDto();
        dto.setId(asset.getId());
        dto.setName(asset.getName());
        dto.setDescription(asset.getDescription());
        return dto;
    }

    @Transactional
    public Asset createAsset(AssetDto dto) {
        Asset asset = new Asset();
        asset.setName(dto.getName());
        asset.setDescription(dto.getDescription());
        return assetRepository.save(asset);
    }

    @Transactional
    public Asset updateAsset(Long id, AssetDto dto) {
        Asset asset = getAssetById(id);
        asset.setName(dto.getName());
        asset.setDescription(dto.getDescription());
        return assetRepository.save(asset);
    }

    @Transactional
    public void deleteAsset(Long id) {
        if (!assetRepository.existsById(id)) {
            throw new IllegalArgumentException("Объект защиты не найден");
        }
        assetRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Asset getAssetById(Long id) {
        return assetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Объект защиты не найден"));
    }
}
