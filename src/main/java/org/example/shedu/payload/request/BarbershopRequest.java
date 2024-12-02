package org.example.shedu.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class BarbershopRequest implements Serializable {
    @NotBlank
    private String name;
    @NotBlank
    private String info;
    private int districtId;
    private Long fileId;

    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private List<Integer> days;
}
