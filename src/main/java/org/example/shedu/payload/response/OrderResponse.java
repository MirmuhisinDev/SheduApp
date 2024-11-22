package org.example.shedu.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
    private int id;
    private String BarbershopName;
    private String serviceName;
    private Integer servicePrice;
    private LocalDate orderDate;
    private LocalTime orderTime;
    private String userFullName;

}
