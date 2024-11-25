package com.example.toygry.moneymoa.Friends.dto;

import com.example.toygry.moneymoa.Friends.entity.FriendsStatus;

import java.util.UUID;

public record RequestFriendResponse(
        FriendsStatus status,
        UUID requestUUID, // 신청 보낸 사람
        String requestUserName,
        UUID receiverUUID, // 받은 사람
        String receiverUserName,
        String message
) {
}
