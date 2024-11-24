package org.example.shedu.payload.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FeedbackRequest {
    private int barbershopID;
    private String feedback;
    private int rating;
}
