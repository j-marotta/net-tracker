package com.jakem.nettracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AssetController.class)
public class AssetControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssetRepository assetRepository;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testAssetCreationWithEmptyName() throws Exception {
        AssetRequest invalidTestAsset = new AssetRequest();
        invalidTestAsset.setName(" ");
        invalidTestAsset.setCategory("STOCK");
        invalidTestAsset.setUnits(BigDecimal.valueOf(100));
        invalidTestAsset.setUnitValue(BigDecimal.valueOf(275));

        mockMvc.perform(
                post("/api/assets")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidTestAsset))
                )
                .andExpect(status().isBadRequest());

        verifyNoInteractions(assetRepository);
    }

    @Test
    public void testAssetCreationWithNegativeUnits() throws Exception {
        AssetRequest invalidTestAsset = new AssetRequest();
        invalidTestAsset.setName("VTI");
        invalidTestAsset.setCategory("STOCK");
        invalidTestAsset.setUnits(BigDecimal.valueOf(-100));
        invalidTestAsset.setUnitValue(BigDecimal.valueOf(275));

        mockMvc.perform(
                post("/api/assets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidTestAsset))
        )
                .andExpect(status().isBadRequest());

        verifyNoInteractions(assetRepository);
    }
}
