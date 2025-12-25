package com.FinSight_Analytics.FinSight_Analytics.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class AnalyticsResponseDTO {
    private String month;
    private Map<String, BigDecimal> categoryWiseExpenses=new HashMap<>();
    private Map<String, BigDecimal> categoryWiseIncome=new HashMap<>();
}
