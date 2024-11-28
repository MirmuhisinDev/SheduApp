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
@Table(name = "chat")

public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    @Column(nullable = false)
    private String message;

    @ManyToOne
    private File file;
    private boolean deleted;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
