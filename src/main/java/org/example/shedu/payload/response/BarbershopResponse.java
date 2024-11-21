package org.example.shedu.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.*;
import org.example.shedu.entity.Days;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BarbershopResponse {
    private int id;
    private String name;
    private String info;
    private String email;
    private String district;
    private Long fileId;
    private LocalDateTime createdAt;
    private String ownerFullName;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<Days> days;

}
