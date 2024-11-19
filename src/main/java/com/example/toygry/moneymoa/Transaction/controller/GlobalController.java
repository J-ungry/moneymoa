package com.example.toygry.moneymoa.Transaction.controller;

import com.example.toygry.moneymoa.Transaction.dto.MonthTotalTransactionResponse;
import com.example.toygry.moneymoa.Transaction.service.GlobalService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/global")
@AllArgsConstructor
public class GlobalController {

    private final GlobalService globalService;

    // 한달치 transaction 기록 가져오기
    @GetMapping("month-total")
    public MonthTotalTransactionResponse getMonthTotalTransactions(
            @RequestHeader("Authorization") String token,
            @RequestParam("date") String date) {
        return globalService.getMonthTotalTransactions(token, date);
    }
}
