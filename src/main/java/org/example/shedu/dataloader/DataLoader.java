package org.example.shedu.dataloader;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.User;
import org.example.shedu.entity.enums.Role;
import org.example.shedu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Override
    public void run(String... args) throws Exception {
        if (ddl.equals("create")) {
            User user = User.builder()
                    .fullName("SuperAdmin")
                    .email("superadmin@example.com")
                    .password(passwordEncoder.encode("superadmin"))
                    .role(Role.ROLE_SUPER_ADMIN)
                    .build();
            userRepository.save(user);
        }
    }
}
