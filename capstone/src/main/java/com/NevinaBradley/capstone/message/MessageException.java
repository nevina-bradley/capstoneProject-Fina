package com.NevinaBradley.capstone.message;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MessageException extends Exception {
    public MessageException(String message) {
        super(message);
    }
}
