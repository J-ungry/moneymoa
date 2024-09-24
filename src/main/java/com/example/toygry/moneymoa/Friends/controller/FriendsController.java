package com.example.toygry.moneymoa.Friends.controller;

import com.example.toygry.moneymoa.Friends.service.FriendsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/friends")
@AllArgsConstructor
public class FriendsController {

    private final FriendsService friendsService;

    // 1) 사용자 조회 기능

    @GetMapping("/users")
    public ResponseEntity<String> getUserList(
            @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(friendsService.getUserList(token));
    }
    // 2) 친구 신청 보내기 기능
    // 3) 친구 신청 수락 기능
    // 4) 친구 목록 불러오기 기능
    // 5) 친구 삭제 기능
    // 6) 친구 상태 (status 수정 기능)

    // friendsTransaction 에 친구꺼 조회하거나 이런 기능 추가하기
}
