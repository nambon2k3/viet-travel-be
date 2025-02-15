package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.dto.common.ServiceContactManagementRequestDTO;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.response.PagingDTO;
import com.fpt.capstone.tourism.dto.response.ServiceContactManagementResponseDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.helper.validator.Validator;
import com.fpt.capstone.tourism.mapper.ServiceContactMapper;
import com.fpt.capstone.tourism.model.ServiceContact;
import com.fpt.capstone.tourism.model.ServiceProvider;
import com.fpt.capstone.tourism.repository.ServiceContactRepository;
import com.fpt.capstone.tourism.repository.ServiceProviderRepository;
import com.fpt.capstone.tourism.service.ServiceContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.fpt.capstone.tourism.constants.Constants.Message.*;

@Service
@RequiredArgsConstructor
public class ServiceContactServiceImpl implements ServiceContactService {

    private final ServiceContactRepository serviceContactRepository;
    private final ServiceContactMapper serviceContactMapper;
    private final ServiceProviderRepository serviceProviderRepository;

    @Override
    @Transactional
    public GeneralResponse<ServiceContactManagementResponseDTO> createServiceContact(ServiceContactManagementRequestDTO requestDTO) {
        try {
            // Validate required fields
            Validator.validateServiceContact(
                    requestDTO.getFullName(),
                    requestDTO.getPhoneNumber(),
                    requestDTO.getEmail(),
                    requestDTO.getPosition()
            );

            // Find service provider by name
            ServiceProvider serviceProvider = serviceProviderRepository.findByName(requestDTO.getServiceProviderName())
                    .orElseThrow(() -> BusinessException.of(HttpStatus.NOT_FOUND, SERVICE_PROVIDER_NOT_FOUND));

            // Check for duplicate phone number
            if (serviceContactRepository.existsByPhoneNumber(requestDTO.getPhoneNumber())) {
                throw BusinessException.of(HttpStatus.CONFLICT, DUPLICATE_SERVICE_CONTACT_PHONE);
            }

            // Check for duplicate email
            if (serviceContactRepository.existsByEmail(requestDTO.getEmail())) {
                throw BusinessException.of(HttpStatus.CONFLICT, DUPLICATE_SERVICE_CONTACT_EMAIL);
            }

            // Convert DTO to entity
            ServiceContact serviceContact = serviceContactMapper.toEntity(requestDTO);
            serviceContact.setDeleted(false);
            serviceContact.setServiceProvider(serviceProvider);
            ServiceContact savedServiceContact = serviceContactRepository.save(serviceContact);

            // Convert to response DTO
            ServiceContactManagementResponseDTO responseDTO = serviceContactMapper.toResponseDTO(savedServiceContact);
            responseDTO.setServiceProviderName(serviceProvider.getName());

            return GeneralResponse.of(responseDTO, CREATE_SERVICE_CONTACT_SUCCESS);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw BusinessException.of(HttpStatus.INTERNAL_SERVER_ERROR, CREATE_SERVICE_CONTACT_FAIL, e);
        }
    }

    @Override
    @Transactional
    public GeneralResponse<ServiceContactManagementResponseDTO> updateServiceContact(Long id, ServiceContactManagementRequestDTO requestDTO) {
        try {
            // Validate required fields
            Validator.validateServiceContact(
                    requestDTO.getFullName(),
                    requestDTO.getPhoneNumber(),
                    requestDTO.getEmail(),
                    requestDTO.getPosition()
            );

            // Find existing service contact
            ServiceContact serviceContact = serviceContactRepository.findById(id)
                    .orElseThrow(() -> BusinessException.of(HttpStatus.NOT_FOUND, SERVICE_CONTACT_NOT_FOUND));

            // Find service provider by name
            ServiceProvider serviceProvider = serviceProviderRepository.findByName(requestDTO.getServiceProviderName())
                    .orElseThrow(() -> BusinessException.of(HttpStatus.NOT_FOUND, SERVICE_PROVIDER_NOT_FOUND));

            // Check for duplicate phone number (excluding current contact)
            Optional<ServiceContact> existingPhoneContact = serviceContactRepository.findByPhoneNumber(requestDTO.getPhoneNumber());
            if (existingPhoneContact.isPresent() && !existingPhoneContact.get().getId().equals(id)) {
                throw BusinessException.of(HttpStatus.CONFLICT, DUPLICATE_SERVICE_CONTACT_PHONE);
            }

            // Check for duplicate email (excluding current contact)
            Optional<ServiceContact> existingEmailContact = serviceContactRepository.findByEmail(requestDTO.getEmail());
            if (existingEmailContact.isPresent() && !existingEmailContact.get().getId().equals(id)) {
                throw BusinessException.of(HttpStatus.CONFLICT, DUPLICATE_SERVICE_CONTACT_EMAIL);
            }

            // Update only changed fields
            if (!serviceContact.getFullName().equals(requestDTO.getFullName())) {
                serviceContact.setFullName(requestDTO.getFullName());
            }
            if (!serviceContact.getPhoneNumber().equals(requestDTO.getPhoneNumber())) {
                serviceContact.setPhoneNumber(requestDTO.getPhoneNumber());
            }
            if (!serviceContact.getEmail().equals(requestDTO.getEmail())) {
                serviceContact.setEmail(requestDTO.getEmail());
            }
            if (!serviceContact.getPosition().equals(requestDTO.getPosition())) {
                serviceContact.setPosition(requestDTO.getPosition());
            }
            if (serviceContact.getGender() != requestDTO.getGender()) {
                serviceContact.setGender(requestDTO.getGender());
            }

            // Update service provider
            serviceContact.setServiceProvider(serviceProvider);

            // Save changes
            ServiceContact updatedServiceContact = serviceContactRepository.save(serviceContact);

            // Convert to response DTO
            ServiceContactManagementResponseDTO responseDTO = serviceContactMapper.toResponseDTO(updatedServiceContact);
            responseDTO.setServiceProviderName(serviceProvider.getName());

            return GeneralResponse.of(responseDTO, UPDATE_SERVICE_CONTACT_SUCCESS);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw BusinessException.of(HttpStatus.INTERNAL_SERVER_ERROR, UPDATE_SERVICE_CONTACT_FAIL, e);
        }
    }

    @Override
    public GeneralResponse<?> getServiceContactById(Long id) {
        try {
            ServiceContact serviceContact = serviceContactRepository.findById(id)
                    .orElseThrow(() -> BusinessException.of(HttpStatus.NOT_FOUND, SERVICE_CONTACT_NOT_FOUND));

            // Convert entity to DTO
            ServiceContactManagementResponseDTO responseDTO = serviceContactMapper.toResponseDTO(serviceContact);
            // Set the service provider name
            if (serviceContact.getServiceProvider() != null) {
                responseDTO.setServiceProviderName(serviceContact.getServiceProvider().getName());
            }
            return GeneralResponse.of(responseDTO, GET_SERVICE_CONTACT_SUCCESS);
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            throw BusinessException.of(GET_SERVICE_CONTACT_FAIL, e);
        }
    }


    @Override
    public GeneralResponse<PagingDTO<List<ServiceContactManagementRequestDTO>>> getAllServiceContacts(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<ServiceContact> serviceContactPage = serviceContactRepository.findAll(pageable);

            List<ServiceContactManagementRequestDTO> serviceContacts = serviceContactPage.getContent().stream()
                    .map(serviceContact -> {
                        ServiceContactManagementRequestDTO dto = serviceContactMapper.toDTO(serviceContact);

                        if (serviceContact.getServiceProvider() != null) {
                            dto.setServiceProviderName(serviceContact.getServiceProvider().getName());
                        }
                        return dto;
                    })
                    .collect(Collectors.toList());

            PagingDTO<List<ServiceContactManagementRequestDTO>> pagingDTO = PagingDTO.<List<ServiceContactManagementRequestDTO>>builder()
                    .page(page)
                    .size(size)
                    .total(serviceContactPage.getTotalElements())
                    .items(serviceContacts)
                    .build();
            return GeneralResponse.of(pagingDTO, GET_ALL_SERVICE_CONTACTS_SUCCESS);
        } catch (Exception e) {
            throw BusinessException.of(GET_ALL_SERVICE_CONTACTS_FAIL, e);
        }
    }

    @Override
    @Transactional
    public GeneralResponse<?> deleteServiceContact(Long id, boolean isDeleted) {
        try {
            ServiceContact serviceContact = serviceContactRepository.findById(id)
                    .orElseThrow(() -> BusinessException.of(HttpStatus.NOT_FOUND, SERVICE_CONTACT_NOT_FOUND));
            serviceContact.setDeleted(isDeleted);
            serviceContactRepository.save(serviceContact);
            ServiceContactManagementResponseDTO responseDTO = serviceContactMapper.toResponseDTO(serviceContact);
            return GeneralResponse.of(responseDTO,DELETE_SERVICE_CONTACT_SUCCESS);
        } catch (Exception e) {
            throw BusinessException.of(DELETE_SERVICE_CONTACT_FAIL, e);
        }
    }
}
