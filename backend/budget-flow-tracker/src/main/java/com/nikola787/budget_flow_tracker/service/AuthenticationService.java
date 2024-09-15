package com.nikola787.budget_flow_tracker.service;

import com.nikola787.budget_flow_tracker.dto.authentication.*;
import com.nikola787.budget_flow_tracker.exception.authentication.TokenHasExpiredException;
import com.nikola787.budget_flow_tracker.exception.authentication.TokenNotValidException;
import jakarta.mail.MessagingException;

public interface AuthenticationService {

    void register(RegisterDto request) throws MessagingException;

    AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto);

    void activateAccount(String token) throws MessagingException, TokenNotValidException, TokenHasExpiredException;
}
