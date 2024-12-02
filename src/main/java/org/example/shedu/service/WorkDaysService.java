package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.Barbershop;
import org.example.shedu.entity.Days;
import org.example.shedu.entity.WorkDays;
import org.example.shedu.repository.DaysRepository;
import org.example.shedu.repository.WorkDaysRepository;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkDaysService {
    private final WorkDaysRepository workDaysRepository;
    private final DaysRepository daysRepository;

    public void add(int startHour, int startMinute, int endHour, int endMinute, List<Integer> days, Barbershop barbershop) {

        List<Days> days1 = new ArrayList<>();
         for (Integer day : days) {
             Days days2=daysRepository.findById(day).orElse(null);
             days1.add(days2);
         }
         LocalTime start = LocalTime.of(startHour, startMinute);
         LocalTime end = LocalTime.of(endHour, endMinute);

            WorkDays workDay = new WorkDays();
            workDay.setBarbershop(barbershop);
            workDay.setOpenTime(start);
            workDay.setCloseTime(end);
            workDay.setDays(days1);
            workDaysRepository.save(workDay);

    }
    public void  update(LocalTime start, LocalTime end, List<Integer> days,Integer id) {

        WorkDays workDay = workDaysRepository.findByBarbershopIdAndDeletedFalse(id).orElse(null);
        List<Days> days1 = new ArrayList<>();
        for (Integer day : days) {
            Days days2=daysRepository.findById(day).orElse(null);
            days1.add(days2);
        }
        if (workDay!=null) {
            workDay.setOpenTime(start);
            workDay.setCloseTime(end);
            workDay.setDays(days1);
            workDaysRepository.save(workDay);
        }
    }

    public void delete(Integer id) {
        workDaysRepository.findByBarbershopIdAndDeletedFalse(id).ifPresent(workDay -> workDay.setDeleted(true));
    }

}
