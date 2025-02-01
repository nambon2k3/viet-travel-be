package com.fpt.capstone.tourism.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TourDayTransport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "day_id")
    private TourDay tourDay;

    @ManyToOne
    @JoinColumn(name = "transport_id")
    private Transport transport;

    private int number;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
