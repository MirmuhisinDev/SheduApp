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
@Table(name = "barbershop")

public class Barbershop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String info;

    @Column(nullable = false)
    private String email;

    @ManyToOne
    private District district;

    @ManyToOne
    private File file;

    @ManyToOne
    private User createdBy;
    private boolean deleted;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private double latitude;
    private double longitude;

}
