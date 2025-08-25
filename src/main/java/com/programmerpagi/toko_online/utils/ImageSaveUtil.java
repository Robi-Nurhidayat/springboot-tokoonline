package com.programmerpagi.toko_online.utils;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageSaveUtil {

    private ImageSaveUtil() {}

    public static void save(String uploadDir,MultipartFile image, String imageName) {

        System.out.println("blok save image jalan");
        if ( image != null && !image.isEmpty()) {
            Path uploadPath = Paths.get(uploadDir);
            Path targetPath = uploadPath.resolve(imageName);
            try {
                image.transferTo(targetPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
