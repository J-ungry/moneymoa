package com.example.toygry.moneymoa.Friends.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "friends")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Friends {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "request_uuid")
    private UUID requestUuid;

    @Column(name = "receiver_uuid")
    private UUID receiverUuid;

    @Column(name = "request_id")
    private String requestId;

    @Column(name = "receiver_id")
    private String receiverID;

    @Enumerated(EnumType.STRING) // Enum을 String으로 저장
    @Column(name = "status")
    private FriendsStatus status;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    public void updateStatus(FriendsStatus status) {
        this.status = status;
        this.modifiedDate = LocalDateTime.now();
    }

}
