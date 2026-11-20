package com.example.employeemanagement.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

@Configuration
public class AdminBootstrap {
    @Bean
    CommandLineRunner createBootstrapAdmin(AppUserRepository users, PasswordEncoder encoder,
                                           @Value("${app.bootstrap-admin.username:}") String username,
                                           @Value("${app.bootstrap-admin.password:}") String password) {
        return args -> {
            if (StringUtils.hasText(username) && StringUtils.hasText(password)
                    && !users.existsByUsername(username.trim())) {
                users.save(new AppUser(username.trim(), encoder.encode(password), Role.ADMIN));
            }
        };
    }
}
