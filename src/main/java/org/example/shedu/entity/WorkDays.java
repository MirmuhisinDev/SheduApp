package org.example.shedu.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "work_days")

public class WorkDays {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Barbershop barbershop;

    @Column(nullable = false)
    private Integer openTime;

    @Column(nullable = false)
    private Integer closeTime;
}
