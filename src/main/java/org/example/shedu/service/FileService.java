package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.File;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.repository.FileRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class FileService {
    private final FileRepository fileRepository;


    private static final Path root = Paths.get("");


    public ApiResponse saveFile(MultipartFile file) {
        String director = "file";

        Path directoryPath = root.resolve(director);
        Path filePath = directoryPath.resolve(file.getOriginalFilename());

        long fileId;
        try {
            // Create directories if they don't exist
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // Copy file to the target location
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Save file details to the database
            File videoFile = new File();
            videoFile.setFileName(file.getOriginalFilename());
            videoFile.setFilepath(filePath.toString());
            File save = fileRepository.save(videoFile);
            fileId = save.getId();

        } catch (IOException e) {
            // Log the exception with a detailed message
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        return new ApiResponse("Success", 200, fileId);
    }

//    public String checkingAttachmentType(MultipartFile file) {
//        String filename = file.getOriginalFilename();
//        if (filename == null) {
//            return null;
//        }
//
//        if (filename.endsWith(".mp4") || filename.endsWith(".avi") || filename.endsWith(".mkv")) {
//            return "video";
//        } else if (filename.endsWith(".png") || filename.endsWith(".jpg") || filename.endsWith(".jpeg")|| filename.endsWith(".webp")) {
//            return "img";
//        } else if (filename.endsWith(".pdf"))
//            return "pdf";
//
//        return null;
//    }


    //    GetFile uchun
    public Resource loadFileAsResource(Long id) throws MalformedURLException {
        Optional<File> videoFileOptional = fileRepository.findById(id);
        if (videoFileOptional.isPresent()) {
            Path filePath = Paths.get(videoFileOptional.get().getFilepath()).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            }
        }
        return null;
    }


    //    update
    public File updateFile(Long id, MultipartFile file) throws IOException {
        Optional<File> existingVideoFile = fileRepository.findById(id);
        if (existingVideoFile.isPresent()) {
            File videoFile = existingVideoFile.get();
            Path oldFilePath = Paths.get(videoFile.getFilepath());
            Files.deleteIfExists(oldFilePath);

            String filename = file.getOriginalFilename();
            Path uploadPath = Path.of("file", filename);


            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            videoFile.setFileName(filename);
            videoFile.setFilepath(filePath.toString());

            return fileRepository.save(videoFile);
        } else {
            throw new IOException("File not found");
        }
    }


    //delete file
    public ApiResponse deleteFile(Long id) throws IOException {
        Optional<File> existingVideoFile = fileRepository.findById(id);
        if (existingVideoFile.isPresent()) {
            File videoFile = existingVideoFile.get();
            Path filePath = Paths.get(videoFile.getFilepath());
            Files.deleteIfExists(filePath);
            fileRepository.delete(videoFile);
            return new ApiResponse("Successfully deleted",200);
        } else {
            throw new IOException("File not found");
        }
    }
}
