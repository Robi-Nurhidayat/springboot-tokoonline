package com.programmerpagi.toko_online.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class ImageFileNameUtil {

    private ImageFileNameUtil(){}

    public static String generate(MultipartFile image) {

        String imageName = "";
        String uuid = UUID.randomUUID().toString();
        if ( image != null && !image.isEmpty()) {
            imageName = uuid + "_" + image.getOriginalFilename().replaceAll("\\s+", "_");
        }else {
            imageName = uuid + "_produk.jpg";
        }

        return imageName;
    }
}
