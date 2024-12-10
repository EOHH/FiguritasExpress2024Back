package com.integrador.backend2.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {
    String uploadProfileImage(byte[] imageBytes, String userId) throws IOException;
}
