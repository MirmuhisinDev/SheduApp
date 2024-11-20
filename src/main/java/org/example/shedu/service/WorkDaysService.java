package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.Barbershop;
import org.example.shedu.entity.Days;
import org.example.shedu.entity.WorkDays;
import org.example.shedu.repository.WorkDaysRepository;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkDaysService {
    private final WorkDaysRepository workDaysRepository;

    public void add(LocalTime start, LocalTime end, List<Days> days, Barbershop barbershop) {

            WorkDays workDay = new WorkDays();
            workDay.setBarbershop(barbershop);
            workDay.setOpenTime(start);
            workDay.setCloseTime(end);
            workDay.setDays(days);
            workDaysRepository.save(workDay);

    }
    public void  update(LocalTime start, LocalTime end, List<Days> days,Integer id) {

        WorkDays workDay = workDaysRepository.findByBarbershopIdAndDeletedFalse(id).orElse(null);
        if (workDay!=null) {
            workDay.setOpenTime(start);
            workDay.setCloseTime(end);
            workDay.setDays(days);
            workDaysRepository.save(workDay);
        }
    }

    public void delete(Integer id) {
        workDaysRepository.findByBarbershopIdAndDeletedFalse(id).ifPresent(workDay -> workDay.setDeleted(true));
    }

}
