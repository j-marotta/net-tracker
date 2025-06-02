package com.jakem.nettracker;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Asset {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String category;

    private BigDecimal units;

    private BigDecimal unitValue;

    private LocalDateTime updated;

    @PrePersist
    @PreUpdate
    private void stamp() {
        updated = LocalDateTime.now();
    }
}
