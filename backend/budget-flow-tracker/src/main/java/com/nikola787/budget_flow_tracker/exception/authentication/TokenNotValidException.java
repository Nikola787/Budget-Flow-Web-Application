package com.nikola787.budget_flow_tracker.exception.authentication;

import com.nikola787.budget_flow_tracker.exception.RequestNotValidException;

public class TokenNotValidException extends RequestNotValidException {

    public TokenNotValidException() {}

    public TokenNotValidException(String token){
        super(String.format("Received token %s is not valid", token));
    }
}
