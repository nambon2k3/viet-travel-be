package com.fpt.capstone.tourism.controller;

import com.fpt.capstone.tourism.dto.common.ChangableServiceProviderDTO;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.ServiceProviderDTO;
import com.fpt.capstone.tourism.service.ServiceProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/CEO/service-provider")
@RequiredArgsConstructor
public class ServiceProviderController {

    private final ServiceProviderService serviceProviderService;

    @PostMapping
    public ResponseEntity<GeneralResponse<ServiceProviderDTO>> create(@RequestBody ServiceProviderDTO serviceProviderDTO) {
        return ResponseEntity.ok(serviceProviderService.save(serviceProviderDTO));
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<ServiceProviderDTO> getServiceProviderById(@PathVariable Long id) {
//        ServiceProviderDTO serviceProviderDTO = serviceProviderService.getServiceProviderById(id);
//        return ResponseEntity.ok(serviceProviderDTO);
//    }
//
//    @GetMapping
//    public ResponseEntity<Page<ServiceProviderDTO>> getAllServiceProviders(Pageable pageable) {
//        Page<ServiceProviderDTO> serviceProviders = serviceProviderService.getAllServiceProviders((java.awt.print.Pageable) pageable);
//        return ResponseEntity.ok(serviceProviders);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ServiceProviderDTO> updateServiceProvider(@PathVariable Long id, @Valid @RequestBody ChangableServiceProviderDTO serviceProviderDTO) {
//        ServiceProviderDTO updatedServiceProvider = serviceProviderService.updateServiceProvider(id, serviceProviderDTO);
//        return ResponseEntity.ok(updatedServiceProvider);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteServiceProvider(@PathVariable Long id) {
//        serviceProviderService.deleteServiceProvider(id);
//        return ResponseEntity.noContent().build();
//    }
}

