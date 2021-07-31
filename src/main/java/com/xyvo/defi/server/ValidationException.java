package com.xyvo.defi.server;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.UNPROCESSABLE_ENTITY, reason="Invalid Request.")
public class ValidationException extends RuntimeException {

    public ValidationException() {
        super();
    }

    public ValidationException(String msg) {
        super(msg);
    }

}