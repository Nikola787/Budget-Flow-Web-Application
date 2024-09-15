package com.nikola787.budget_flow_tracker.service.impl;

import com.nikola787.budget_flow_tracker.dto.userprofile.UserProfileDto;
import com.nikola787.budget_flow_tracker.mapper.userprofile.UserProfileMapper;
import com.nikola787.budget_flow_tracker.model.authentication.UserProfile;
import com.nikola787.budget_flow_tracker.repository.UserProfileRepository;
import com.nikola787.budget_flow_tracker.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileMapper userProfileMapper;
    private final UserProfileRepository userProfileRepository;

    @Override
    public UserProfileDto find(Authentication user) {
        UserProfile userProfile = ((UserProfile) user.getPrincipal());
        return userProfileMapper.toUserProfileDto(userProfile);
    }

    @Override
    @Transactional
    public void updateBalance(UserProfile userProfile, long amount) {
        userProfile.setBalance(userProfile.getBalance() + amount);
        userProfileRepository.save(userProfile);
    }
}
