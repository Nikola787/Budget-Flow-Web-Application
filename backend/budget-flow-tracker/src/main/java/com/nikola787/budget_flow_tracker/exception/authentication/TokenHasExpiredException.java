package com.nikola787.budget_flow_tracker.exception.authentication;

import com.nikola787.budget_flow_tracker.exception.RequestNotValidException;

public class TokenHasExpiredException extends RequestNotValidException {

    public TokenHasExpiredException() {}

    public TokenHasExpiredException(String token) {
        super(String.format("Received token %s is not valid. A new token has been sent to the same email address.", token));
    }
}
