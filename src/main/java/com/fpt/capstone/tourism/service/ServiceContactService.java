package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.request.ServiceContactManagementRequestDTO;
import com.fpt.capstone.tourism.dto.response.PagingDTO;
import com.fpt.capstone.tourism.dto.response.ServiceContactManagementResponseDTO;

import java.util.List;

public interface ServiceContactService {
    GeneralResponse<?> createServiceContact(ServiceContactManagementRequestDTO serviceContactManagementRequestDTO);
    GeneralResponse<?> getServiceContactById(Long id);
    GeneralResponse<PagingDTO<List<ServiceContactManagementResponseDTO>>> getAllServiceContacts(int page, int size, String keyword, Boolean isDeleted, String sortField, String sortDirection, Long loggedInUserId);
    GeneralResponse<?> updateServiceContact(Long id, ServiceContactManagementRequestDTO serviceContactManagementRequestDTO);
    GeneralResponse<?> deleteServiceContact(Long id,boolean isDeleted);
}
