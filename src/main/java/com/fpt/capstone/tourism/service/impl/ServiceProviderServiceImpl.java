package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.dto.common.*;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.mapper.ServiceProviderMapper;
import com.fpt.capstone.tourism.model.GeoPosition;
import com.fpt.capstone.tourism.model.Location;
import com.fpt.capstone.tourism.model.ServiceCategory;
import com.fpt.capstone.tourism.model.ServiceProvider;
import com.fpt.capstone.tourism.repository.ServiceProviderRepository;
import com.fpt.capstone.tourism.service.ServiceProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static com.fpt.capstone.tourism.constants.Constants.Message.*;
import static com.fpt.capstone.tourism.constants.Constants.UserExceptionInformation.*;

@Service
@RequiredArgsConstructor
public class ServiceProviderServiceImpl implements ServiceProviderService {

    private final ServiceProviderRepository serviceProviderRepository;
    private final ServiceProviderMapper serviceProviderMapper;

    @Override
    public GeneralResponse<ServiceProviderDTO> save(ServiceProviderDTO serviceProviderDTO) {
        try{
            ServiceProvider serviceProvider = serviceProviderMapper.toEntity(serviceProviderDTO);
            serviceProviderRepository.save(serviceProvider);
            return new GeneralResponse<>(HttpStatus.OK.value(), CREATE_SERVICE_PROVIDER_SUCCESS, serviceProviderDTO);
        } catch (BusinessException be){
            throw be;
        } catch (Exception ex){
            throw BusinessException.of(CREATE_SERVICE_PROVIDER_FAIL, ex);
        }

    }

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

//    @Override
//    public Page<ServiceProviderDTO> getAllServiceProviders(java.awt.print.Pageable pageable) {
//        Page<ServiceProvider> serviceProviders = serviceProviderRepository.findAll((Pageable) pageable);
//        return serviceProviders.map(this::mapToDTO);
//    }

    @Override
    @Transactional
    public GeneralResponse<ServiceProviderDTO> updateServiceProvider(Long id, ServiceProviderDTO serviceProviderDTO) {
        try{
            //Find in database
            ServiceProvider serviceProvider = serviceProviderRepository.findById(id).orElseThrow();

            //Update service provider information
            ServiceProvider newServiceProvider = serviceProviderMapper.toEntity(serviceProviderDTO);
            newServiceProvider.setId(serviceProvider.getId());

            serviceProviderRepository.save(newServiceProvider);
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


}

