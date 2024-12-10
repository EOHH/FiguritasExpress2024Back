package com.integrador.backend2.service.Impl;

import com.integrador.backend2.service.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    @Override
    public String uploadProfileImage(byte[] imageBytes, String userId) {
        String imagePath = UPLOAD_DIR + userId + "/profile_image.jpg";

        File directory = new File(UPLOAD_DIR + userId);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new RuntimeException("No se pudo crear el directorio de almacenamiento.");
        }

        Path path = Paths.get(imagePath);
        try {
            Files.write(path, imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar la imagen en el servidor: " + e.getMessage());
        }

        return imagePath;
    }
}

