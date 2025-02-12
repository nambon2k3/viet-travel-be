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

    @GetMapping("/details/{id}")
    public ResponseEntity<GeneralResponse<ServiceProviderDTO>> getDetail(@PathVariable Long id) {
        return ResponseEntity.ok(serviceProviderService.getServiceProviderById(id));
    }

//    @GetMapping
//    public ResponseEntity<Page<ServiceProviderDTO>> getAllServiceProviders(Pageable pageable) {
//        Page<ServiceProviderDTO> serviceProviders = serviceProviderService.getAllServiceProviders((java.awt.print.Pageable) pageable);
//        return ResponseEntity.ok(serviceProviders);
//    }
//
    @PutMapping("/update/{id}")
    public ResponseEntity<GeneralResponse<ServiceProviderDTO>> updateServiceProvider(@PathVariable Long id, @RequestBody ServiceProviderDTO serviceProviderDTO) {
        return ResponseEntity.ok(serviceProviderService.updateServiceProvider(id, serviceProviderDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GeneralResponse<ServiceProviderDTO>> deleteServiceProvider(@PathVariable Long id, @RequestParam boolean isDeleted) {
        return ResponseEntity.ok(serviceProviderService.deleteServiceProvider(id, isDeleted));
    }
}

