package com.example.toygry.moneymoa.Transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MonthTransactionResponse(
        LocalDate date,
        BigDecimal totalDeposit,
        BigDecimal totalWithdrawal
) {
}
