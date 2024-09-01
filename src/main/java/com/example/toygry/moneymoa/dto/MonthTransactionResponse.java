package com.example.toygry.moneymoa.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MonthTransactionResponse(
        LocalDate date,
        BigDecimal totalDeposit,
        BigDecimal totalWithdrawal
) {
}
