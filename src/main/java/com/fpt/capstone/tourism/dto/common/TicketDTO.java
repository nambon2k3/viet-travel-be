package com.fpt.capstone.tourism.dto.common;

import com.fpt.capstone.tourism.model.Tour;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketDTO {
    private Long id;
    private String type;
    private Double price;
    private Boolean deleted;
}
