package org.example.shedu.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedbackResponse {
    private int feedbackId;
    private String fromUser;
    private String barbershop;
    private String feedback;
    private int status;
    private LocalDateTime createdAt;
}
