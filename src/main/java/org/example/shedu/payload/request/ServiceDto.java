package org.example.shedu.payload.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ServiceDto {
    private int id;
    private String serviceName;
    private int price;
}
