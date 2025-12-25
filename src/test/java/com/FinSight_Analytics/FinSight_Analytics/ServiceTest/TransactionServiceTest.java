package com.FinSight_Analytics.FinSight_Analytics.ServiceTest;

import com.FinSight_Analytics.FinSight_Analytics.Enums.TransactionType;
import com.FinSight_Analytics.FinSight_Analytics.Exception.ResourceNotFoundException;
import com.FinSight_Analytics.FinSight_Analytics.Repository.TransactionRepository;
import com.FinSight_Analytics.FinSight_Analytics.Service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;
    @InjectMocks
    private TransactionService transactionService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getMonthlyIncomeVsExpense_success(){
        YearMonth month=YearMonth.of(2025,12);
        Object[] db_result=new Object[]{
                new BigDecimal("45000"),
                new BigDecimal("15000")
        };
        when(transactionRepository.getMonthlyIncomeAndExpense(2025,12)).thenReturn(db_result);
        Map<String, BigDecimal> result=transactionService.getMonthlyIncomeVsExpense(month);
        assertEquals(new BigDecimal("45000"), result.get("INCOME"));
        assertEquals(new BigDecimal("15000"), result.get("EXPENSE"));
        verify(transactionRepository, times(1))
                .getMonthlyIncomeAndExpense(2025, 12);
    }

    @Test
    void getCategoryWiseExpense_success(){
        YearMonth month = YearMonth.of(2025, 12);

        List<Object[]> db_Result = List.of(
                new Object[]{"Food", new BigDecimal("5000")},
                new Object[]{"Rent", new BigDecimal("10000")}
        );
        when(transactionRepository.getCategoryWiseTotal(TransactionType.EXPENSE,2025,12)).thenReturn(db_Result);
        Map<String, BigDecimal> result=transactionService.getCategoryWiseExpense(month);
        assertEquals(2, result.size());
        assertEquals(new BigDecimal("5000"), result.get("Food"));
        assertEquals(new BigDecimal("10000"), result.get("Rent"));
    }
    @Test
    void getMonthlyIncomeVsExpense_noData_throwsException() {

        YearMonth month = YearMonth.of(2025, 12);

        Object[] dbResult = new Object[]{
                BigDecimal.ZERO,
                BigDecimal.ZERO
        };

        when(transactionRepository.getMonthlyIncomeAndExpense(
                2025, 12)
        ).thenReturn(dbResult);

        assertThrows(ResourceNotFoundException.class,
                () -> transactionService.getMonthlyIncomeVsExpense(month)
        );
    }
    @Test
    void shouldCalculateMonthlyBalanceCorrectly() {
        YearMonth month = YearMonth.of(2025, 12);

        Object[] dbResult = new Object[]{
                new BigDecimal("30000"),
                new BigDecimal("10000")
        };

        when(transactionRepository.getMonthlyIncomeAndExpense(2025, 12))
                .thenReturn(dbResult);

        BigDecimal balance = transactionService.getMonthlyBalance(month);

        assertEquals(new BigDecimal("20000"), balance);
    }
}
