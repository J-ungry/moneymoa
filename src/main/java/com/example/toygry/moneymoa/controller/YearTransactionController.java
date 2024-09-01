package com.example.toygry.moneymoa.controller;

import com.example.toygry.moneymoa.dto.YearTransactionResponse;
import com.example.toygry.moneymoa.service.YearTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/year-transaction")
@AllArgsConstructor
public class YearTransactionController {

    private final YearTransactionService yearTransactionService;

    @GetMapping
    private ResponseEntity<List<YearTransactionResponse>> getYearTransaction(
            @RequestHeader("Authorization") String token,
            @RequestParam("date") int year
    ) {
        return ResponseEntity.ok(yearTransactionService.getYearTransaction(token, year));
    }
}
