package com.example.toygry.moneymoa.dto;

import java.math.BigDecimal;
import java.time.YearMonth;

public record YearTransactionResponse(
        YearMonth month,
        BigDecimal totalDeposit,
        BigDecimal totalWithdrawal
) {
}
