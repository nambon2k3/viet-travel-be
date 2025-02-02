package com.fpt.capstone.tourism.service.impl;


import com.fpt.capstone.tourism.dto.common.ServiceCategoryDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.mapper.ServiceCategoryMapper;
import com.fpt.capstone.tourism.model.ServiceCategory;
import com.fpt.capstone.tourism.repository.ServiceCategoryRepository;
import com.fpt.capstone.tourism.service.ServiceCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.fpt.capstone.tourism.constants.Constants.ServiceCategoryExceptionInformation.SERVICE_CATEGORY_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

    private final ServiceCategoryRepository serviceCategoryRepository;
    private final ServiceCategoryMapper serviceCategoryMapper;

    @Override
    @Transactional
    public ServiceCategoryDTO createServiceCategory(ServiceCategoryDTO serviceCategoryDTO) {
        ServiceCategory serviceCategory = serviceCategoryMapper.toEntity(serviceCategoryDTO);
        serviceCategory.setDeleted(false);
        return serviceCategoryMapper.toDTO(serviceCategoryRepository.save(serviceCategory));
    }

    @Override
    public ServiceCategoryDTO getServiceCategoryById(Long id) {
        ServiceCategory serviceCategory = serviceCategoryRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(SERVICE_CATEGORY_NOT_FOUND_MESSAGE));
        return serviceCategoryMapper.toDTO(serviceCategory);
    }

    @Override
    public Page<ServiceCategoryDTO> getAllServiceCategories(Pageable pageable) {
        Page<ServiceCategory> serviceCategories = serviceCategoryRepository.findAll(pageable);
        return serviceCategories.map(serviceCategoryMapper::toDTO);
    }


    @Override
    @Transactional
    public ServiceCategoryDTO updateServiceCategory(Long id, ServiceCategoryDTO serviceCategoryDTO) {
        ServiceCategory serviceCategory = serviceCategoryRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(SERVICE_CATEGORY_NOT_FOUND_MESSAGE));
        serviceCategory.setCategoryName(serviceCategoryDTO.getCategoryName());
        return serviceCategoryMapper.toDTO(serviceCategoryRepository.save(serviceCategory));
    }

    @Override
    @Transactional
    public void deleteServiceCategory(Long id) {
        ServiceCategory serviceCategory = serviceCategoryRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(SERVICE_CATEGORY_NOT_FOUND_MESSAGE));
        if (!serviceCategory.isDeleted()) {
            serviceCategory.setDeleted(true);
            serviceCategoryRepository.saveAndFlush(serviceCategory);
        }
    }
}

