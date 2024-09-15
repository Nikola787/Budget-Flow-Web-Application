package com.nikola787.budget_flow_tracker.service.impl;

import com.nikola787.budget_flow_tracker.dto.authentication.*;
import com.nikola787.budget_flow_tracker.exception.ModelInvalidException;
import com.nikola787.budget_flow_tracker.exception.RecordNotValidException;
import com.nikola787.budget_flow_tracker.exception.authentication.TokenHasExpiredException;
import com.nikola787.budget_flow_tracker.exception.authentication.TokenNotValidException;
import com.nikola787.budget_flow_tracker.model.authentication.EmailTemplateName;
import com.nikola787.budget_flow_tracker.model.authentication.Token;
import com.nikola787.budget_flow_tracker.model.authentication.UserProfile;
import com.nikola787.budget_flow_tracker.model.authentication.UserRole;
import com.nikola787.budget_flow_tracker.repository.TokenRepository;
import com.nikola787.budget_flow_tracker.repository.UserProfileRepository;
import com.nikola787.budget_flow_tracker.repository.UserRoleRepository;
import com.nikola787.budget_flow_tracker.service.AuthenticationService;
import com.nikola787.budget_flow_tracker.service.EmailService;
import com.nikola787.budget_flow_tracker.service.JwtService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserProfileRepository userProfileRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Value("${activation.url}")
    private String activationUrl;

    @Override
    public void register(RegisterDto request) {
        var userRole = userRoleRepository.findByName("USER")
                .orElseThrow(() -> new ModelInvalidException(UserRole.class, "Role USER was not initialized."));
        Optional<UserProfile> existingUser = userProfileRepository.findByUsername(request.username());
        if (existingUser.isPresent()) throw new RecordNotValidException("Username '" + request.username() + "' is already taken.");
        Optional<UserProfile> existingUserByEmail = userProfileRepository.findByEmail(request.email());
        if (existingUserByEmail.isPresent()) {
            throw new RecordNotValidException("Email '" + request.email() + "' is already in use.");
        }
        var user = UserProfile
                .builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .firstName(request.firstname())
                .lastName(request.lastname())
                .email(request.email())
                .balance(request.balance())
                .currency(request.currency())
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userProfileRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(UserProfile userProfile) {
        var newToken = generateAndSaveActivationToken(userProfile);
        try {
            emailService.sendEmail(
                    userProfile.getEmail(),
                    userProfile.getFirstName(),
                    EmailTemplateName.ACTIVATE_ACCOUNT,
                    activationUrl,
                    newToken,
                    "Account Activation"
            );
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateAndSaveActivationToken(UserProfile userProfile) {
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .userProfile(userProfile)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i =0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    @Override
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequestDto.username(),
                        authenticationRequestDto.password()
                )
        );
        var claims = new HashMap<String, Object>();
        var userProfile = ( (UserProfile) auth.getPrincipal());
        claims.put("fullName", userProfile.fullName());
        var jwtToken = jwtService.generateToken(claims, userProfile);
        return AuthenticationResponseDto
                .builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public void activateAccount(String token) {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotValidException(token));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUserProfile());
            throw new TokenHasExpiredException(token);
        }
        var userProfile = userProfileRepository.findById(savedToken.getUserProfile().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        userProfile.setEnabled(true);
        userProfileRepository.save(userProfile);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }
}
