package org.example.shedu.controller;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.File;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor

public class FileController {

    private final FileService fileService;



    @PostMapping(value = "/upload",consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse> uploadVideo(@RequestParam("file") MultipartFile file) {
        try {
            ApiResponse videoFile = fileService.saveFile(file);
            return ResponseEntity.status(videoFile.getStatus()).body(videoFile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable Long id) {
        try {
            Resource resource = fileService.loadFileAsResource(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(Files.probeContentType(Paths.get(resource.getURI()))))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping(value = "/update/{id}",consumes = {"multipart/form-data"})
    public ResponseEntity<File> updateFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            File updatedFile = fileService.updateFile(id, file);
            return ResponseEntity.ok(updatedFile);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteFile(@PathVariable Long id) {
        try {
            ApiResponse apiResponse = fileService.deleteFile(id);
            return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }
}
