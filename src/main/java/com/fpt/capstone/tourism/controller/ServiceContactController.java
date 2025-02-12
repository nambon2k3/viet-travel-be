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
@RequestMapping("${api.prefix}/CEO/service-contacts")
@RequiredArgsConstructor
public class ServiceContactController {

    private final ServiceContactService serviceContactService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ServiceContactManagementRequestDTO serviceContactManagementRequestDTO) {
        return ResponseEntity.ok(serviceContactService.create(serviceContactManagementRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceContactService.getById(id));
    }

    @GetMapping
    public ResponseEntity<GeneralResponse<PagingDTO<List<ServiceContactManagementRequestDTO>>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(serviceContactService.getAll(page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ServiceContactManagementRequestDTO serviceContactManagementRequestDTO) {
        return ResponseEntity.ok(serviceContactService.update(id, serviceContactManagementRequestDTO));
    }

    @DeleteMapping("/recover/{id}")
    public ResponseEntity<?> recover(@PathVariable Long id) {
        return ResponseEntity.ok(serviceContactService.recover(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(serviceContactService.delete(id));
    }
}
