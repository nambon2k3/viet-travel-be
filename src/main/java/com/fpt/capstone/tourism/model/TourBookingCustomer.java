package com.fpt.capstone.tourism.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourBookingCustomer extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tour_booking_id")
    private TourBooking tourBooking;

    @Column(name = "customer_name")
    private String customerName;
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String note;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
