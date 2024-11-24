package com.example.toygry.moneymoa.Transaction.controller;

import com.example.toygry.moneymoa.Transaction.dto.YearTransactionResponse;
import com.example.toygry.moneymoa.Transaction.service.YearTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/year-transaction")
@AllArgsConstructor
public class YearTransactionController {

    private final YearTransactionService yearTransactionService;

    // 1년치 트랜잭션
    @GetMapping
    private ResponseEntity<List<YearTransactionResponse>> getYearTransaction(
            @RequestHeader("Authorization") String token,
            @RequestParam("date") int year
    ) {
        return ResponseEntity.ok(yearTransactionService.getYearTransaction(token, year));
    }
}
