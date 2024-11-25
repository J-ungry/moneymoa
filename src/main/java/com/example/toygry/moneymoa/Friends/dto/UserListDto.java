package com.example.toygry.moneymoa.Friends.dto;

import com.example.toygry.moneymoa.Friends.entity.FriendsStatus;

public record UserListDto(String UUID, String userId, FriendsStatus status) {
}
