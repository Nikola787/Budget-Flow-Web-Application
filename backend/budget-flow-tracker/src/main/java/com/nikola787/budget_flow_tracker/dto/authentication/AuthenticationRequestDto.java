package com.nikola787.budget_flow_tracker.dto.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record AuthenticationRequestDto(

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z]{3,30}$", message = "Username must contain only letters and be between 3 and 30 characters long")
        String username,
        @NotBlank
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
                message = "Password is not in valid format."
        )
        String password
) {
}
