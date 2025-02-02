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

import java.util.HashMap;
import java.util.Map;

import static com.fpt.capstone.tourism.constants.Constants.Message.*;

@RestController
@RequestMapping("/api/v1/service-categories")
@RequiredArgsConstructor
public class ServiceCategoryController {

    private final ServiceCategoryService serviceCategoryService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createServiceCategory(@Valid @RequestBody ServiceCategoryDTO serviceCategoryDTO) {
        ServiceCategoryDTO createdServiceCategory = serviceCategoryService.createServiceCategory(serviceCategoryDTO);
        //return new ResponseEntity<>(createdServiceCategory, HttpStatus.CREATED);
        Map<String, Object> response = new HashMap<>();
        response.put("message", CATEGORY_CREATED_SUCCESS_MESSAGE);
        response.put("data", createdServiceCategory);

        return ResponseEntity.status(201).body(response);
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
    public ResponseEntity<Map<String, Object>> updateServiceCategory(@PathVariable Long id, @Valid @RequestBody ServiceCategoryDTO serviceCategoryDTO) {
        ServiceCategoryDTO updatedServiceCategory = serviceCategoryService.updateServiceCategory(id, serviceCategoryDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("message", CATEGORY_UPDATED_SUCCESS_MESSAGE);
        response.put("data", updatedServiceCategory);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteServiceCategory(@PathVariable Long id) {
        serviceCategoryService.deleteServiceCategory(id);
        return ResponseEntity.ok(CATEGORY_DELETED_SUCCESS_MESSAGE);
    }

    @GetMapping
    public ResponseEntity<Page<ServiceCategoryDTO>> filterServiceCategories(
            @RequestParam(required = false) String status,
            Pageable pageable) {
        Page<ServiceCategoryDTO> serviceCategories = serviceCategoryService.filterServiceCategories(status, pageable);
        return ResponseEntity.ok(serviceCategories);
    }

}
