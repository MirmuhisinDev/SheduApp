package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.repository.FeedbackRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
}
