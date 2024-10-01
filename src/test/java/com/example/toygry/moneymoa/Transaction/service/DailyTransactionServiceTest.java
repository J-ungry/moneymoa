package com.example.toygry.moneymoa.Transaction.service;

import com.example.toygry.moneymoa.Transaction.dto.TransactionsResponse;
import com.example.toygry.moneymoa.Transaction.entity.TransactionType;
import com.example.toygry.moneymoa.Transaction.entity.Transactions;
import com.example.toygry.moneymoa.Transaction.mapper.TransactionsMapper;
import com.example.toygry.moneymoa.Transaction.repository.TransactionRepository;
import com.example.toygry.moneymoa.utils.TokenUtils;
import jakarta.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DailyTransactionServiceTest {

    @Mock
    private TokenUtils tokenUtils;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionsMapper transactionsMapper;

    @InjectMocks // 실제 테스트 할 객체를 생성하고 Mock 객체를 주입한다
    private DailyTransactionService dailyTransactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // 목 객체 초기화
    }

    @Test
    void getAllTransactions() {
        // Given
        UUID transactionId = UUID.randomUUID();
        LocalDateTime now = now();
        List<Transactions> transactionsList = new ArrayList<>();
        Transactions transaction = Transactions.builder()
                .id(transactionId)
                .userId("testUser")
                .type("DEPOSIT")
                .amount(BigDecimal.valueOf(1000))
                .description("Test deposit")
                .createdDate(now)
                .modifiedDate(now)
                .build();
        transactionsList.add(transaction);

        // 실제 Repository 랑 상호작용 하지 않고 서비스 로직이 정상적으로 동작하는지 Test 하는거임 !!!
        when(transactionRepository.findAll()).thenReturn(transactionsList);
        when(transactionsMapper.toDto(any(Transactions.class))).thenReturn(new TransactionsResponse(
                transactionId,
                "testUser",
                "DEPOSIT",
                BigDecimal.valueOf(1000),
                "Test deposit",
                now,
                now));
        // When

        List<TransactionsResponse> result = dailyTransactionService.getAllTransactions();
        // Then

        assertEquals(1, result.size());
        assertEquals(transactionId, result.getFirst().id());
        assertEquals("testUser", result.getFirst().userId());
        assertEquals("DEPOSIT", result.getFirst().type());
        assertEquals(BigDecimal.valueOf(1000), result.getFirst().amount());
        assertEquals("Test deposit", result.getFirst().description());

        verify(transactionRepository, times(1)).findAll();
        verify(transactionsMapper, times(1)).toDto(any(Transactions.class));
    }

    @Test
    void getDayTransactions() {
    }

    @Test
    void createDayTransaction() {
    }

    @Test
    void deleteDayTransaction() {
    }
}