package com.jakem.nettracker.plaid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/plaid")
@RequiredArgsConstructor
public class PlaidController {

    private final PlaidService plaidService;

    @GetMapping("/link_token")
    public ResponseEntity<Map<String, String>> getLinkToken() {
        try {
            String clientUserId = "test-user-id";
            String linkToken = plaidService.createTokenLink(clientUserId);
            return ResponseEntity.ok(Map.of("link_token", linkToken));
        } catch (IOException e) {
            return ResponseEntity
                    .status(500)
                    .body(Map.of("error", "Failed to create link token: " + e.getMessage()));
        }
    }

    @PostMapping("/exchange")
    public ResponseEntity<Map<String, String>> exchangePublicToken(
            @RequestBody Map<String, String> body) {

        String publicToken = body.get("public_token");
        if (publicToken == null || publicToken.isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "public_token is required"));
        }

        try {
            String accessToken = plaidService.exchangePublicToken(publicToken);

            // (3) TODO: Save accessToken in your database for this user.
            return ResponseEntity.ok(Map.of("accessToken", accessToken));

        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Could not exchange public token: " + e.getMessage()));
        }
    }
}
