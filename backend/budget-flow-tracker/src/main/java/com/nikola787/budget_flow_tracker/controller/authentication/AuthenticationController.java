package com.nikola787.budget_flow_tracker.controller.authentication;

import com.nikola787.budget_flow_tracker.dto.authentication.AuthenticationRequestDto;
import com.nikola787.budget_flow_tracker.dto.authentication.AuthenticationResponseDto;
import com.nikola787.budget_flow_tracker.dto.authentication.RegisterDto;
import com.nikola787.budget_flow_tracker.service.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
            @RequestBody @Valid RegisterDto registerDto
    ) throws MessagingException {
        authenticationService.register(registerDto);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<AuthenticationResponseDto> authenticate (
            @RequestBody @Valid AuthenticationRequestDto authenticationRequestDto
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequestDto));
    }

    @GetMapping("/activate-account")
    public void confirm(
            @RequestParam String token
    ) throws MessagingException {
        authenticationService.activateAccount(token);
    }
}
