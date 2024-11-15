package org.example.shedu.payload.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BarbershopResponse {
    private int id;
    private String name;
    private String info;
    private String email;
    private int districtId;
    private long fileId;
    private LocalDateTime createdAt;
    private String ownerFullName;
}
