package edu.ntnu.SpringBackend.config;

import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.enums.Role;
import edu.ntnu.SpringBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import io.github.cdimascio.dotenv.Dotenv;

@Component
@RequiredArgsConstructor
public class AdminDataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Dotenv dotenv = Dotenv.load();
    private final Logger logger = LoggerFactory.getLogger(AdminDataSeeder.class);

    private final String DEFAULT_ADMIN_EMAIL = dotenv.get("APP_DEFAULT_ADMIN_EMAIL");
    private final String DEFAULT_ADMIN_PASSWORD = dotenv.get("APP_DEFAULT_ADMIN_PASSWORD");

    @Override
    public void run(String... args) {
        boolean hasAdmin = userRepository.existsByRole(Role.ROLE_ADMIN);
        if (!hasAdmin) {

            User admin = User.builder()
                    .firstName("Admin")
                    .lastName("User")
                    .email(DEFAULT_ADMIN_EMAIL)
                    .phoneNumber("1")
                    .password(passwordEncoder.encode(DEFAULT_ADMIN_PASSWORD))
                    .role(Role.ROLE_ADMIN)
                    .build();

            userRepository.save(admin);

            logger.warn("=== Default admin created ===");
            logger.warn("Email: {}", DEFAULT_ADMIN_EMAIL);
            logger.warn("Please change this password ASAP!");
        }
    }
}