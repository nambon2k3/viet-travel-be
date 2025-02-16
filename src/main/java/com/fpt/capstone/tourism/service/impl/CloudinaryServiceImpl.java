package com.fpt.capstone.tourism.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fpt.capstone.tourism.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;
    @Override
    public List<String> uploadFile(MultipartFile[] files) {
        List<String> uploadResult = new ArrayList<>();
        try {
            for(MultipartFile file: files){
                Map result  = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                        "folder", "test",
                        "use_filename", true,
                        "unique_filename", true,
                        "resource_type", "auto",
                        "chunk_size", 5 * 1024 * 1024,
                        "quality", "auto"
                ));
                uploadResult.add(result.get("url").toString());
            }

            return uploadResult;
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public String uploadAvatar(MultipartFile file, Long userId) {
        try {
                Map result  = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                        "folder", "avatar",
                        "use_filename", true,
                        "unique_filename", false,
                        "public_id", "avatar_" + userId,
                        "resource_type", "auto",
                        "chunk_size", 5 * 1024 * 1024,
                        "quality", "auto"
                ));
                return result.get("url").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> uploadFiles(MultipartFile[] files, String folder, Long userId) {
        List<String> uploadResult = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                Map<String, Object> uploadParams = ObjectUtils.asMap(
                        "folder", folder,
                        "use_filename", true,
                        "unique_filename", true,
                        "resource_type", "auto",
                        "chunk_size", 5 * 1024 * 1024,
                        "quality", "auto"
                );

                // If userId is provided, generate a specific public_id
                if (userId != null) {
                    uploadParams.put("public_id", folder + "_user_" + userId + "_" + file.getOriginalFilename());
                    uploadParams.put("unique_filename", false);
                }

                Map result = cloudinary.uploader().upload(file.getBytes(), uploadParams);
                uploadResult.add(result.get("url").toString());
            }
            return uploadResult;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload files", e);
        }
    }

}
