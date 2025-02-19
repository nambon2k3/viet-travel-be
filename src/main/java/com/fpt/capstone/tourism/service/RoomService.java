package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.response.PagingDTO;
import com.fpt.capstone.tourism.dto.response.RoomServiceResponseDTO;

import java.util.List;

public interface RoomService {
    Object getById(Long id);

    GeneralResponse<PagingDTO<List<RoomServiceResponseDTO>>> getAll(int page, int size, String keyword, Boolean isDeleted, String sortField, String sortDirection, Long loggedInProviderId);
}
