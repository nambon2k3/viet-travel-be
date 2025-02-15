package com.fpt.capstone.tourism.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "tour_day_meal")
public class TourDayMeal extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "day_id")
    private TourDay tourDay;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    private int number;

    @Column(name = "is_deleted")
    private Boolean deleted;
}
