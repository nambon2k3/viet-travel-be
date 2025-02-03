package com.fpt.capstone.tourism.controller;

import com.fpt.capstone.tourism.dto.common.ServiceContactDTO;
import com.fpt.capstone.tourism.service.ServiceContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/service-contacts")
@RequiredArgsConstructor
public class ServiceContactController {

    private final ServiceContactService serviceContactService;

    @PostMapping
    public ResponseEntity<ServiceContactDTO> createServiceContact(@Valid @RequestBody ServiceContactDTO serviceContactDTO) {
        return new ResponseEntity<>(serviceContactService.createServiceContact(serviceContactDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceContactDTO> getServiceContactById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceContactService.getServiceContactById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ServiceContactDTO>> getAllServiceContacts(Pageable pageable) {
        return ResponseEntity.ok(serviceContactService.getAllServiceContacts(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceContactDTO> updateServiceContact(@PathVariable Long id, @Valid @RequestBody ServiceContactDTO serviceContactDTO) {
        return ResponseEntity.ok(serviceContactService.updateServiceContact(id, serviceContactDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceContact(@PathVariable Long id) {
        serviceContactService.deleteServiceContact(id);
        return ResponseEntity.noContent().build();
    }
}

