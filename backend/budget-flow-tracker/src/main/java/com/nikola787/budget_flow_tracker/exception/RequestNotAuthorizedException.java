package com.nikola787.budget_flow_tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class RequestNotAuthorizedException extends RuntimeException {
    public RequestNotAuthorizedException() {
        super("You are not authorized for requested action.");
    }
    public RequestNotAuthorizedException(String message) {
        super(message);
    }
}
