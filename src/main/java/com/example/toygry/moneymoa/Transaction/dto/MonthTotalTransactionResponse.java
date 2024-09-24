package com.example.toygry.moneymoa.Transaction.dto;

import java.math.BigDecimal;

public record MonthTotalTransactionResponse(
        int year,
        int month,
        BigDecimal totalDeposit,
        BigDecimal totalWithdrawal,
        BigDecimal totalAmount
) {
}
