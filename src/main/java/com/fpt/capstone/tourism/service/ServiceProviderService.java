package com.fpt.capstone.tourism.service;

import java.awt.print.Pageable;

import com.fpt.capstone.tourism.dto.common.ChangableServiceProviderDTO;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.ServiceProviderDTO;
import org.springframework.data.domain.Page;


public interface ServiceProviderService {
        GeneralResponse<ServiceProviderDTO> save(ServiceProviderDTO serviceProviderDTO);
        GeneralResponse<ServiceProviderDTO> getServiceProviderById(Long id);
        Page<ServiceProviderDTO> getAllServiceProviders(Pageable pageable);
        GeneralResponse<ServiceProviderDTO> updateServiceProvider(Long id, ServiceProviderDTO serviceProviderDTO);
        GeneralResponse<ServiceProviderDTO> deleteServiceProvider(Long id, boolean isDeleted);
}
