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
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String serviceName;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    private Barbershop barbershop;

    @ManyToOne
    private File file;

    private Boolean deleted;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
