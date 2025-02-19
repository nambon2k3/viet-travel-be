package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.ServiceContactManagementRequestDTO;
import com.fpt.capstone.tourism.dto.response.PagingDTO;

import java.util.List;

public interface ServiceContactService {
    GeneralResponse<?> createServiceContact(ServiceContactManagementRequestDTO serviceContactManagementRequestDTO);
    GeneralResponse<?> getServiceContactById(Long id);
    GeneralResponse<PagingDTO<List<ServiceContactManagementRequestDTO>>> getAllServiceContacts(int page, int size, String keyword,Boolean isDeleted,String sortField, String sortDirection, Long loggedInUserId);
    GeneralResponse<?> updateServiceContact(Long id, ServiceContactManagementRequestDTO serviceContactManagementRequestDTO);
    GeneralResponse<?> deleteServiceContact(Long id,boolean isDeleted);
}
