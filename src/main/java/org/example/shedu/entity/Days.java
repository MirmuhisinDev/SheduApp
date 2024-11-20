package org.example.shedu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shedu.entity.enums.Week;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Days {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int day;
    @Enumerated(EnumType.STRING)
    private Week week;
    public Days( Week week) {
        this.week = week;
    }

}
