package com.fpt.capstone.tourism.controller;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/{roleName}/{featureName}/upload/{userId}")
@RequiredArgsConstructor
public class FileUploadController {
    private final CloudinaryService cloudinaryService;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFiles(@RequestParam("files") MultipartFile[] files,
                                         @RequestParam(value = "folder", required = false, defaultValue = "general") String folder,
                                         @PathVariable("roleName") String roleName,
                                         @PathVariable("featureName") String featureName,
                                         @PathVariable("userId") Long userId) {
        List<String> fileUrls = cloudinaryService.uploadFiles(files, folder, userId);
        return ResponseEntity.ok(new GeneralResponse<>(HttpStatus.OK.value(), "Files uploaded successfully", fileUrls));
    }
}
