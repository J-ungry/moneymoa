package com.example.toygry.moneymoa.Friends.exception;

import com.example.toygry.moneymoa.Common.Exception.BaseException;
import org.springframework.http.HttpStatus;

public class FriendFailedException extends BaseException {
    public FriendFailedException() {
        super(HttpStatus.BAD_REQUEST, "Friend Failed");
    }
}
