package org.example.shedu.dataloader;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.*;
import org.example.shedu.entity.enums.Role;
import org.example.shedu.entity.enums.Week;
import org.example.shedu.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor

public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DaysRepository daysRepository;



    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Override
    public void run(String... args) throws Exception {
        if (ddl.equals("create")) {
            User user = User.builder()
                    .fullName("SuperAdmin")
                    .birthdate(25)
                    .phoneNumber("123456789")
                    .email("superadmin@gmail.com")
                    .password(passwordEncoder.encode("superadmin"))
                    .role(Role.ROLE_SUPER_ADMIN)
                    .enabled(true)
                    .build();
            userRepository.save(user);

            Days days = new Days(Week.Dushanba);
            daysRepository.save(days);
            Days days1 = new Days(Week.Seshanba);
            daysRepository.save(days1);
            Days days2 = new Days(Week.Chorshanba);
            daysRepository.save(days2);
            Days days3 = new Days(Week.Payshanba);
            daysRepository.save(days3);
            Days days4 = new Days(Week.Juma);
            daysRepository.save(days4);
            Days days5 = new Days(Week.Shanba);
            daysRepository.save(days5);
            Days days6 = new Days(Week.Yakshanba);
            daysRepository.save(days6);
        }
    }
}
