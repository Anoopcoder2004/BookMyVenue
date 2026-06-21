package com.bookmyvenue.venue.controller;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/files")
public class FileController {

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/upload")
    public List<String> uploadFiles(@RequestParam("files") List<MultipartFile> files) {

        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile file : files) {

            try {
                // 🔹 unique filename
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

                // 🔹 file path
                Path path = Paths.get(UPLOAD_DIR + fileName);

                // 🔹 create folder if not exists
                Files.createDirectories(path.getParent());

                // 🔹 save file
                Files.write(path, file.getBytes());

                // 🔹 generate URL
                String url = "http://localhost:8080/uploads/" + fileName;

                imageUrls.add(url);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return imageUrls;
    }
}