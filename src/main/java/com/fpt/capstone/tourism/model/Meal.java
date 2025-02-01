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
public class Meal extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(name = "meal_type")
    private String mealType;

    @Column(name = "price_per_person")
    private double pricePerPerson;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private ServiceProvider serviceProvider;

    @OneToMany(mappedBy = "meal")
    private Set<TourDayMeal> tourDayMeals;

}
