package com.jakem.nettracker.service;


import com.jakem.nettracker.Asset;
import com.jakem.nettracker.AssetRepository;
import com.jakem.nettracker.AssetRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;

    private final PriceClient priceClient;

    @Transactional
    public Asset create(AssetRequest dto) {
        Asset asset = map(dto);
        return assetRepository.save(asset);
    }

    public List<Asset> list() {
        return assetRepository.findAll();
    }

    @Transactional
    public Asset update(Long id, AssetRequest dto) {
        return assetRepository.findById(id)
                .map(existing -> {
                    existing.setName(dto.getName());
                    existing.setCategory(dto.getCategory());
                    existing.setUnits(dto.getUnits());
                    existing.setUnitValue(dto.getUnitValue());
                    existing.setUpdated(java.time.LocalDateTime.now());
                    return assetRepository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Asset " + id + " not found"));
    }

    @Transactional
    public void delete(Long id) {
        assetRepository.deleteById(id);
    }

    public BigDecimal totalNetWorth() {
        return assetRepository.findAll().stream()
                .map(asset -> asset.getUnits().multiply(asset.getUnitValue()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @org.springframework.scheduling.annotation.Scheduled(fixedRateString = "PT15M")
    @jakarta.transaction.Transactional
    public void refreshPrices() {
        assetRepository.findAll().forEach(asset -> {
            // only update categories that have market prices
            if ("STOCK".equalsIgnoreCase(asset.getCategory()) ||
                    "CRYPTO".equalsIgnoreCase(asset.getCategory())) {

                try {
                    BigDecimal price = priceClient.latestPrice(asset.getName());
                    asset.setUnitValue(price);
                } catch (Exception ex) {
                    System.err.println("Price fetch failed for " + asset.getName());
                }
            }
        });
    }


    private static Asset map(AssetRequest dto) {
        Asset a = new Asset();
        a.setName(dto.getName());
        a.setCategory(dto.getCategory());
        a.setUnits(dto.getUnits());
        a.setUnitValue(dto.getUnitValue());
        return a;
    }
}
