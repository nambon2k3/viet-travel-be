package com.fpt.capstone.tourism.dto.common;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PublicActivityDetailDTO {
    ActivityDTO detailActivityDTO;
    List<ActivityDTO> relatedActivities;
}
