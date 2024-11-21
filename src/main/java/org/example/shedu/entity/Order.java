package org.example.shedu.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.shedu.entity.enums.Status;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "orders")

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


    private LocalTime startTime;

    private LocalTime endTime;


    private LocalDate orderDay;

    private Status status;

    private boolean deleted;

    @CreationTimestamp
    private LocalDateTime createAt;
}
