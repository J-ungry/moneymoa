package com.example.toygry.moneymoa.Transaction.service;

import com.example.toygry.moneymoa.Transaction.dto.YearTransactionResponse;
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
public class YearTransactionService {

    private final TransactionRepository transactionRepository;
    private final TokenUtils tokenUtils;

    public List<YearTransactionResponse> getYearTransaction(String token, int year) {
        KeycloakToken keycloakToken = tokenUtils.tokenParser(token);
        String userId = keycloakToken.userId();

        LocalDateTime startDateTime = LocalDate.of(year, 1, 1).atStartOfDay();
        LocalDateTime endDateTime = LocalDate.of(year + 1, 1, 1).atStartOfDay();

        List<Transactions> transactions = transactionRepository.findTransactionsBetweenDate(userId, startDateTime, endDateTime).orElse(new ArrayList<>());

        Map<YearMonth, List<Transactions>> groupTransaction = transactions.stream()
                .collect(Collectors.groupingBy(t -> YearMonth.from(t.getCreatedDate())));

        List<YearTransactionResponse> yearTransactionResponses = new ArrayList<>();

        for (Map.Entry<YearMonth, List<Transactions>> entry : groupTransaction.entrySet()) {
            YearMonth yearMonth = entry.getKey();
            List<Transactions> transactionsList = entry.getValue();

            BigDecimal totalDeposit = transactionsList.stream()
                    .filter(t -> Objects.equals(t.getType(), TransactionType.DEPOSIT.name()))
                    .map(Transactions::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalWithdrawal = transactionsList.stream()
                    .filter(t -> Objects.equals(t.getType(), TransactionType.WITHDRAWAL.name()))
                    .map(Transactions::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            yearTransactionResponses.add(new YearTransactionResponse(yearMonth, totalDeposit, totalWithdrawal));
        }

        return yearTransactionResponses;
    }
}
