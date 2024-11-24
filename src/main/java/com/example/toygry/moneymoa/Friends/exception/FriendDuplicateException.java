package com.example.toygry.moneymoa.Friends.exception;

import com.example.toygry.moneymoa.Common.Exception.BaseException;
import org.springframework.http.HttpStatus;

public class FriendDuplicateException extends BaseException {
    public FriendDuplicateException() {
        super(HttpStatus.CONFLICT, "Friend already exists");
    }
}
