package com.example.toygry.moneymoa.Common.Exception;


import org.springframework.http.HttpStatus;

public record ErrorResponse(HttpStatus status, String message) {

    public ErrorResponse(BaseException e) {
        this(e.getStatus(), e.getMessage());
    }
}
