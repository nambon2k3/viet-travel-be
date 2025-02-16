package com.fpt.capstone.tourism.controller;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.request.TourGuideRequestDTO;
import com.fpt.capstone.tourism.dto.request.UserCreationRequestDTO;
import com.fpt.capstone.tourism.dto.response.PagingDTO;
import com.fpt.capstone.tourism.dto.response.TourGuideResponseDTO;
import com.fpt.capstone.tourism.dto.response.UserFullInformationResponseDTO;
import com.fpt.capstone.tourism.service.TourGuideService;
import com.fpt.capstone.tourism.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/head-of-business/tour-guides")
@RequiredArgsConstructor
public class TourGuideController {

    private final TourGuideService tourGuideService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody TourGuideRequestDTO userDTO) {
        return ResponseEntity.ok(tourGuideService.create(userDTO));
    }
    @GetMapping()
    public ResponseEntity<GeneralResponse<PagingDTO<List<TourGuideResponseDTO>>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean isDeleted,
            @RequestParam(required = false) String roleName,
            @RequestParam(defaultValue = "createdAt") String sortField,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        return ResponseEntity.ok(tourGuideService.getAll(page, size, keyword, isDeleted, roleName, sortField, sortDirection));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(tourGuideService.getById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TourGuideRequestDTO userDTO) {
        return ResponseEntity.ok(tourGuideService.update(id, userDTO));
    }

    @PostMapping("/change-status/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestParam boolean isDeleted) {
        return ResponseEntity.ok(tourGuideService.delete(id, isDeleted));
    }
}
