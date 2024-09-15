package com.nikola787.budget_flow_tracker.mapper.userprofile;

import com.nikola787.budget_flow_tracker.dto.transaction.TransactionDto;
import com.nikola787.budget_flow_tracker.dto.userprofile.UserProfileDto;
import com.nikola787.budget_flow_tracker.model.authentication.UserProfile;
import com.nikola787.budget_flow_tracker.model.transaction.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProfileMapper {
    public UserProfileDto toUserProfileDto(UserProfile userProfile) {
        return UserProfileDto.builder()
                .firstname(userProfile.getFirstName())
                .lastname(userProfile.getLastName())
                .balance(userProfile.getBalance())
                .currency(userProfile.getCurrency())
                .build();
    }
}
