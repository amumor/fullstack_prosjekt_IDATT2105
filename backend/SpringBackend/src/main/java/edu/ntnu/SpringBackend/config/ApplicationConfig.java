package edu.ntnu.SpringBackend.config;

import edu.ntnu.SpringBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * ApplicationConfig is a configuration class that sets up the security configuration
 * for the application, including user details service, authentication provider,
 * and password encoder.
 * <p>
 * This class is responsible for configuring the authentication manager,
 * which is used to authenticate users based on their credentials.
 *
 * @author Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository repository;

    /**
     * This method returns a UserDetailsService that loads user details by email.
     * It uses the UserRepository to find the user by email and throws an exception
     * if the user is not found.
     *
     * @return a UserDetailsService that loads user details by email
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * This method returns an AuthenticationProvider that uses the UserDetailsService
     * and PasswordEncoder to authenticate users.
     * It is used to configure the authentication manager.
     *
     * @return an AuthenticationProvider that uses the UserDetailsService and PasswordEncoder
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * This method returns an AuthenticationManager that is used to authenticate users.
     * It is configured with the AuthenticationConfiguration.
     *
     * @param config the AuthenticationConfiguration
     * @return an AuthenticationManager that is used to authenticate users
     * @throws Exception if an error occurs while creating the authentication manager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * This method returns a PasswordEncoder that is used to encode passwords.
     * It uses the BCryptPasswordEncoder for password encoding.
     *
     * @return a PasswordEncoder that is used to encode passwords
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
