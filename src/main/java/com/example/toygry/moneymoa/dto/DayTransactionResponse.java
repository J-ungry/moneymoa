package com.example.toygry.moneymoa.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public record DayTransactionResponse (
    LocalDate date,
    BigDecimal totalIncrease,
    BigDecimal totalDecrease,
    List<TransactionsResponse> transactions) {
}
