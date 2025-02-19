package com.fpt.capstone.tourism.controller;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.request.ServiceContactManagementRequestDTO;
import com.fpt.capstone.tourism.dto.response.PagingDTO;
import com.fpt.capstone.tourism.dto.response.ServiceContactManagementResponseDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.model.ServiceProvider;
import com.fpt.capstone.tourism.model.User;
import com.fpt.capstone.tourism.repository.ServiceProviderRepository;
import com.fpt.capstone.tourism.repository.UserRepository;
import com.fpt.capstone.tourism.service.ServiceContactService;
import com.fpt.capstone.tourism.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fpt.capstone.tourism.constants.Constants.Message.*;
import static com.fpt.capstone.tourism.constants.Constants.UserExceptionInformation.*;

@RestController
@RequestMapping("/service-provider/service-contacts")
@RequiredArgsConstructor
public class ServiceContactController {

    private final ServiceContactService serviceContactService;
    private final UserRepository userRepository;
    private final ServiceProviderRepository serviceProviderRepository;

    @GetMapping("/details/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceContactService.getServiceContactById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<GeneralResponse<PagingDTO<List<ServiceContactManagementResponseDTO>>>> getAllServiceContacts(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean isDeleted,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "desc") String sortDirection) {
        try {
            if (userDetails == null) {
                throw BusinessException.of(USER_NOT_AUTHENTICATED);
            }

            User user = userRepository.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> BusinessException.of(USER_NOT_FOUND));
            // Get ServiceProvider using userId
            ServiceProvider serviceProvider = serviceProviderRepository.findByUserId(user.getId())
                    .orElseThrow(() -> BusinessException.of(HttpStatus.NOT_FOUND, SERVICE_CONTACTS_NOT_EXITS));

            Long loggedInProviderId = serviceProvider.getId();
            return ResponseEntity.ok(serviceContactService.getAllServiceContacts(
                    page, size, keyword, isDeleted, sortField, sortDirection, loggedInProviderId));
        } catch (Exception e) {
            throw BusinessException.of(SERVICE_CONTACTS_NOT_EXITS, e);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal UserDetails userDetails,
                                    @Valid @RequestBody ServiceContactManagementRequestDTO requestDTO) {
        Long providerId = getLoggedInServiceProviderId(userDetails);
        return ResponseEntity.ok(serviceContactService.createServiceContact(requestDTO, providerId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@AuthenticationPrincipal UserDetails userDetails,
                                    @PathVariable Long id,
                                    @Valid @RequestBody ServiceContactManagementRequestDTO requestDTO) {
        Long providerId = getLoggedInServiceProviderId(userDetails);
        return ResponseEntity.ok(serviceContactService.updateServiceContact(id, requestDTO, providerId));
    }

    @PostMapping("/change-status/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,@RequestParam boolean isDeleted) {
        return ResponseEntity.ok(serviceContactService.deleteServiceContact(id,isDeleted));
    }

    private Long getLoggedInServiceProviderId(UserDetails userDetails) {
        if (userDetails == null) {
            throw BusinessException.of(USER_NOT_AUTHENTICATED);
        }
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> BusinessException.of(USER_NOT_FOUND));

        ServiceProvider serviceProvider = serviceProviderRepository.findByUserId(user.getId())
                .orElseThrow(() -> BusinessException.of(SERVICE_PROVIDER_NOT_FOUND));

        return serviceProvider.getId();
    }

}
