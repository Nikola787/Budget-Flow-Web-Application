package com.nikola787.budget_flow_tracker;

import com.nikola787.budget_flow_tracker.model.authentication.UserRole;
import com.nikola787.budget_flow_tracker.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class BudgetFlowTrackerApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("SYSTEM"));
		SpringApplication.run(BudgetFlowTrackerApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(UserRoleRepository userRoleRepository) {
		return args -> {
			if (userRoleRepository.findByName("USER").isEmpty()) {
				userRoleRepository.save(
						UserRole.builder().name("USER").build()
				);
			}
		};
	}
}
