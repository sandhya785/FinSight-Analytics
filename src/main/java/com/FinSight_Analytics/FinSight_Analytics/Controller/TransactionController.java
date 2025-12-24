package com.FinSight_Analytics.FinSight_Analytics.Controller;

import com.FinSight_Analytics.FinSight_Analytics.DTO.AnalyticsResponseDTO;
import com.FinSight_Analytics.FinSight_Analytics.DTO.MonthlyBalanceResponseDTO;
import com.FinSight_Analytics.FinSight_Analytics.DTO.TransactionRequestDTO;
import com.FinSight_Analytics.FinSight_Analytics.Model.TransactionEntity;
import com.FinSight_Analytics.FinSight_Analytics.Service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @PostMapping
    public ResponseEntity<?> createTransaction(
            @Valid @RequestBody TransactionRequestDTO request) {

        TransactionEntity saved = transactionService.saveTransaction(request);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    @GetMapping("/analytics/category-expense")
    public AnalyticsResponseDTO getCategoryExpense(@RequestParam String month) {
        YearMonth ym = YearMonth.parse(month, DateTimeFormatter.ofPattern("yyyy-MM"));

        AnalyticsResponseDTO response = new AnalyticsResponseDTO();
        response.setMonth(month);
        response.setCategoryWiseExpenses(transactionService.getCategoryWiseExpense(ym));

        return response;
    }
    @GetMapping("/analytics/category-income")
    public AnalyticsResponseDTO getCategoryIncome(@RequestParam String month) {
        YearMonth ym = YearMonth.parse(month, DateTimeFormatter.ofPattern("yyyy-MM"));

        AnalyticsResponseDTO response = new AnalyticsResponseDTO();
        response.setMonth(month);
        response.setCategoryWiseIncome(transactionService.getCategoryWiseIncome(ym));

        return response;
    }
    @GetMapping("/analytics/monthly-summary")
    public Map<String, BigDecimal> getMonthlySummary(@RequestParam String month) {
        YearMonth ym = YearMonth.parse(month, DateTimeFormatter.ofPattern("yyyy-MM"));
        return transactionService.getMonthlyIncomeVsExpense(ym);
    }
//    @GetMapping("/analytics/monthly-balance")
//    public BigDecimal getMonthlyBalance(@RequestParam String month) {
//        YearMonth ym = YearMonth.parse(month, DateTimeFormatter.ofPattern("yyyy-MM"));
//        return transactionService.getMonthlyBalance(ym);
//    }
    @GetMapping("/analytics/category-wise")
    public AnalyticsResponseDTO getCategoryWiseAnalytics(@RequestParam String month) {
        YearMonth ym = YearMonth.parse(month);
        return transactionService.getCategoryWiseAnalytics(ym);
    }
    @GetMapping("/analytics/monthly-balance")
    public MonthlyBalanceResponseDTO getMonthlyBalance(@RequestParam String month) {
        YearMonth ym = YearMonth.parse(month);
        return transactionService.getMonthlyBalanceResponse(ym);
    }

}
