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
@Table(name = "cancel_booking_request")
public class CancelBookingRequest extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private TourBooking booking;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    private String status;

    private String reason;

    @Column(name = "is_deleted")
    private Boolean deleted;
}
