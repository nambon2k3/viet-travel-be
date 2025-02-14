package com.fpt.capstone.tourism.controller;


import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.LocationDTO;
import com.fpt.capstone.tourism.dto.request.LocationRequestDTO;
import com.fpt.capstone.tourism.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/head-business/location")
public class LocationController {

    private final LocationService locationService;


    @PostMapping("/create")
    public ResponseEntity<GeneralResponse<LocationDTO>> create(@RequestBody LocationRequestDTO locationRequestDTO) {
        return ResponseEntity.ok(locationService.saveLocation(locationRequestDTO));
    }

//    @GetMapping("/details/{id}")
//    public ResponseEntity<GeneralResponse<LocationDTO>> detail(@PathVariable Long id) {
//        return ResponseEntity.ok(locationService.getLocationById(id));
//    }
//
//    @PostMapping("/update")
//    public ResponseEntity<GeneralResponse<LocationDTO>> update(@RequestBody LocationDTO locationDTO) {
//        return ResponseEntity.ok(locationService.saveLocation(locationDTO));
//    }


}
