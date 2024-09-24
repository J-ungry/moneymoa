package com.example.toygry.moneymoa.Transaction.dto;

import java.math.BigDecimal;
import java.time.YearMonth;

public record YearTransactionResponse(
        YearMonth month,
        BigDecimal totalDeposit,
        BigDecimal totalWithdrawal
) {
}
