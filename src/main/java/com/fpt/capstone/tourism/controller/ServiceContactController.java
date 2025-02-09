package com.fpt.capstone.tourism.controller;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.ServiceContactManagementRequestDTO;
import com.fpt.capstone.tourism.dto.response.PagingDTO;
import com.fpt.capstone.tourism.service.ServiceContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/CEO/service-contacts")
@RequiredArgsConstructor
public class ServiceContactController {

    private final ServiceContactService serviceContactService;

    @PostMapping
    public ResponseEntity<?> createServiceContact(@Valid @RequestBody ServiceContactManagementRequestDTO serviceContactManagementRequestDTO) {
        return ResponseEntity.ok(serviceContactService.createServiceContact(serviceContactManagementRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getServiceContactById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceContactService.getServiceContactById(id));
    }

    @GetMapping
    public ResponseEntity<GeneralResponse<PagingDTO<List<ServiceContactManagementRequestDTO>>>> getAllServiceContacts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(serviceContactService.getAllServiceContacts(page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateServiceContact(@PathVariable Long id, @Valid @RequestBody ServiceContactManagementRequestDTO serviceContactManagementRequestDTO) {
        return ResponseEntity.ok(serviceContactService.updateServiceContact(id, serviceContactManagementRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteServiceContact(@PathVariable Long id) {
        return ResponseEntity.ok(serviceContactService.deleteServiceContact(id));
    }
}
