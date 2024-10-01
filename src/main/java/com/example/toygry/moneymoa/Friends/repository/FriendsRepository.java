package com.example.toygry.moneymoa.Friends.repository;

import com.example.toygry.moneymoa.Friends.entity.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FriendsRepository extends JpaRepository<Friends, UUID> {}
