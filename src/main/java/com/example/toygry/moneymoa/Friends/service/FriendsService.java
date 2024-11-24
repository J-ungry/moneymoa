package com.example.toygry.moneymoa.Friends.service;

import com.example.toygry.moneymoa.Friends.dto.RequestFriendResponse;
import com.example.toygry.moneymoa.Friends.dto.User;
import com.example.toygry.moneymoa.Friends.dto.UserListDto;
import com.example.toygry.moneymoa.Friends.entity.Friends;
import com.example.toygry.moneymoa.Friends.exception.FriendDuplicateException;
import com.example.toygry.moneymoa.Friends.exception.FriendFailedException;
import com.example.toygry.moneymoa.Friends.repository.FriendsRepository;
import com.example.toygry.moneymoa.utils.KeycloakToken;
import com.example.toygry.moneymoa.utils.KeycloakUserService;
import com.example.toygry.moneymoa.utils.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FriendsService {

    private final KeycloakUserService keycloakUserService;
    private final TokenUtils tokenUtils;
    private final FriendsRepository friendsRepository;

    public List<UserListDto> getUserList(String token, String id) throws Exception {

        List<UserListDto> userListResponses = new ArrayList<>();
        KeycloakToken keycloakToken = tokenUtils.tokenParser(token);

        /*
        1) keycloak 에서 user 정보 가져오기 (이거 보기 좋은 형태로 바꿔야함)
        2) 가져온 user 정보의 id 가 입력받은 id 로 시작하는거 다 가져오기
        3) 자기 자신의 id 제거하기
        4) friend 테이블에서 자기가 친구 걸었거나 상대방이 걸었더나 중에 있는지 검색하기
        5) status 값 넣어서
         */
        List<User> userList =  keycloakUserService.getUserList(token);
        List<User> findUserList = userList.stream()
                .filter(user -> user.username().contains(id))
                .toList();

        // 자기 자신의 ID 제외하기
        List<User> withoutSelfList = findUserList.stream()
                .filter(user -> !user.username().equals(keycloakToken.userId()))
                .toList();

        // 전체 친구 list 에서 나랑 친구인 사람 찾기
        List<Friends> allFriendsList = friendsRepository.findAll().stream()
                .filter(friends -> friends.getRequestId().equals(keycloakToken.userId()) || friends.getReceiverID().equals(keycloakToken.userId()))
                .toList();

        for (User user : withoutSelfList) {
            String status = "NONE";
            for (Friends friends : allFriendsList) {
                // 해당 유저가 나와 친구 상태인지를 check
                if (friends.getRequestId().equals(user.username()) || friends.getReceiverID().equals(user.username())) {
                    status = friends.getStatus();
                    break;
                }
            }
            UserListDto userListResponse = new UserListDto(
                    user.id(),
                    user.username(),
                    status
            );
            userListResponses.add(userListResponse);
        }
        return userListResponses;
    }

    // 친구 신청 보내기 기능
    public String requestFriend(RequestFriendResponse dto) {
        // 이미 친구인지 확인하기 친구라면 에러 뱉기
        switch (dto.status()) {
            case "ACCEPT" -> throw new FriendDuplicateException();
            // 만약 pending 인 경우 (내가 신청한거면 이미 요청을 보냈습니다, 상대가 신청한거면 요청 수락)
            case "PENDING" -> {
                // 내가 신청한 적이 있는 경우
                boolean checkRequestPending = friendsRepository.existsByRequestUuidAndReceiverUuid(dto.requestUUID(), dto.receiverUUID());
                if (checkRequestPending) {
                    return "이미 요청을 전송했습니다.";
                }

                boolean checkReceivePending = friendsRepository.existsByRequestUuidAndReceiverUuid(dto.receiverUUID(), dto.requestUUID());
                if (checkReceivePending) {
                    Friends updateFriend = friendsRepository.findByRequestUuidAndReceiverUuid(dto.receiverUUID(), dto.requestUUID());
                    updateFriend.updateStatus("ACCEPT"); // 승인으로 변경
                    return "친구 승인";
                }
            }
            // none 인 경우 친구 신청 보내기 (pending 상태로 db insert)
            case "NONE" -> {
                Friends insertFriend = Friends.builder()
                        .requestUuid(dto.requestUUID())
                        .requestId(dto.requestUserName())
                        .receiverUuid(dto.receiverUUID())
                        .receiverID(dto.receiverUserName())
                        .status("PENDING")
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now())
                        .build();
                friendsRepository.save(insertFriend);
            }
            // reject 인 경우 거절당한지 3일이 지났으면 다시 친구 신청 아니면 거절당했다고 메세지 보내기
            case "REJECT" -> {
                return "친구 신청을 거절당했습니다";
            }
        }
        throw new FriendFailedException();
    }
}
