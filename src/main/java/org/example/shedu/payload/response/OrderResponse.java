package org.example.shedu.payload.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class OrderResponse {
    private int id;
    private String BarbershopName;
    private String serviceName;
    private Integer servicePrice;
    private LocalDate orderDate;
    private LocalTime orderTime;
    private String userFullName;

}
