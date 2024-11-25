package com.example.toygry.moneymoa.Friends.controller;

import com.example.toygry.moneymoa.Friends.dto.RequestFriendResponse;
import com.example.toygry.moneymoa.Friends.dto.UserListDto;
import com.example.toygry.moneymoa.Friends.service.FriendsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/friends")
@AllArgsConstructor
public class FriendsController {

    private final FriendsService friendsService;

    // 1) 검색을 통한 사용자 조회 기능
    @GetMapping("/users")
    public ResponseEntity<List<UserListDto>> getUserList(
            @RequestHeader("Authorization") String token,
            @RequestParam("userId") String id
    ) throws Exception {
        return ResponseEntity.ok(friendsService.getUserList(token, id));
    }
    // 2) 친구 신청 보내기 기능
    @PostMapping("/request")
    public ResponseEntity<String> requestFriend(
            @RequestBody RequestFriendResponse requestFriendResponse
    ) {
        return ResponseEntity.ok(friendsService.requestFriend(requestFriendResponse));
    }
    // 3) 친구 신청 수락 기능 (Pending -> accept)

    @PostMapping("/accept")
    public ResponseEntity<String> acceptFriend(
            @RequestHeader("Authorization") String token,
            @RequestParam RequestFriendResponse requestFriendResponse
    ) {
        return ResponseEntity.ok(friendsService.acceptFriend(token, requestFriendResponse));
    }
    // 3.5) 친구 신청 거절 기능 (그냥 row 날려버리자)

//    @PostMapping("/reject")
//    public ResponseEntity<String> rejectFriend(
//            @RequestHeader("Authorization") String token,
//            @RequestParam RequestFriendResponse requestFriendResponse
//    ) {
//        return ResponseEntity.ok(friendsService.rejectFriend(token, requestFriendResponse));
//    }
    // 4) 친구 목록 불러오기 기능
    // 5) 친구 삭제 기능
    // 6) 친구 상태 (status 수정 기능)

    // friendsTransaction 에 친구꺼 조회하거나 이런 기능 추가하기
}
