package com.FinSight_Analytics.FinSight_Analytics.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.FinSight_Analytics.FinSight_Analytics.DTO.AnalyticsResponseDTO;
import com.FinSight_Analytics.FinSight_Analytics.DTO.MonthlyBalanceResponseDTO;
import com.FinSight_Analytics.FinSight_Analytics.DTO.TransactionRequestDTO;
import com.FinSight_Analytics.FinSight_Analytics.Enums.TransactionType;
import com.FinSight_Analytics.FinSight_Analytics.Model.TransactionEntity;
import com.FinSight_Analytics.FinSight_Analytics.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.FinSight_Analytics.FinSight_Analytics.Exception.ResourceNotFoundException;


import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TransactionService {
    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    public static final String INCOME = "INCOME";
    public static final String EXPENSE = "EXPENSE";

    @Autowired
    private TransactionRepository transactionRepository;
    public TransactionEntity saveTransaction(TransactionRequestDTO request) {
        log.info("Saving transaction: {}", request);
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setCategory(request.getCategory());
        transaction.setTransactionDate(request.getTransactionDate());

        return transactionRepository.save(transaction);
    }
    public Map<String, BigDecimal> getCategoryWiseExpense(YearMonth month) {
        log.info("Fetching category-wise expense for {}", month);
        //List<TransactionEntity> transactions = transactionRepository.findAll();
//        LocalDate startDate = month.atDay(1);
//        LocalDate endDate = month.atEndOfMonth();
//
//        List<TransactionEntity> transactions =
//                transactionRepository.findByTransactionDateBetween(startDate, endDate);
//
//
//        Map<String, BigDecimal> result = transactions.stream()
//                .filter(t -> t.getType() == TransactionType.EXPENSE
//                        && YearMonth.from(t.getTransactionDate()).equals(month))
//                .collect(Collectors.groupingBy(
//                        TransactionEntity::getCategory,
//                        Collectors.reducing(BigDecimal.ZERO, TransactionEntity::getAmount, BigDecimal::add)
//                ));
//        if(result.isEmpty()){
//            throw new ResourceNotFoundException("No expense data found for month: " + month);
//        }
//        return result;
        List<Object[]> results =
                transactionRepository.getCategoryWiseTotal(
                        TransactionType.EXPENSE,
                        month.getYear(),
                        month.getMonthValue()
                );

        if (results.isEmpty()) {
            throw new ResourceNotFoundException("No expense data found for month: " + month);
        }

        Map<String, BigDecimal> expenseMap = new HashMap<>();
        for (Object[] row : results) {
            expenseMap.put((String) row[0], new BigDecimal(row[1].toString())
            );
        }

        return expenseMap;
    }

    public Map<String, BigDecimal> getCategoryWiseIncome(YearMonth month) {
        log.info("Fetching category-wise income for {}", month);
        //List<TransactionEntity> transactions = transactionRepository.findAll();
//        LocalDate startDate = month.atDay(1);
//        LocalDate endDate = month.atEndOfMonth();
//
//        List<TransactionEntity> transactions =
//                transactionRepository.findByTransactionDateBetween(startDate, endDate);
//
//        Map<String, BigDecimal> result= transactions.stream()
//                .filter(t -> t.getType() == TransactionType.INCOME
//                        && YearMonth.from(t.getTransactionDate()).equals(month))
//                .collect(Collectors.groupingBy(
//                        TransactionEntity::getCategory,
//                        Collectors.reducing(BigDecimal.ZERO, TransactionEntity::getAmount, BigDecimal::add)
//                ));
//        if(result.isEmpty()){
//            throw new ResourceNotFoundException("No income data found for month: " + month);
//        }
//        return result;
        List<Object[]> results =
                transactionRepository.getCategoryWiseTotal(
                        TransactionType.INCOME,
                        month.getYear(),
                        month.getMonthValue()
                );

        if (results.isEmpty()) {
            throw new ResourceNotFoundException("No income data found for month: " + month);
        }

        Map<String, BigDecimal> incomeMap = new HashMap<>();
        for (Object[] row : results) {
            incomeMap.put((String) row[0], new BigDecimal(row[1].toString())
            );
        }

        return incomeMap;
    }

    public Map<String, BigDecimal> getMonthlyIncomeVsExpense(YearMonth month) {
        log.info("Calculating monthly income vs expense for {}", month);

        Object[] result = transactionRepository.getMonthlyIncomeAndExpense(
                month.getYear(),
                month.getMonthValue()
        );

        BigDecimal income = result[0] != null
                ? new BigDecimal(result[0].toString())
                : BigDecimal.ZERO;

        BigDecimal expense = result[1] != null
                ? new BigDecimal(result[1].toString())
                : BigDecimal.ZERO;

        if (income.equals(BigDecimal.ZERO) && expense.equals(BigDecimal.ZERO)) {
            throw new ResourceNotFoundException("No transactions found for month: " + month);
        }

        Map<String, BigDecimal> response = new HashMap<>();
        response.put(INCOME, income);
        response.put(EXPENSE, expense);

        return response;
    }

    public BigDecimal getMonthlyBalance(YearMonth month){
        log.info("Calculating monthly balance for {}", month);

        Map<String, BigDecimal> summary = getMonthlyIncomeVsExpense(month);
        return summary.get(INCOME).subtract(summary.get(EXPENSE));
    }

    public AnalyticsResponseDTO getCategoryWiseAnalytics(YearMonth month) {

//        Map<String, BigDecimal> expenseMap = new HashMap<>();
//        Map<String, BigDecimal> incomeMap = new HashMap<>();

        //List<TransactionEntity> transactions = transactionRepository.findAll();
//        LocalDate startDate = month.atDay(1);
//        LocalDate endDate = month.atEndOfMonth();
//
//        List<TransactionEntity> transactions =
//                transactionRepository.findByTransactionDateBetween(startDate, endDate);
//
//        for (TransactionEntity t : transactions) {
//            if (YearMonth.from(t.getTransactionDate()).equals(month)) {
//
//                Map<String, BigDecimal> targetMap =
//                        t.getType() == TransactionType.EXPENSE ? expenseMap : incomeMap;
//
//                targetMap.put(
//                        t.getCategory(),
//                        targetMap.getOrDefault(t.getCategory(), BigDecimal.ZERO)
//                                .add(t.getAmount())
//                );
//            }
//        }
//        if (expenseMap.isEmpty() && incomeMap.isEmpty()) {
//            throw new ResourceNotFoundException("No analytics data found for month: " + month);
//        }
//        AnalyticsResponseDTO response = new AnalyticsResponseDTO();
//        response.setMonth(month.toString());
//        response.setCategoryWiseExpenses(expenseMap);
//        response.setCategoryWiseIncome(new HashMap<>());
//
//        return response;
        Map<String, BigDecimal> expenseMap = getCategoryWiseExpense(month);
        Map<String, BigDecimal> incomeMap = getCategoryWiseIncome(month);

        AnalyticsResponseDTO response = new AnalyticsResponseDTO();
        response.setMonth(month.toString());
        response.setCategoryWiseExpenses(expenseMap);
        response.setCategoryWiseIncome(incomeMap);

        return response;

    }
    public MonthlyBalanceResponseDTO getMonthlyBalanceResponse(YearMonth month) {
        Map<String, BigDecimal> summary = getMonthlyIncomeVsExpense(month);

        BigDecimal income = summary.get(INCOME);
        BigDecimal expense = summary.get(EXPENSE);
        MonthlyBalanceResponseDTO dto = new MonthlyBalanceResponseDTO();
        dto.setMonth(month.toString());
        dto.setTotalIncome(income);
        dto.setTotalExpense(expense);
        dto.setBalance(income.subtract(expense));

        return dto;
    }
}
