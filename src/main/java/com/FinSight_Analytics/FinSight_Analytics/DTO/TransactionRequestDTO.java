package com.FinSight_Analytics.FinSight_Analytics.DTO;
import com.FinSight_Analytics.FinSight_Analytics.Enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
public class TransactionRequestDTO {
    @NotNull
    @Positive
    private BigDecimal amount;
    @NotNull
    private TransactionType type;
    @NotBlank
    private String category;
    @NotNull
    private LocalDate transactionDate;

    // getters and setters
}
