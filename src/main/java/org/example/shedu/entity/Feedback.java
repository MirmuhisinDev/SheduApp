package org.example.shedu.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "feedback")

public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Barbershop barbershop;

    @Column(nullable = false)
    private String comment;

    @Size(min = 1, max = 5, message = "Baholash tartibi 1 dan 5 gacha!")
    private Integer rating;

    private Boolean deleted;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
