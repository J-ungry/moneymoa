package com.example.toygry.moneymoa.Friends.dto;

import java.util.List;

public record User(
        String id,
        String username,
        String firstName,
        String lastName,
        String email,
        boolean emailVerified,
        long createdTimestamp,
        boolean enabled,
        boolean totp,
        List<String> disableableCredentialTypes,  // 빈 배열이므로 List<String>
        List<String> requiredActions,            // 마찬가지로 빈 배열이므로 List<String>
        int notBefore,
        Access access
) {
    public record Access(
            boolean manageGroupMembership,
            boolean view,
            boolean mapRoles,
            boolean impersonate,
            boolean manage
    ) {}
}
