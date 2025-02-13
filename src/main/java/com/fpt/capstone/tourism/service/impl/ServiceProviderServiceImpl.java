package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.dto.common.*;
import com.fpt.capstone.tourism.dto.response.PagingDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.helper.validator.Validator;
import com.fpt.capstone.tourism.mapper.ServiceCategoryMapper;
import com.fpt.capstone.tourism.mapper.ServiceProviderMapper;
import com.fpt.capstone.tourism.model.GeoPosition;
import com.fpt.capstone.tourism.model.Location;
import com.fpt.capstone.tourism.model.ServiceCategory;
import com.fpt.capstone.tourism.model.ServiceProvider;
import com.fpt.capstone.tourism.repository.LocationRepository;
import com.fpt.capstone.tourism.repository.ServiceProviderRepository;
import com.fpt.capstone.tourism.service.ServiceProviderService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.fpt.capstone.tourism.constants.Constants.Message.*;
import static com.fpt.capstone.tourism.constants.Constants.UserExceptionInformation.*;

@Service
@RequiredArgsConstructor
public class ServiceProviderServiceImpl implements ServiceProviderService {

    private final ServiceProviderRepository serviceProviderRepository;
    private final LocationRepository locationRepository;
    private final ServiceProviderMapper serviceProviderMapper;
    private final ServiceCategoryMapper serviceCategoryMapper;

    @Override
    public GeneralResponse<ServiceProviderDTO> save(ServiceProviderDTO serviceProviderDTO) {
        try{
            //Validate input data
            Validator.validateServiceProvider(serviceProviderDTO);

            //Check duplicate email and phone number
            if(serviceProviderRepository.findByEmail(serviceProviderDTO.getEmail()) != null){
                throw BusinessException.of(EMAIL_ALREADY_EXISTS_MESSAGE);
            }
            if(serviceProviderRepository.findByPhone(serviceProviderDTO.getPhone()) != null){
                throw BusinessException.of(PHONE_ALREADY_EXISTS_MESSAGE);
            }
            //Store data into database
            ServiceProvider serviceProvider = serviceProviderMapper.toEntity(serviceProviderDTO);
            serviceProvider.setId(null);
            serviceProvider.setDeleted(false);
            serviceProvider.setCreatedAt(LocalDateTime.now());
            serviceProviderRepository.save(serviceProvider);
            return new GeneralResponse<>(HttpStatus.OK.value(), CREATE_SERVICE_PROVIDER_SUCCESS, serviceProviderDTO);
        } catch (BusinessException be){
            throw be;
        } catch (Exception ex){
            throw BusinessException.of(CREATE_SERVICE_PROVIDER_FAIL, ex);
        }

    }

    @Transactional
    @Override
    public GeneralResponse<ServiceProviderDTO> getServiceProviderById(Long id) {
        try{
            ServiceProvider serviceProvider = serviceProviderRepository.findById(id).orElseThrow();
            ServiceProviderDTO serviceProviderDTO = serviceProviderMapper.toDTO(serviceProvider);
            return new GeneralResponse<>(HttpStatus.OK.value(), GENERAL_SUCCESS_MESSAGE, serviceProviderDTO);
        } catch (BusinessException be){
            throw be;
        } catch (Exception ex){
            throw BusinessException.of(GENERAL_FAIL_MESSAGE, ex);
        }
    }

    @Override
    public GeneralResponse<PagingDTO<List<ServiceProviderDTO>>> getAllServiceProviders(int page, int size, String keyword, Boolean isDeleted) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
            Specification<ServiceProvider> spec = buildSearchSpecification(keyword, isDeleted);

            Page<ServiceProvider> serviceProviderPage = serviceProviderRepository.findAll(spec, pageable);
            List<ServiceProviderDTO> serviceProviderDTOS = serviceProviderPage.getContent().stream()
                    .map(serviceProviderMapper::toDTO)
                    .collect(Collectors.toList());

            return buildPagedResponse(serviceProviderPage, serviceProviderDTOS);
        } catch (Exception ex) {
            throw BusinessException.of("not ok", ex);
        }
    }


    @Override
    @Transactional
    public GeneralResponse<ServiceProviderDTO> updateServiceProvider(Long id, ServiceProviderDTO serviceProviderDTO) {
        try{
            //Find in database
            ServiceProvider serviceProvider = serviceProviderRepository.findById(id).orElseThrow();

            //Validate input data
            Validator.validateServiceProvider(serviceProviderDTO);

            //Update service provider information
            if(!serviceProviderDTO.getImageUrl().equals(serviceProvider.getImageUrl())){
                serviceProvider.setImageUrl(serviceProviderDTO.getImageUrl());
            }
            if(!serviceProviderDTO.getAbbreviation().equals(serviceProvider.getAbbreviation())){
                serviceProvider.setAbbreviation(serviceProviderDTO.getAbbreviation());
            }
            if(!serviceProviderDTO.getWebsite().equals(serviceProvider.getWebsite())){
                serviceProvider.setWebsite(serviceProviderDTO.getWebsite());
            }
            if(!serviceProviderDTO.getEmail().equals(serviceProvider.getEmail())){
                //Check duplicate email
                if(serviceProviderRepository.findByEmail(serviceProviderDTO.getEmail()) != null){
                    throw BusinessException.of(EMAIL_ALREADY_EXISTS_MESSAGE);
                }
                serviceProvider.setEmail(serviceProviderDTO.getEmail());
            }
            if(!serviceProviderDTO.getPhone().equals(serviceProvider.getPhone())){
                if(serviceProviderRepository.findByPhone(serviceProviderDTO.getPhone()) != null){
                    throw BusinessException.of(PHONE_ALREADY_EXISTS_MESSAGE);
                }
                serviceProvider.setPhone(serviceProviderDTO.getPhone());
            }
            if(!serviceProviderDTO.getAddress().equals(serviceProvider.getAddress())){
                serviceProvider.setAddress(serviceProviderDTO.getAddress());
            }

            if(!serviceProviderDTO.getGeoPosition().getId().equals(serviceProvider.getGeoPosition().getId())){
                GeoPosition geoPosition = GeoPosition.builder()
                        .latitude(serviceProviderDTO.getGeoPosition().getLatitude())
                        .longitude(serviceProviderDTO.getGeoPosition().getLongitude()).build() ;
                serviceProvider.setGeoPosition(geoPosition);
            }

            if(!serviceProviderDTO.getLocation().getId().equals(serviceProvider.getLocation().getId())) {
                Location location = locationRepository.findById(serviceProviderDTO.getLocation().getId()).orElseThrow();
                serviceProvider.setLocation(location);
            }
            serviceProvider.setServiceCategories(serviceProviderDTO.getServiceCategories()
                    .stream().map(serviceCategoryMapper::toEntity).collect(Collectors.toList()));

            serviceProvider.setUpdatedAt(LocalDateTime.now());

            serviceProviderRepository.save(serviceProvider);

            serviceProviderDTO.setId(serviceProvider.getId());
            return new GeneralResponse<>(HttpStatus.OK.value(), UPDATE_SERVICE_PROVIDER_SUCCESS, serviceProviderDTO);
        } catch (BusinessException be){
            throw be;
        } catch (Exception ex){
            throw BusinessException.of(UPDATE_SERVICE_PROVIDER_FAIL, ex);
        }

    }

    @Override
    @Transactional
    public GeneralResponse<ServiceProviderDTO> deleteServiceProvider(Long id, boolean isDeleted) {
        try{
            ServiceProvider serviceProvider = serviceProviderRepository.findById(id).orElseThrow();

            serviceProvider.setDeleted(isDeleted);
            serviceProvider.setUpdatedAt(LocalDateTime.now());
            serviceProviderRepository.save(serviceProvider);

            ServiceProviderDTO serviceProviderDTO = serviceProviderMapper.toDTO(serviceProvider);
            return new GeneralResponse<>(HttpStatus.OK.value(), UPDATE_SERVICE_PROVIDER_SUCCESS, serviceProviderDTO);
        }catch (BusinessException be){
            throw be;
        } catch (Exception ex){
            throw BusinessException.of(UPDATE_SERVICE_PROVIDER_FAIL, ex);
        }
    }

    private Specification<ServiceProvider> buildSearchSpecification(String keyword, Boolean isDeleted) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (keyword != null && !keyword.isEmpty()) {
                Predicate namePredicate = cb.like(root.get("name"), "%" + keyword + "%");
                predicates.add(namePredicate);
            }

            if (isDeleted != null) {
                predicates.add(cb.equal(root.get("isDeleted"), isDeleted));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private GeneralResponse<PagingDTO<List<ServiceProviderDTO>>> buildPagedResponse(Page<ServiceProvider> serviceProviderPage, List<ServiceProviderDTO> serviceProviders) {
        PagingDTO<List<ServiceProviderDTO>> pagingDTO = PagingDTO.<List<ServiceProviderDTO>>builder()
                .page(serviceProviderPage.getNumber())
                .size(serviceProviderPage.getSize())
                .total(serviceProviderPage.getTotalElements())
                .items(serviceProviders)
                .build();

        return new GeneralResponse<>(HttpStatus.OK.value(), "ok", pagingDTO);
    }


}

