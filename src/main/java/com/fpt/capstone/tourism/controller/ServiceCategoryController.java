package com.fpt.capstone.tourism.controller;

import com.fpt.capstone.tourism.dto.common.ServiceCategoryDTO;
import com.fpt.capstone.tourism.service.ServiceCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/service-categories")
@RequiredArgsConstructor
public class ServiceCategoryController {

    private final ServiceCategoryService serviceCategoryService;

    @PostMapping
    public ResponseEntity<ServiceCategoryDTO> createServiceCategory(@Valid @RequestBody ServiceCategoryDTO serviceCategoryDTO) {
        ServiceCategoryDTO createdServiceCategory = serviceCategoryService.createServiceCategory(serviceCategoryDTO);
        return new ResponseEntity<>(createdServiceCategory, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceCategoryDTO> getServiceCategoryById(@PathVariable Long id) {
        ServiceCategoryDTO serviceCategoryDTO = serviceCategoryService.getServiceCategoryById(id);
        return ResponseEntity.ok(serviceCategoryDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ServiceCategoryDTO>> getAllServiceCategories(Pageable pageable) {
        Page<ServiceCategoryDTO> serviceCategories = serviceCategoryService.getAllServiceCategories(pageable);
        return ResponseEntity.ok(serviceCategories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceCategoryDTO> updateServiceCategory(@PathVariable Long id, @Valid @RequestBody ServiceCategoryDTO serviceCategoryDTO) {
        ServiceCategoryDTO updatedServiceCategory = serviceCategoryService.updateServiceCategory(id, serviceCategoryDTO);
        return ResponseEntity.ok(updatedServiceCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceCategory(@PathVariable Long id) {
        serviceCategoryService.deleteServiceCategory(id);
        return ResponseEntity.noContent().build();
    }
}
