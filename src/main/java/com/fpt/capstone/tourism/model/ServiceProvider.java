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
public class ServiceProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    private String abbreviation;

    private String website;

    private String email;

    private String phone;

    private String address;


    private boolean isDeleted;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_date")
    private LocalDateTime updatedAt;


    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToOne
    @JoinColumn(name = "geo_position_id")
    private GeoPosition geoPosition;

    @ManyToMany
    @JoinTable(
            name = "provide_service",
            joinColumns = @JoinColumn(name = "provider_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<ServiceCategory> serviceCategories;
}
