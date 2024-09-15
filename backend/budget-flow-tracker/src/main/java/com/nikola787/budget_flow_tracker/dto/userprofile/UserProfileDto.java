package com.nikola787.budget_flow_tracker.dto.userprofile;

import com.nikola787.budget_flow_tracker.model.authentication.Currency;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record UserProfileDto (
    String firstname,
    String lastname,
    Long balance,
    Currency currency
) { }
