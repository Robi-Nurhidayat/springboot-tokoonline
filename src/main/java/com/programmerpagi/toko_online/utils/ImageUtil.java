package com.programmerpagi.toko_online.utils;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public final class ImageUtil {

    private ImageUtil() {}

    public static String generateImageName(MultipartFile image) {

        String imageName = "";
        String uuid = UUID.randomUUID().toString();
        if ( image != null && !image.isEmpty()) {
            imageName = uuid + "_" + image.getOriginalFilename().replaceAll("\\s+", "_");
        }else {
            imageName = uuid + "_produk.jpg";
        }

        return imageName;
    }


    public static void saveImage(String uploadDir, MultipartFile image, String imageName) {

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

    public static void deleteImage(String uploadDir,  String imageName) {

        System.out.println("blok delete image jalan");

        Path imageUrl = Paths.get(uploadDir).resolve(imageName);

        if(Files.exists(imageUrl)) {
            try {
                Files.delete(imageUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
