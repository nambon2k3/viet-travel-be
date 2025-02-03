package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.dto.common.ServiceContactDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.mapper.ServiceContactMapper;
import com.fpt.capstone.tourism.model.ServiceContact;
import com.fpt.capstone.tourism.repository.ServiceContactRepository;
import com.fpt.capstone.tourism.service.ServiceContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.fpt.capstone.tourism.constants.Constants.Message.DUPLICATE_SERVICE_CONTACT_PHONE;
import static com.fpt.capstone.tourism.constants.Constants.Message.SERVICE_CONTACT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ServiceContactServiceImpl implements ServiceContactService {
    private final ServiceContactRepository serviceContactRepository;
    private final ServiceContactMapper serviceContactMapper;

    @Override
    @Transactional
    public ServiceContactDTO createServiceContact(ServiceContactDTO serviceContactDTO) {
        if (serviceContactRepository.existsByPhoneNumber(serviceContactDTO.getPhoneNumber())) {
            throw BusinessException.of(DUPLICATE_SERVICE_CONTACT_PHONE);
        }
        ServiceContact serviceContact = serviceContactMapper.toEntity(serviceContactDTO);
        serviceContact.setDeleted(false);
        return serviceContactMapper.toDTO(serviceContactRepository.save(serviceContact));
    }

    @Override
    public ServiceContactDTO getServiceContactById(Long id) {
        ServiceContact serviceContact = serviceContactRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(SERVICE_CONTACT_NOT_FOUND));
        return serviceContactMapper.toDTO(serviceContact);
    }

    @Override
    public Page<ServiceContactDTO> getAllServiceContacts(Pageable pageable) {
        Page<ServiceContact> serviceContacts = serviceContactRepository.findAll(pageable);
        return serviceContacts.map(serviceContactMapper::toDTO);
    }


    @Override
    @Transactional
    public ServiceContactDTO updateServiceContact(Long id, ServiceContactDTO serviceContactDTO) {
        ServiceContact serviceContact = serviceContactRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(SERVICE_CONTACT_NOT_FOUND));
        serviceContact.setFullName(serviceContactDTO.getFullName());
        serviceContact.setPosition(serviceContactDTO.getPosition());
        serviceContact.setPhoneNumber(serviceContactDTO.getPhoneNumber());
        serviceContact.setEmail(serviceContactDTO.getEmail());
        serviceContact.setGender(serviceContactDTO.getGender());
        return serviceContactMapper.toDTO(serviceContactRepository.save(serviceContact));
    }

    @Override
    @Transactional
    public void deleteServiceContact(Long id) {
        ServiceContact serviceContact = serviceContactRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(SERVICE_CONTACT_NOT_FOUND));
        if (!serviceContact.isDeleted()) {
            serviceContact.setDeleted(true);
            serviceContactRepository.save(serviceContact);
        }
    }
}
