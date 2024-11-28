package org.example.shedu.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.example.shedu.entity.File;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceResponse {
    private int id;
    private String barbershop;
    private String serviceName;
    private int price;
    private String description;
    private Long fileId;
    private LocalDateTime createdAt;
    private String ownerFullName;
}
