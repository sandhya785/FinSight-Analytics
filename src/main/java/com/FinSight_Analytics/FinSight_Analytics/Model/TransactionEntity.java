package com.FinSight_Analytics.FinSight_Analytics.Model;
import com.FinSight_Analytics.FinSight_Analytics.Enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;

import lombok.Setter;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 12, scale = 2)
    @NotNull
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false, length = 50)

    private String category;

    @Column(nullable = false)
    private LocalDate transactionDate;

    // getters and setters
}
