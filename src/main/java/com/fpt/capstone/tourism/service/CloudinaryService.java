package com.fpt.capstone.tourism.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CloudinaryService {
    List<String> uploadFile(MultipartFile[] file);
    String uploadAvatar(MultipartFile file, Integer userId);
}
