package com.nikola787.budget_flow_tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class RecordNotValidException extends RuntimeException {
    public RecordNotValidException() {
        super("The sent record in request is not valid.");
    }

    public RecordNotValidException(String message) {
        super(message);
    }
}
