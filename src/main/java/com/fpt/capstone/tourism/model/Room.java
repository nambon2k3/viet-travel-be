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
@Table(name = "room")
public class Room extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_type")
    private String roomType;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "bed_type")
    private String bedType;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "price_per_night")
    private Double pricePerNight;

    private double rating;

    @Column(name = "is_deleted")
    private Boolean deleted;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private ServiceProvider serviceProvider;

    @OneToMany(mappedBy = "room")
    private Set<TourDayRoom> tourDayRooms;
}
