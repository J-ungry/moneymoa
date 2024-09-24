package com.example.toygry.moneymoa.Transaction.dto;

import com.example.toygry.moneymoa.Transaction.entity.TransactionType;

import java.math.BigDecimal;

public record CreateTransactionRequest(
        TransactionType type,
        BigDecimal amount,
        String description
) {
}
