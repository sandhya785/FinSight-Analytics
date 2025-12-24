package com.FinSight_Analytics.FinSight_Analytics.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class MonthlyBalanceResponseDTO {

    private String month;
    @NotNull
    private BigDecimal totalIncome;
    @NotNull
    private BigDecimal totalExpense;
    @NotNull
    private BigDecimal balance;
}
