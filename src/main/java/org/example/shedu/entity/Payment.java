package org.example.shedu.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.shedu.entity.enums.PayType;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "payment")

public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;

    private PayType payType;

    @ManyToOne
    private Service service;

    @Column(nullable = false)
    private Double amount;

    private boolean deleted;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
