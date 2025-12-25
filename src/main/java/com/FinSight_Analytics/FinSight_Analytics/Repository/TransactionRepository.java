package com.FinSight_Analytics.FinSight_Analytics.Repository;

import com.FinSight_Analytics.FinSight_Analytics.Enums.TransactionType;
import com.FinSight_Analytics.FinSight_Analytics.Model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findByTransactionDateBetween(
            LocalDate startDate,
            LocalDate endDate
    );
    // Total income/expense per category for a given month
    @Query("SELECT t.category, SUM(t.amount) FROM TransactionEntity t WHERE t.type = :type AND YEAR(t.transactionDate) = :year AND MONTH(t.transactionDate) = :month GROUP BY t.category")
    List<Object[]> getCategoryWiseTotal(@Param("type") TransactionType type, @Param("year") int year, @Param("month") int month);

    // Total income/expense for a month
    @Query("SELECT SUM(t.amount) FROM TransactionEntity t WHERE t.type = :type AND YEAR(t.transactionDate) = :year AND MONTH(t.transactionDate) = :month")
    BigDecimal getTotalAmountByTypeAndMonth(@Param("type") TransactionType type, @Param("year") int year, @Param("month") int month);
    @Query("""
    SELECT 
        SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END),
        SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END)
    FROM TransactionEntity t
    WHERE YEAR(t.transactionDate) = :year
      AND MONTH(t.transactionDate) = :month
""")
    Object[] getMonthlyIncomeAndExpense(
            @Param("year") int year,
            @Param("month") int month
    );

}
