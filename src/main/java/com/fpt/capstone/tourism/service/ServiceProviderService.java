package com.fpt.capstone.tourism.service;

import java.awt.print.Pageable;
import java.util.List;

import com.fpt.capstone.tourism.dto.common.ChangableServiceProviderDTO;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.ServiceProviderDTO;
import com.fpt.capstone.tourism.dto.response.PagingDTO;
import org.springframework.data.domain.Page;


public interface ServiceProviderService {
        GeneralResponse<ServiceProviderDTO> save(ServiceProviderDTO serviceProviderDTO);
        GeneralResponse<ServiceProviderDTO> getServiceProviderById(Long id);
        GeneralResponse<PagingDTO<List<ServiceProviderDTO>>> getAllServiceProviders(int page, int size, String keyword, Boolean isDeleted);
        GeneralResponse<PagingDTO<List<ServiceProviderDTO>>> getAllHotel(int page, int size, String keyword);
//        GeneralResponse<PagingDTO<List<ServiceProviderDTO>>> getAllRestaurant(int page, int size, String keyword);
        GeneralResponse<ServiceProviderDTO> updateServiceProvider(Long id, ServiceProviderDTO serviceProviderDTO);
        GeneralResponse<ServiceProviderDTO> deleteServiceProvider(Long id, boolean isDeleted);
}
