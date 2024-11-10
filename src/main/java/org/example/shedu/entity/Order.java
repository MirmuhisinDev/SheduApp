package org.example.shedu.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.shedu.entity.enums.Status;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "order")

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Service service;

    @ManyToOne
    private Barbershop barbershop;

    @CreationTimestamp
    private LocalDateTime orderDayTime;

    private Integer duration;

    private Status status;

    private boolean deleted;

    @CreationTimestamp
    private LocalDateTime createAt;
}
