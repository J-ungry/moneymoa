package com.example.toygry.moneymoa.Transaction.controller;

import com.example.toygry.moneymoa.Transaction.dto.CreateTransactionRequest;
import com.example.toygry.moneymoa.Transaction.dto.DayTransactionResponse;
import com.example.toygry.moneymoa.Transaction.dto.TransactionsResponse;
import com.example.toygry.moneymoa.Transaction.service.DailyTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/day-transaction")
@AllArgsConstructor
public class DailyTransactionController {

    private final DailyTransactionService dayTransactionService;

    @GetMapping
    public ResponseEntity<List<TransactionsResponse>> getAllTransactions() {
        return ResponseEntity.ok(dayTransactionService.getAllTransactions());
    }

    // 하루치 transaction 기록 가져오기
    @GetMapping("/day")
    public ResponseEntity<DayTransactionResponse> getDayTransactions(
            @RequestHeader("Authorization") String token,
            @RequestParam("date") String date) {
        return ResponseEntity.ok(dayTransactionService.getDayTransactions(token, date));
    }

    // transaction 추가
    @PostMapping
    public ResponseEntity<TransactionsResponse> createDayTransaction(
            @RequestHeader("Authorization") String token,
            @RequestBody CreateTransactionRequest createTransactionRequest
    ) {
        return ResponseEntity.ok(dayTransactionService.createDayTransaction(token, createTransactionRequest));
    }

    // transaction 삭제
    @DeleteMapping
    public ResponseEntity<String> deleteDayTransaction(
            @RequestHeader("Authorization") String token,
            @RequestParam("id") String id
    ) {
        return ResponseEntity.ok(dayTransactionService.deleteDayTransaction(token, id));
    }

    // transaction 수정
}
