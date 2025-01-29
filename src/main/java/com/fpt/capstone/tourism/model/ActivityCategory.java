package com.fpt.capstone.tourism.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ActivityCategory")
public class ActivityCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean isDeleted;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_date")
    private LocalDateTime updatedAt;

}
