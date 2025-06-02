package com.jakem.nettracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class AssetCreateValidationTest {

    @Autowired
    private AssetRepository assetRepository;

    @Test
    public void testAssetCreationWithValidData() {
        Asset testAsset = new Asset();
        testAsset.setName("VTI");
        testAsset.setCategory("STOCK");
        testAsset.setUnits(BigDecimal.valueOf(100));
        testAsset.setUnitValue(BigDecimal.valueOf(275));

        Asset saved = assetRepository.save(testAsset);

        assertNotNull(saved.getId());
        assertThat(saved.getName()).isEqualTo("VTI");
        assertThat(saved.getCategory()).isEqualTo("STOCK");
        assertThat(saved.getUnits()).isEqualByComparingTo(BigDecimal.valueOf(100));
        assertThat(saved.getUnitValue()).isEqualByComparingTo(BigDecimal.valueOf(275));
    }
}