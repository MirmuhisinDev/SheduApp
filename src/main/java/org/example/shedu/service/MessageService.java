package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.repository.MessageRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class MessageService {
    private final MessageRepository messageRepository;
}
