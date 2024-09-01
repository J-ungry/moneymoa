package com.example.toygry.moneymoa.dto;

import com.example.toygry.moneymoa.entity.TransactionType;

import java.math.BigDecimal;

public record CreateTransactionRequest(
        TransactionType type,
        BigDecimal amount,
        String description
) {
}
