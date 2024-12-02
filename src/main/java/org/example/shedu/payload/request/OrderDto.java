package org.example.shedu.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class OrderDto {
    private int hour;
    private int minute;
    private int endHour;
    private int endMinute;
    private LocalDate orderDate;
}
