package org.example.shedu.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceDto {
    @NotBlank
    private String serviceName;
    private Integer price;
    private Integer serviceTime;
    @NotBlank
    private String description;
    private Integer barbershopId;
    private Long fileId;
}
