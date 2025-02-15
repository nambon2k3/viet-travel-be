package com.fpt.capstone.tourism.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "service_provider")
public class ServiceProvider extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    private String name;

    private String abbreviation;

    private String website;

    private String email;

    private String phone;

    private String address;

    @Column(name = "is_deleted")
    private Boolean deleted;


    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "geo_position_id")
    private GeoPosition geoPosition;

    @ManyToMany
    @JoinTable(
            name = "provide_service",
            joinColumns = @JoinColumn(name = "provider_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<ServiceCategory> serviceCategories;
}
