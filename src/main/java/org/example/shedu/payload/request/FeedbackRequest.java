package org.example.shedu.payload.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FeedbackRequest {
    private int barbershopID;
    private String feedback;
    @Min(value = 1, message = "sot 1 dan katta bolishi kerak")
    @Max(value = 5, message = "son 5 dan kichik bolishi kerak")
    private Integer rating;
}
