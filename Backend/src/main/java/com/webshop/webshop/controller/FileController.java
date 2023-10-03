package com.webshop.webshop.controller;

import com.webshop.webshop.DTO.FileDTO;
import com.webshop.webshop.model.File;
import com.webshop.webshop.repository.FileRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@RestController
public class FileController {
    private FileRepository fileRepository;

    public FileController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @PostMapping("/single/upload")
    FileDTO singleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        String name = StringUtils.cleanPath(file.getOriginalFilename());
        File imgFile = new File();
        imgFile.setFileName(name);
        imgFile.setDocFile(file.getBytes());

        fileRepository.save(imgFile);

        //returns http://localhost:8080/download/picture.png
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(name)
                .toUriString();

        String contentType = file.getContentType();

        FileDTO response = new FileDTO(name, contentType, url);

        return response;
    }

    @GetMapping("/download/{fileName}")
    ResponseEntity<byte[]> downloadSingleFile(@PathVariable String fileName, HttpServletRequest request) {

        File imgFile = fileRepository.findByFileName(fileName);

        String mimeType = request.getServletContext().getMimeType(imgFile.getFileName());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; fileName" + imgFile.getFileName())
                .body(imgFile.getDocFile());
    }

}
