package com.example.toygry.moneymoa.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionsResponse(
        UUID id,
        String userId,
        String type,
        BigDecimal amount,
        String description,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate
) {}
