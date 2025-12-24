package com.FinSight_Analytics.FinSight_Analytics.Service;

import com.FinSight_Analytics.FinSight_Analytics.DTO.AnalyticsResponseDTO;
import com.FinSight_Analytics.FinSight_Analytics.DTO.MonthlyBalanceResponseDTO;
import com.FinSight_Analytics.FinSight_Analytics.DTO.TransactionRequestDTO;
import com.FinSight_Analytics.FinSight_Analytics.Enums.TransactionType;
import com.FinSight_Analytics.FinSight_Analytics.Model.TransactionEntity;
import com.FinSight_Analytics.FinSight_Analytics.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    public TransactionEntity saveTransaction(TransactionRequestDTO request) {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setCategory(request.getCategory());
        transaction.setTransactionDate(request.getTransactionDate());

        return transactionRepository.save(transaction);
    }
    public Map<String, BigDecimal> getCategoryWiseExpense(YearMonth month) {
        List<TransactionEntity> transactions = transactionRepository.findAll();

        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE
                        && YearMonth.from(t.getTransactionDate()).equals(month))
                .collect(Collectors.groupingBy(
                        TransactionEntity::getCategory,
                        Collectors.reducing(BigDecimal.ZERO, TransactionEntity::getAmount, BigDecimal::add)
                ));
    }

    public Map<String, BigDecimal> getCategoryWiseIncome(YearMonth month) {
        List<TransactionEntity> transactions = transactionRepository.findAll();

        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME
                        && YearMonth.from(t.getTransactionDate()).equals(month))
                .collect(Collectors.groupingBy(
                        TransactionEntity::getCategory,
                        Collectors.reducing(BigDecimal.ZERO, TransactionEntity::getAmount, BigDecimal::add)
                ));
    }

    public Map<String, BigDecimal> getMonthlyIncomeVsExpense(YearMonth month) {
        List<TransactionEntity> transactions = transactionRepository.findAll();

        BigDecimal income = transactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME
                        && YearMonth.from(t.getTransactionDate()).equals(month))
                .map(TransactionEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expense = transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE
                        && YearMonth.from(t.getTransactionDate()).equals(month))
                .map(TransactionEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return Map.of(
                "income", income,
                "expense", expense
        );
    }
    public BigDecimal getMonthlyBalance(YearMonth month){
        List<TransactionEntity> transactions = transactionRepository.findAll();

        BigDecimal totalIncome = transactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME
                        && YearMonth.from(t.getTransactionDate()).equals(month))
                .map(TransactionEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE
                        && YearMonth.from(t.getTransactionDate()).equals(month))
                .map(TransactionEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalIncome.subtract(totalExpense);
    }

    public AnalyticsResponseDTO getCategoryWiseAnalytics(YearMonth month) {

        Map<String, BigDecimal> expenseMap = new HashMap<>();
        Map<String, BigDecimal> incomeMap = new HashMap<>();

        List<TransactionEntity> transactions = transactionRepository.findAll();

        for (TransactionEntity t : transactions) {
            if (YearMonth.from(t.getTransactionDate()).equals(month)) {

                Map<String, BigDecimal> targetMap =
                        t.getType() == TransactionType.EXPENSE ? expenseMap : incomeMap;

                targetMap.put(
                        t.getCategory(),
                        targetMap.getOrDefault(t.getCategory(), BigDecimal.ZERO)
                                .add(t.getAmount())
                );
            }
        }

        AnalyticsResponseDTO response = new AnalyticsResponseDTO();
        response.setMonth(month.toString());
        response.setCategoryWiseExpenses(expenseMap);
        response.setCategoryWiseIncome(incomeMap);

        return response;
    }
    public MonthlyBalanceResponseDTO getMonthlyBalanceResponse(YearMonth month) {

        BigDecimal income = getMonthlyIncomeVsExpense(month).get("INCOME");
        BigDecimal expense = getMonthlyIncomeVsExpense(month).get("EXPENSE");

        MonthlyBalanceResponseDTO dto = new MonthlyBalanceResponseDTO();
        dto.setMonth(month.toString());
        dto.setTotalIncome(income);
        dto.setTotalExpense(expense);
        dto.setBalance(income.subtract(expense));

        return dto;
    }
}
