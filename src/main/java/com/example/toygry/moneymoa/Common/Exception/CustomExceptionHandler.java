package com.example.toygry.moneymoa.Common.Exception;

import com.example.toygry.moneymoa.Common.Exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> baseHandler(BaseException e) {
        return ResponseEntity.status(e.getStatus()).body(this.makeErrorResponse(e));
    }

    private ErrorResponse makeErrorResponse(BaseException e) {
        return new ErrorResponse(e);
    }
}
