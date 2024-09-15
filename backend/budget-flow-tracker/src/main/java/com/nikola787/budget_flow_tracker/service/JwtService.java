package com.nikola787.budget_flow_tracker.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

public interface JwtService {

    public String generateToken(UserDetails userDetails);
    public String generateToken(HashMap<String,Object> claims, UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    String extractUsername(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimResolver);

    Date extractExpiration(String token);
}
