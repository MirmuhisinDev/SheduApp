package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.repository.BarbershopRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class BarbershopService {
    private final BarbershopRepository barbershopRepository;
}
