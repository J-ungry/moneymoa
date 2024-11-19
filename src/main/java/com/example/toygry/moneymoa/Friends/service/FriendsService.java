package com.example.toygry.moneymoa.Friends.service;

import com.example.toygry.moneymoa.utils.KeycloakUserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FriendsService {

    private final KeycloakUserService keycloakUserService;

    public String getUserList(String token) {
        return keycloakUserService.getUserList(token);
    }
}
