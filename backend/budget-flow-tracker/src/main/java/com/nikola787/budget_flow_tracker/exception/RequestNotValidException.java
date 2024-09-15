package com.nikola787.budget_flow_tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestNotValidException extends RuntimeException {
    public RequestNotValidException() {
        super("Request is not valid.");
    }
    public RequestNotValidException(String message) {
        super(message);
    }
}
