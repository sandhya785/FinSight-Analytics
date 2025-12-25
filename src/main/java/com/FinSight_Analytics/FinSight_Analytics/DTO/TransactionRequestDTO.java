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
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount should be positive")
    private BigDecimal amount;
    @NotNull(message = "Transaction type is required")
    private TransactionType type;
    @NotBlank(message = "category cannot be empty")
    private String category;
    @NotNull(message = "transaction date is required")
    private LocalDate transactionDate;

    // getters and setters
}
