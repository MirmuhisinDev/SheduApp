package org.example.shedu.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegionResponse {
    private int id;
    private String regionName;
}
