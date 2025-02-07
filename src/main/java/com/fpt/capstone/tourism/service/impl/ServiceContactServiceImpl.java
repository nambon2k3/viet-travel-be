package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.constants.Constants;
import com.fpt.capstone.tourism.dto.common.ServiceContactDTO;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.response.PagingDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.mapper.ServiceContactMapper;
import com.fpt.capstone.tourism.model.ServiceContact;
import com.fpt.capstone.tourism.repository.ServiceContactRepository;
import com.fpt.capstone.tourism.service.ServiceContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.fpt.capstone.tourism.constants.Constants.Message.*;

@Service
@RequiredArgsConstructor
public class ServiceContactServiceImpl implements ServiceContactService {

    private final ServiceContactRepository serviceContactRepository;
    private final ServiceContactMapper serviceContactMapper;

    @Override
    @Transactional
    public GeneralResponse<?> createServiceContact(ServiceContactDTO serviceContactDTO) {
        try {
            // Validate duplicate phone number
            if (serviceContactRepository.existsByPhoneNumber(serviceContactDTO.getPhoneNumber())) {
                throw BusinessException.of(HttpStatus.CONFLICT, DUPLICATE_SERVICE_CONTACT_PHONE);
            }

            // Convert DTO to entity and save
            ServiceContact serviceContact = serviceContactMapper.toEntity(serviceContactDTO);
            serviceContact.setDeleted(false);
            ServiceContact savedServiceContact = serviceContactRepository.save(serviceContact);

            return GeneralResponse.of(serviceContactMapper.toDTO(savedServiceContact), CREATE_SERVICE_CONTACT_SUCCESS);
        }catch(BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw BusinessException.of(CREATE_SERVICE_CONTACT_FAIL, e);
        }
    }

    @Override
    @Transactional
    public GeneralResponse<?> updateServiceContact(Long id, ServiceContactDTO serviceContactDTO) {
        try {
            ServiceContact serviceContact = serviceContactRepository.findById(id)
                    .orElseThrow(() -> BusinessException.of(HttpStatus.NOT_FOUND, SERVICE_CONTACT_NOT_FOUND));

            // Update fields
            serviceContact.setFullName(serviceContactDTO.getFullName());
            serviceContact.setPosition(serviceContactDTO.getPosition());
            serviceContact.setPhoneNumber(serviceContactDTO.getPhoneNumber());
            serviceContact.setEmail(serviceContactDTO.getEmail());
            serviceContact.setGender(serviceContactDTO.getGender());

            ServiceContact updatedServiceContact = serviceContactRepository.save(serviceContact);
            return GeneralResponse.of(serviceContactMapper.toDTO(updatedServiceContact), UPDATE_SERVICE_CONTACT_SUCCESS);
        } catch (Exception e) {
            throw BusinessException.of(UPDATE_SERVICE_CONTACT_FAIL, e);
        }
    }

    @Override
    public GeneralResponse<?> getServiceContactById(Long id) {
        try {
            ServiceContact serviceContact = serviceContactRepository.findById(id)
                    .orElseThrow(() -> BusinessException.of(HttpStatus.NOT_FOUND, SERVICE_CONTACT_NOT_FOUND));
            return GeneralResponse.of(serviceContactMapper.toDTO(serviceContact), GET_SERVICE_CONTACT_SUCCESS);
        } catch (Exception e) {
            throw BusinessException.of(GET_SERVICE_CONTACT_FAIL, e);
        }
    }

    @Override
    public GeneralResponse<PagingDTO<List<ServiceContactDTO>>> getAllServiceContacts(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<ServiceContact> serviceContactPage = serviceContactRepository.findAll(pageable);

            List<ServiceContactDTO> serviceContacts = serviceContactPage.getContent().stream()
                    .map(serviceContactMapper::toDTO)
                    .collect(Collectors.toList());

            PagingDTO<List<ServiceContactDTO>> pagingDTO = PagingDTO.<List<ServiceContactDTO>>builder()
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
    public GeneralResponse<?> deleteServiceContact(Long id) {
        try {
            ServiceContact serviceContact = serviceContactRepository.findById(id)
                    .orElseThrow(() -> BusinessException.of(HttpStatus.NOT_FOUND, SERVICE_CONTACT_NOT_FOUND));
            if (!serviceContact.isDeleted()) {
                serviceContact.setDeleted(true);
                serviceContactRepository.save(serviceContact);
            }
            return GeneralResponse.of(DELETE_SERVICE_CONTACT_SUCCESS);
        } catch (Exception e) {
            throw BusinessException.of(DELETE_SERVICE_CONTACT_FAIL, e);
        }
    }
}
