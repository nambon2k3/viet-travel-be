package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.common.ServiceContactDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServiceContactService {
    ServiceContactDTO createServiceContact(ServiceContactDTO serviceContactDTO);
    ServiceContactDTO getServiceContactById(Long id);
    Page<ServiceContactDTO> getAllServiceContacts(Pageable pageable);
    ServiceContactDTO updateServiceContact(Long id, ServiceContactDTO serviceContactDTO);
    void deleteServiceContact(Long id);
}
