package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.repository.OrderRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class OrderService {
    private final OrderRepository orderRepository;
}
