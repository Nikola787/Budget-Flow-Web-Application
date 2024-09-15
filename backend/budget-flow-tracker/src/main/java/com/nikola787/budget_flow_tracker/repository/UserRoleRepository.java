package com.nikola787.budget_flow_tracker.repository;

import com.nikola787.budget_flow_tracker.model.authentication.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByName(String role);
}
