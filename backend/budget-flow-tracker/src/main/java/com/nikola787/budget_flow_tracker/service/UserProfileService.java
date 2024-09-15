package com.nikola787.budget_flow_tracker.service;

import com.nikola787.budget_flow_tracker.dto.userprofile.UserProfileDto;
import com.nikola787.budget_flow_tracker.model.authentication.UserProfile;
import org.springframework.security.core.Authentication;

public interface UserProfileService {

    UserProfileDto find(Authentication user);

    void updateBalance(UserProfile userProfile, long amount);
}
