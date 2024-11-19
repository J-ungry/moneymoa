package com.example.toygry.moneymoa.Transaction.mapper;

import com.example.toygry.moneymoa.Transaction.dto.TransactionsResponse;
import com.example.toygry.moneymoa.Transaction.entity.Transactions;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TransactionsMapper {

    // Entity -> DTO
    public TransactionsResponse toDto(Transactions transactions) {
        if (Objects.isNull(transactions)) {
            return null;
        }

        return new TransactionsResponse(
                transactions.getId(),
                transactions.getUserId(),
                transactions.getType(),
                transactions.getAmount(),
                transactions.getDescription(),
                transactions.getCreatedDate(),
                transactions.getModifiedDate()
        );
    }

    // DTO -> Entity
    public Transactions toEntity(TransactionsResponse transactionsResponse) {
        if (Objects.isNull(transactionsResponse)) {
            return null;
        }

        return Transactions.builder()
                .id(transactionsResponse.id())
                .userId(transactionsResponse.userId())
                .type(transactionsResponse.type())
                .amount(transactionsResponse.amount())
                .description(transactionsResponse.description())
                .createdDate(transactionsResponse.createdDate())
                .modifiedDate(transactionsResponse.modifiedDate())
                .build();
    }
}
