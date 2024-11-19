package org.example.shedu.payload.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ServiceResponse {
    private int id;
    private String barbershop;
    private String serviceName;
    private int price;
    private Integer serviceTime;
    private String description;
    private Long fileId;
    private LocalDateTime createdAt;
    private String ownerFullName;
}
