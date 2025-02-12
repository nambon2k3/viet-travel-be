package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.ServiceContactManagementRequestDTO;
import com.fpt.capstone.tourism.dto.response.PagingDTO;

import java.util.List;

public interface ServiceContactService {
    GeneralResponse<?> create(ServiceContactManagementRequestDTO serviceContactManagementRequestDTO);
    GeneralResponse<?> getById(Long id);
    GeneralResponse<PagingDTO<List<ServiceContactManagementRequestDTO>>> getAll(int page, int size);
    GeneralResponse<?> update(Long id, ServiceContactManagementRequestDTO serviceContactManagementRequestDTO);
    GeneralResponse<?> delete(Long id);

    GeneralResponse<?> recover(Long id);
}
