package com.example.toygry.moneymoa.controller;

import com.example.toygry.moneymoa.dto.MonthTransactionResponse;
import com.example.toygry.moneymoa.service.MonthTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/month-transaction")
@AllArgsConstructor
public class MonthTransactionController {

    private final MonthTransactionService monthTransactionService;

    @GetMapping
    private ResponseEntity<List<MonthTransactionResponse>> getMonthTransaction(
            @RequestHeader("Authorization") String token,
            @RequestParam("date") String date
    ) {
        return ResponseEntity.ok(monthTransactionService.getMonthTransaction(token, date));
    }
}
