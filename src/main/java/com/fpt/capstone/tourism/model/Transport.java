package com.fpt.capstone.tourism.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "transport")
public class Transport extends BaseEntity{
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transport_name")
    private String transportName;
    @Column(name = "transport_type")
    private String transportType;
    private String brand;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "driver_name")
    private String driverName;

    private String description;

    private double price;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private ServiceProvider serviceProvider;

    @OneToMany(mappedBy = "transport")
    private Set<TourDayTransport> tourDayTransports;

}
