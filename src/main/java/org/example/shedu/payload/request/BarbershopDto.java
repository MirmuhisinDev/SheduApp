package org.example.shedu.payload.request;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BarbershopDto {
    private int id;
    private String barbershopName;
    private List<ServiceDto> services;
}
