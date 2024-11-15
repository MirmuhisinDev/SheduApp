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

public class BarbershopDto {
    @NotBlank
    private String name;
    @NotBlank
    private String info;
    @NotBlank
    private String email;
    private int districtId;
    private Long fileId;
}
