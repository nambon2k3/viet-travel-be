package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.ServiceContactDTO;
import com.fpt.capstone.tourism.dto.response.PagingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServiceContactService {
    GeneralResponse<?> createServiceContact(ServiceContactDTO serviceContactDTO);
    GeneralResponse<?> getServiceContactById(Long id);
    GeneralResponse<PagingDTO<List<ServiceContactDTO>>> getAllServiceContacts(int page, int size);
    GeneralResponse<?> updateServiceContact(Long id, ServiceContactDTO serviceContactDTO);
    GeneralResponse<?> deleteServiceContact(Long id);
}
