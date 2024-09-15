package com.nikola787.budget_flow_tracker.dto.authentication;

import com.nikola787.budget_flow_tracker.model.authentication.Currency;
import jakarta.validation.constraints.*;

public record RegisterDto(

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z]{3,30}$", message = "Username must contain only letters and be between 3 and 30 characters long")
        String username,
        @NotBlank
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
                message = "Password must have minimum 8 characters, at least one uppercase letter, one lowercase letter and one number."
        )
        String password,

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z]{3,30}$", message = "First name must contain only letters and be between 3 and 30 characters long")
        String firstname,

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z]{3,30}$", message = "Last name must contain only letters and be between 3 and 30 characters long")
        String lastname,

        @NotBlank
        @Email(message = "Email should be valid")
        String email,

        @Positive(message = "Balance must be a positive value")
        Long balance,

        @NotNull
        Currency currency
) {
}
