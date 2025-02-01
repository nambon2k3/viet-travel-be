package com.fpt.capstone.tourism.service.impl;


import com.fpt.capstone.tourism.dto.common.ServiceCategoryDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.mapper.ServiceCategoryMapper;
import com.fpt.capstone.tourism.model.ServiceCategory;
import com.fpt.capstone.tourism.repository.ServiceCategoryRepository;
import com.fpt.capstone.tourism.service.ServiceCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.time.LocalDateTime;

import static com.fpt.capstone.tourism.constants.Constants.ServiceCategoryExceptionInformation.SERVICE_CATEGORY_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

    private final ServiceCategoryRepository serviceCategoryRepository;
    private final ServiceCategoryMapper mapper;

    @Override
    @Transactional
    public ServiceCategoryDTO createServiceCategory(ServiceCategoryDTO serviceCategoryDTO) {
        ServiceCategory serviceCategory = mapper.toEntity(serviceCategoryDTO);
        serviceCategory.setCreatedAt(LocalDateTime.now());
        serviceCategory.setUpdatedAt(LocalDateTime.now());
        serviceCategory.setDeleted(false);
        return mapper.toDTO(serviceCategoryRepository.save(serviceCategory));
    }

    @Override
    public ServiceCategoryDTO getServiceCategoryById(Long id) {
        ServiceCategory serviceCategory = serviceCategoryRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(SERVICE_CATEGORY_NOT_FOUND_MESSAGE));
        return mapper.toDTO(serviceCategory);
    }

    @Override
    public Page<ServiceCategoryDTO> getAllServiceCategories(Pageable pageable) {
        Page<ServiceCategory> serviceCategories = serviceCategoryRepository.findAll((org.springframework.data.domain.Pageable) pageable);
        return serviceCategories.map(mapper::toDTO);
    }


    @Override
    @Transactional
    public ServiceCategoryDTO updateServiceCategory(Long id, ServiceCategoryDTO serviceCategoryDTO) {
        ServiceCategory serviceCategory = serviceCategoryRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(SERVICE_CATEGORY_NOT_FOUND_MESSAGE));

        serviceCategory.setCategoryName(serviceCategoryDTO.getCategoryName());
        serviceCategory.setUpdatedAt(LocalDateTime.now());

        return mapper.toDTO(serviceCategoryRepository.save(serviceCategory));
    }

    @Override
    @Transactional
    public void deleteServiceCategory(Long id) {
        ServiceCategory serviceCategory = serviceCategoryRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(SERVICE_CATEGORY_NOT_FOUND_MESSAGE));
        serviceCategory.setDeleted(true);
        serviceCategory.setUpdatedAt(LocalDateTime.now());
        serviceCategoryRepository.save(serviceCategory);
    }
}

