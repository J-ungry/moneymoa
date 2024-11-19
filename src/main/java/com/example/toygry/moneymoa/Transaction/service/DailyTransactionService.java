package com.example.toygry.moneymoa.Transaction.service;

import com.example.toygry.moneymoa.Transaction.dto.CreateTransactionRequest;
import com.example.toygry.moneymoa.Transaction.dto.DayTransactionResponse;
import com.example.toygry.moneymoa.Transaction.dto.TransactionsResponse;
import com.example.toygry.moneymoa.Transaction.entity.TransactionType;
import com.example.toygry.moneymoa.Transaction.entity.Transactions;
import com.example.toygry.moneymoa.Transaction.mapper.TransactionsMapper;
import com.example.toygry.moneymoa.Transaction.repository.TransactionRepository;
import com.example.toygry.moneymoa.utils.KeycloakToken;
import com.example.toygry.moneymoa.utils.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DailyTransactionService {

    private final TokenUtils tokenUtils;
    private final TransactionRepository transactionRepository;
    private final TransactionsMapper transactionsMapper;

    public List<TransactionsResponse> getAllTransactions() {
        List<Transactions> transactions = transactionRepository.findAll();
        List<TransactionsResponse> result = new ArrayList<>();
        for (Transactions t : transactions) {
            result.add(transactionsMapper.toDto(t));
        }
        return result;
    }

    public DayTransactionResponse getDayTransactions(String token, String date) {
        KeycloakToken keycloakToken = tokenUtils.tokenParser(token);
        LocalDate localDate = LocalDate.parse(date);

        String userId = keycloakToken.userId();

        List<Transactions> transactions = transactionRepository.findByUserIdAndCreatedDate(userId,localDate)
                .orElse(new ArrayList<>());

        BigDecimal totalDeposit = BigDecimal.ZERO;
        BigDecimal totalWithdrawal = BigDecimal.ZERO;

        List<TransactionsResponse> transactionsResponses = new ArrayList<>();

        for (Transactions t : transactions) {
            if (TransactionType.DEPOSIT.name().equalsIgnoreCase(t.getType())) {
                totalDeposit = totalDeposit.add(t.getAmount());
            } else if (TransactionType.WITHDRAWAL.name().equalsIgnoreCase(t.getType())) {
                totalWithdrawal = totalWithdrawal.add(t.getAmount());
            }
            transactionsResponses.add(transactionsMapper.toDto(t));
        }

        return new DayTransactionResponse(
                localDate,
                totalDeposit,
                totalWithdrawal,
                transactionsResponses
        );
    }

    public TransactionsResponse createDayTransaction(String token, CreateTransactionRequest createTransactionRequest) {
        KeycloakToken keycloakToken = tokenUtils.tokenParser(token);
        String userId = keycloakToken.userId();

        Transactions transactions = Transactions.builder()
                .userId(userId)
                .type(createTransactionRequest.type().name())
                .amount(createTransactionRequest.amount())
                .description(createTransactionRequest.description())
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();

        Transactions result = transactionRepository.save(transactions);
        return transactionsMapper.toDto(result);
    }

    public String deleteDayTransaction(String token, String id) {
        KeycloakToken keycloakToken = tokenUtils.tokenParser(token);
        String userId = keycloakToken.userId();
        UUID transactionId = UUID.fromString(id);

        Transactions transactionsOptional = transactionRepository.findById(transactionId).orElseThrow(() -> new RuntimeException("존재하지 않는 id 입니다"));
        if (transactionsOptional.getUserId().equalsIgnoreCase(userId)) {
            transactionRepository.deleteById(transactionId);
            return "정상적으로 삭제되었습니다";
        } else {
            throw new RuntimeException("삭제권한이 없는 유저입니다");
        }
    }
}
