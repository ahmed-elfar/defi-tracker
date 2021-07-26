package com.xyvo.defi.server;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Invalid Request.")
public class ServerException extends RuntimeException {

    public ServerException() {
        super();
    }

    public ServerException(String msg) {
        super(msg);
    }

}
