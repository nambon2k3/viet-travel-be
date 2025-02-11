package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.dto.common.*;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.model.GeoPosition;
import com.fpt.capstone.tourism.model.Location;
import com.fpt.capstone.tourism.model.ServiceCategory;
import com.fpt.capstone.tourism.model.ServiceProvider;
import com.fpt.capstone.tourism.repository.ServiceProviderRepository;
import com.fpt.capstone.tourism.service.ServiceProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static com.fpt.capstone.tourism.constants.Constants.UserExceptionInformation.USER_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class ServiceProviderServiceImpl implements ServiceProviderService {

    private final ServiceProviderRepository serviceProviderRepository;

    @Override
    @Transactional
    public ServiceProviderDTO createServiceProvider(ChangableServiceProviderDTO changableServiceProviderDTO) {
        ServiceProvider serviceProvider = mapToEntity(changableServiceProviderDTO);
        serviceProvider.setCreatedAt(LocalDateTime.now());
        serviceProvider.setUpdatedAt(LocalDateTime.now());
        ServiceProvider savedServiceProvider = serviceProviderRepository.save(serviceProvider);
        return mapToDTO(savedServiceProvider);
    }

    @Override
    public ServiceProviderDTO getServiceProviderById(Long id) {
        ServiceProvider serviceProvider = serviceProviderRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(USER_NOT_FOUND_MESSAGE));
        return mapToDTO(serviceProvider);
    }

    @Override
    public Page<ServiceProviderDTO> getAllServiceProviders(java.awt.print.Pageable pageable) {
        Page<ServiceProvider> serviceProviders = serviceProviderRepository.findAll((Pageable) pageable);
        return serviceProviders.map(this::mapToDTO);
    }

    @Override
    @Transactional
    public ServiceProviderDTO updateServiceProvider(Long id, ChangableServiceProviderDTO serviceProviderDTO) {
        ServiceProvider serviceProvider = serviceProviderRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(USER_NOT_FOUND_MESSAGE));

        ServiceProvider updatedServiceProvider = mapToEntity(serviceProviderDTO);
        updatedServiceProvider.setId(serviceProvider.getId());
        updatedServiceProvider.setCreatedAt(serviceProvider.getCreatedAt());
        updatedServiceProvider.setUpdatedAt(LocalDateTime.now());

        ServiceProvider savedServiceProvider = serviceProviderRepository.save(updatedServiceProvider);
        return mapToDTO(savedServiceProvider);
    }

    @Override
    @Transactional
    public void deleteServiceProvider(Long id) {
        ServiceProvider serviceProvider = serviceProviderRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(USER_NOT_FOUND_MESSAGE));
        serviceProvider.setDeleted(true);
        serviceProvider.setUpdatedAt(LocalDateTime.now());
        serviceProviderRepository.save(serviceProvider);
    }

    private ServiceProviderDTO mapToDTO(ServiceProvider serviceProvider) {
        ServiceProviderDTO dto = new ServiceProviderDTO();
        dto.setId(serviceProvider.getId());
        dto.setImageUrl(serviceProvider.getImageUrl());
        dto.setAbbreviation(serviceProvider.getAbbreviation());
        dto.setWebsite(serviceProvider.getWebsite());
        dto.setEmail(serviceProvider.getEmail());
        dto.setPhone(serviceProvider.getPhone());
        dto.setAddress(serviceProvider.getAddress());
        dto.setDeleted(serviceProvider.isDeleted());
        dto.setCreatedAt(serviceProvider.getCreatedAt());
        dto.setUpdatedAt(serviceProvider.getUpdatedAt());

        if (serviceProvider.getLocation() != null) {
            LocationDTO locationDTO = new LocationDTO();
            locationDTO.setId(serviceProvider.getLocation().getId());
            locationDTO.setName(serviceProvider.getLocation().getName());
            dto.setLocation(locationDTO);
        }

        if (serviceProvider.getGeoPosition() != null) {
            GeoPositionDTO geoPositionDTO = new GeoPositionDTO();
            geoPositionDTO.setId(serviceProvider.getGeoPosition().getId());
            geoPositionDTO.setLatitude(serviceProvider.getGeoPosition().getLatitude());
            geoPositionDTO.setLongitude(serviceProvider.getGeoPosition().getLongitude());
            dto.setGeoPosition(geoPositionDTO);
        }

        if (serviceProvider.getServiceCategories() != null) {
             dto.setServiceCategories(serviceProvider.getServiceCategories().stream()
                    .map(category -> {
                        ServiceCategoryDTO categoryDTO = new ServiceCategoryDTO();
                        categoryDTO.setId(category.getId());
                        categoryDTO.setName(category.getCategoryName());
                        return categoryDTO;
                    })
                    .collect(Collectors.toSet()));
        }

        return dto;
    }

    private ServiceProvider mapToEntity(ChangableServiceProviderDTO dto) {
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setImageUrl(dto.getImageUrl());
        serviceProvider.setAbbreviation(dto.getAbbreviation());
        serviceProvider.setWebsite(dto.getWebsite());
        serviceProvider.setEmail(dto.getEmail());
        serviceProvider.setPhone(dto.getPhone());
        serviceProvider.setAddress(dto.getAddress());

        if (dto.getLocation() != null) {
            Location location = new Location();
            location.setId(dto.getLocation().getId());
            location.setName(dto.getLocation().getName());
            serviceProvider.setLocation(location);
        }

        if (dto.getGeoPosition() != null) {
            GeoPosition geoPosition = new GeoPosition();
            geoPosition.setId(dto.getGeoPosition().getId());
            geoPosition.setLatitude(dto.getGeoPosition().getLatitude());
            geoPosition.setLongitude(dto.getGeoPosition().getLongitude());
            serviceProvider.setGeoPosition(geoPosition);
        }

        if (dto.getServiceCategories() != null) {
            serviceProvider.setServiceCategories(dto.getServiceCategories().stream()
                    .map(categoryDTO -> {
                        ServiceCategory category = new ServiceCategory();
                        category.setId(categoryDTO.getId());
                        category.setCategoryName(categoryDTO.getName());
                        return category;
                    })
                    .collect(Collectors.toSet()));
        }

        return serviceProvider;
    }
}

