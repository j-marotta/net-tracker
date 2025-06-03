package com.jakem.nettracker.service;

import com.jakem.nettracker.Asset;
import com.jakem.nettracker.AssetRepository;
import com.jakem.nettracker.AssetRequest;
import com.jakem.nettracker.exception.InvalidTickerException;
import com.jakem.nettracker.pricing.PriceClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;
    private final PriceClient     priceClient;

    @Transactional
    public Asset create(AssetRequest dto) {
        if (priceClient.quote(dto.getName()).isEmpty())
            throw new InvalidTickerException(dto.getName());

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
    public void delete(Long id) { assetRepository.deleteById(id); }

    public BigDecimal totalNetWorth() {
        return assetRepository.findAll().stream()
                .map(a -> a.getUnits().multiply(a.getUnitValue()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Scheduled(fixedRateString = "PT1M")
    @Transactional
    public void refreshPrices() {
        assetRepository.findAll().forEach(asset -> {
            if ("STOCK".equalsIgnoreCase(asset.getCategory())
                    || "CRYPTO".equalsIgnoreCase(asset.getCategory())) {

                priceClient.quote(asset.getName()).ifPresent(price -> {
                    asset.setUnitValue(price);
                    asset.setUpdated(java.time.LocalDateTime.now());
                });
            }
        });
    }

    private static Asset map(AssetRequest dto) {
        Asset a = new Asset();
        a.setName(dto.getName());
        a.setCategory(dto.getCategory());
        a.setUnits(dto.getUnits());
        a.setPurchasePrice(dto.getPurchasePrice());
        a.setUnitValue(dto.getPurchasePrice());
        return a;
    }
}
