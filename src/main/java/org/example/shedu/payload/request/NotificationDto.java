package org.example.shedu.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shedu.entity.User;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class NotificationDto {

    private String title;
    private Integer userID;
    private boolean read;
    private LocalDateTime createdAt;
}
