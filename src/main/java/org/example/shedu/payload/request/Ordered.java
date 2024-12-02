package org.example.shedu.payload.request;

import lombok.*;
import org.example.shedu.entity.enums.Status;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Ordered {
    private Integer serviceId;
    private Integer barbershopId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate orderDate;
    private Status status;
}
