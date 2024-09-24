package com.example.toygry.moneymoa.Transaction.service;

import com.example.toygry.moneymoa.Transaction.dto.MonthTotalTransactionResponse;
import com.example.toygry.moneymoa.Transaction.repository.TransactionRepository;
import com.example.toygry.moneymoa.utils.KeycloakToken;
import com.example.toygry.moneymoa.utils.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class GlobalService {

    private final TransactionRepository transactionRepository;
    private final TokenUtils tokenUtils;

    public MonthTotalTransactionResponse getMonthTotalTransactions(String token, String date) {
        KeycloakToken keycloakToken = tokenUtils.tokenParser(token);
        String userId = keycloakToken.userId();

        YearMonth yearMonth = YearMonth.parse(date, DateTimeFormatter.ofPattern("yyyy-MM"));
        int year = yearMonth.getYear();
        int month = yearMonth.getMonthValue();

        BigDecimal totalDeposit = transactionRepository.getTotalDepositForMonthAndUser(userId, year, month);
        BigDecimal totalWithdrawal = transactionRepository.getTotalWithdrawalForMonthAndUser(userId, year, month);
        BigDecimal totalAmount = totalDeposit.subtract(totalWithdrawal);
        return new MonthTotalTransactionResponse(year, month, totalDeposit, totalWithdrawal,totalAmount);
    }
}
