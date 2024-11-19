package org.example.shedu.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "notification")

public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String message;

    @ManyToOne
    private User user;

    private boolean isRead;

    private boolean deleted;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
