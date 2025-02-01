package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.common.ServiceCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ServiceCategoryService {
    ServiceCategoryDTO createServiceCategory(ServiceCategoryDTO serviceCategoryDTO);

    ServiceCategoryDTO getServiceCategoryById(Long id);

    Page<ServiceCategoryDTO> getAllServiceCategories(Pageable pageable);

    ServiceCategoryDTO updateServiceCategory(Long id, ServiceCategoryDTO serviceCategoryDTO);

    void deleteServiceCategory(Long id);
}
