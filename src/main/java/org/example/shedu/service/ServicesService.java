package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.repository.ServiceRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServicesService {
    private final ServiceRepository serviceRepository;
}
