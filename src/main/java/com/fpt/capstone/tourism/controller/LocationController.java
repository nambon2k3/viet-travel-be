package com.fpt.capstone.tourism.controller;


import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.LocationDTO;
import com.fpt.capstone.tourism.dto.request.LocationRequestDTO;
import com.fpt.capstone.tourism.dto.response.PagingDTO;
import com.fpt.capstone.tourism.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/head-business/location")
public class LocationController {

    private final LocationService locationService;


    @PostMapping("/create")
    public ResponseEntity<GeneralResponse<LocationDTO>> create(@RequestBody LocationRequestDTO locationRequestDTO) {
        return ResponseEntity.ok(locationService.saveLocation(locationRequestDTO));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<GeneralResponse<LocationDTO>> detail(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getLocationById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<GeneralResponse<PagingDTO<List<LocationDTO>>>> getAll(@RequestParam(defaultValue = "0") int page,
                                                                                                @RequestParam(defaultValue = "10") int size,
                                                                                                @RequestParam(required = false) String keyword,
                                                                                                @RequestParam(required = false) Boolean isDeleted,
                                                                                                @RequestParam(defaultValue = "desc") String orderDate) {
        return ResponseEntity.ok(locationService.getAllLocation(page, size, keyword, isDeleted, orderDate));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GeneralResponse<LocationDTO>> update(@PathVariable Long id, @RequestBody LocationRequestDTO locationRequestDTO) {
        return ResponseEntity.ok(locationService.updateLocation(id, locationRequestDTO));
    }

    @DeleteMapping("/change-status/{id}")
    public ResponseEntity<GeneralResponse<LocationDTO>> delete(@PathVariable Long id,
                                                               @RequestParam boolean isDeleted) {
        return ResponseEntity.ok(locationService.deleteLocation(id, isDeleted));
    }
}
