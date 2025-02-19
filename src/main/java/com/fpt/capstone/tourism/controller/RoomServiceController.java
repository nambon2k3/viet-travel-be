//package com.fpt.capstone.tourism.controller;
//
//import com.fpt.capstone.tourism.dto.common.GeneralResponse;
//import com.fpt.capstone.tourism.dto.request.RoomServiceRequestDTO;
//import com.fpt.capstone.tourism.dto.response.PagingDTO;
//import com.fpt.capstone.tourism.dto.response.RoomServiceResponseDTO;
//import com.fpt.capstone.tourism.exception.common.BusinessException;
//import com.fpt.capstone.tourism.model.ServiceProvider;
//import com.fpt.capstone.tourism.model.User;
//import com.fpt.capstone.tourism.repository.ServiceProviderRepository;
//import com.fpt.capstone.tourism.repository.UserRepository;
//import com.fpt.capstone.tourism.service.RoomService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//import static com.fpt.capstone.tourism.constants.Constants.Message.*;
//import static com.fpt.capstone.tourism.constants.Constants.UserExceptionInformation.*;
//
//@RestController
//@RequestMapping("/service-provider/service/room")
//@RequiredArgsConstructor
//
//public class RoomServiceController {
//    private final UserRepository userRepository;
//    private final ServiceProviderRepository serviceProviderRepository;
//    private final RoomService roomService;
//
//    @GetMapping("/details/{id}")
//    public ResponseEntity<?> getById(@PathVariable Long id) {
//        return ResponseEntity.ok(roomService.getById(id));
//    }
//
//    @GetMapping("/list")
//    public ResponseEntity<GeneralResponse<PagingDTO<List<RoomServiceResponseDTO>>>> getAll(
//            @AuthenticationPrincipal UserDetails userDetails,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(required = false) String keyword,
//            @RequestParam(required = false) Boolean isDeleted,
//            @RequestParam(defaultValue = "id") String sortField,
//            @RequestParam(defaultValue = "desc") String sortDirection) {
//        try {
//            if (userDetails == null) {
//                throw BusinessException.of(USER_NOT_AUTHENTICATED);
//            }
//
//            User user = userRepository.findByUsername(userDetails.getUsername())
//                    .orElseThrow(() -> BusinessException.of(USER_NOT_FOUND));
//            // Get ServiceProvider using userId
//            ServiceProvider serviceProvider = serviceProviderRepository.findByUserId(user.getId())
//                    .orElseThrow(() -> BusinessException.of(HttpStatus.NOT_FOUND, SERVICE_CONTACTS_NOT_EXITS));
//
//            Long loggedInProviderId = serviceProvider.getId();
//            return ResponseEntity.ok(roomService.getAll(
//                    page, size, keyword, isDeleted, sortField, sortDirection, loggedInProviderId));
//        } catch (Exception e) {
//            throw BusinessException.of(SERVICE_NOT_EXITS, e);
//        }
//    }
//
////    @PostMapping
////    public ResponseEntity<?> create(@Valid @RequestBody RoomServiceRequestDTO roomServiceRequestDTO) {
////        return ResponseEntity.ok(roomService.create(roomServiceRequestDTO));
////    }
////
////    @PutMapping("/update/{id}")
////    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody RoomServiceRequestDTO roomServiceRequestDTO) {
////        return ResponseEntity.ok(roomService.update(id, roomServiceRequestDTO));
////    }
////
////    @PostMapping("/change-status/{id}")
////    public ResponseEntity<?> delete(@PathVariable Long id,@RequestParam boolean isDeleted) {
////        return ResponseEntity.ok(roomService.delete(id,isDeleted));
////    }
//}
