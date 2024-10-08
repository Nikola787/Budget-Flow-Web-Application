package com.nikola787.budget_flow_tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RecordNotCurrentVersionException extends RuntimeException {
    public RecordNotCurrentVersionException(){
        super("Version of resource is not up to date.");
    }
    public RecordNotCurrentVersionException(String message) {
        super(message);
    }
}
