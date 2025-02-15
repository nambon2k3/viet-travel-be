package com.fpt.capstone.tourism.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table (name = "tour_day")
public class TourDay extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "day_title")
    private String title;

    private String content;

    @Column(name = "meal_plan")
    private String mealPlan;

    @Column(name = "is_deleted")
    private Boolean deleted;

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
