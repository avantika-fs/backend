package com.example.demo.config;

import com.example.demo.entity.SystemUser;
import com.example.demo.entity.enums.Role;
import com.example.demo.repository.SystemUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final SystemUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(SystemUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("admin@buildbridge.com").isEmpty()) {
            userRepository.save(SystemUser.builder()
                    .username("admin@buildbridge.com")
                    .email("admin@buildbridge.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.SUPERVISOR)
                    .fullName("Admin Supervisor")
                    .build());
        }
        if (userRepository.findByUsername("contractor1").isEmpty()) {
            userRepository.save(SystemUser.builder()
                    .username("contractor1")
                    .email("contractor1@buildbridge.com")
                    .password(passwordEncoder.encode("password123"))
                    .role(Role.CONTRACTOR)
                    .fullName("Default Contractor")
                    .build());
        }
        if (userRepository.findByUsername("client1").isEmpty()) {
            userRepository.save(SystemUser.builder()
                    .username("client1")
                    .email("client1@buildbridge.com")
                    .password(passwordEncoder.encode("password123"))
                    .role(Role.CLIENT)
                    .fullName("Default Client")
                    .build());
        }
    }
}
