package org.example.shedu.payload.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DistrictResponse {
    private int regionId;
    private String district;
    private LocalDateTime createdAt;
}