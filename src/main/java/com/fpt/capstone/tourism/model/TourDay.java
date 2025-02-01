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
@Builder
@Data
public class TourDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "day_title")
    private String title;

    private String content;

    private String meal_plan;

    private boolean isDeleted;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;


    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;


    @OneToOne
    @JoinColumn(name = "tour_guide_id")
    private User tourGuide;









    @OneToMany(mappedBy = "tourDay")
    private Set<TourDayTransport> tourDayTransports;

    @OneToMany(mappedBy = "tourDay")
    private Set<TourDayMeal> tourDayMeals;

    @OneToMany(mappedBy = "tourDay")
    private Set<TourDayRoom> tourDayRooms;


    @OneToMany(mappedBy = "tourDay")
    private Set<TourDayActivity> tourDayActivities;


}
