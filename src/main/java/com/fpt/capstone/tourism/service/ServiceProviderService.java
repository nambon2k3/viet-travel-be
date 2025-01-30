package com.fpt.capstone.tourism.service;

import java.awt.print.Pageable;

import com.fpt.capstone.tourism.dto.common.ChangableServiceProviderDTO;
import com.fpt.capstone.tourism.dto.common.ServiceProviderDTO;
import org.springframework.data.domain.Page;


public interface ServiceProviderService {
        ServiceProviderDTO createServiceProvider(ChangableServiceProviderDTO serviceProviderDTO);
        ServiceProviderDTO getServiceProviderById(Long id);
        Page<ServiceProviderDTO> getAllServiceProviders(Pageable pageable);
        ServiceProviderDTO updateServiceProvider(Long id, ChangableServiceProviderDTO serviceProviderDTO);
        void deleteServiceProvider(Long id);
}
