package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.repository.NotificationRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class NotificationService {
    private final NotificationRepository notificationRepository;
}
