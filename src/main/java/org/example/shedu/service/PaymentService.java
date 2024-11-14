package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.repository.PaymentRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
}
