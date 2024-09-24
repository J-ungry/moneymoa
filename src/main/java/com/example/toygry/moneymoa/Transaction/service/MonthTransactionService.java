package com.example.toygry.moneymoa.Transaction.service;

import com.example.toygry.moneymoa.Transaction.dto.MonthTransactionResponse;
import com.example.toygry.moneymoa.Transaction.entity.TransactionType;
import com.example.toygry.moneymoa.Transaction.entity.Transactions;
import com.example.toygry.moneymoa.Transaction.repository.TransactionRepository;
import com.example.toygry.moneymoa.utils.KeycloakToken;
import com.example.toygry.moneymoa.utils.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MonthTransactionService {

    private final TransactionRepository transactionRepository;
    private final TokenUtils tokenUtils;

    public List<MonthTransactionResponse> getMonthTransaction(String token, String date) {
        KeycloakToken keycloakToken = tokenUtils.tokenParser(token);
        String userId = keycloakToken.userId();

        YearMonth yearMonth = YearMonth.parse(date);
        // 월 1일과 다음달 1일을 구함 (범위설정)
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.plusMonths(1).atDay(1);

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atStartOfDay();

        List<Transactions> transactions = transactionRepository.findTransactionsBetweenDate(userId, startDateTime, endDateTime)
                .orElse(new ArrayList<>());

        Map<LocalDate, List<Transactions>> groupTransaction = transactions.stream()
                .collect(Collectors.groupingBy(t -> t.getCreatedDate().toLocalDate()));

        List<MonthTransactionResponse> monthTransactionResponses = new ArrayList<>();

        for (Map.Entry<LocalDate, List<Transactions>> entry : groupTransaction.entrySet()) {
            LocalDate transactionDate = entry.getKey();
            List<Transactions> transactionsForMonth = entry.getValue();

            BigDecimal totalDeposit = transactionsForMonth.stream()
                    .filter(t -> Objects.equals(t.getType(), TransactionType.DEPOSIT.name()))
                    .map(Transactions::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalWithdrawal = transactionsForMonth.stream()
                    .filter(t -> Objects.equals(t.getType(), TransactionType.WITHDRAWAL.name()))
                    .map(Transactions::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            monthTransactionResponses.add(new MonthTransactionResponse(transactionDate, totalDeposit, totalWithdrawal));
        }

        return monthTransactionResponses;
    }
}
