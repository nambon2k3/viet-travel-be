package com.fpt.capstone.tourism.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Meal {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String meal_type;

    private double pricePerPerson;
    private String imageUrl;


    private boolean isDeleted;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private LocalDateTime expiredAt;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private ServiceProvider serviceProvider;

    @ManyToMany(mappedBy = "meals")
    private Set<TourDayMeal> tourDayMeals;

}
