package com.nikola787.budget_flow_tracker.controller.userprofile;

import com.nikola787.budget_flow_tracker.dto.userprofile.UserProfileDto;
import com.nikola787.budget_flow_tracker.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("userprofile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;
    @GetMapping
    public ResponseEntity<UserProfileDto> findAllTransactionsByUser(
            Authentication user
    ) {
        return ResponseEntity.ok(userProfileService.find(user));
    }
}
