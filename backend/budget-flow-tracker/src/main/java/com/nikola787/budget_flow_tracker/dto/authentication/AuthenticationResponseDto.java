package com.nikola787.budget_flow_tracker.dto.authentication;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public record AuthenticationResponseDto(
        String token
) {
}
