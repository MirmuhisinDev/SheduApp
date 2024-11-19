package org.example.shedu.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shedu.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDto {

    private User user;
    private Integer serviceId;
    private Integer barbershopId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate orderDate;

}
