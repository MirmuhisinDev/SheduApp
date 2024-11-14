package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.repository.WorkDaysRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkDaysService {
    private final WorkDaysRepository workDaysRepository;
}
