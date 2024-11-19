package org.example.shedu.payload.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotificationResponse {
    private int id;
    private String title;
    private String message;
    private String userFullName;
    private LocalDateTime createdAt;


}
