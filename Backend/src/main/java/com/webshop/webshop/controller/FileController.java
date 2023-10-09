package com.webshop.webshop.controller;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class FileController {

    public static String IMAGE_PATH = "../Frontend/images/";

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/upload", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        File convertFile = new File(IMAGE_PATH + file.getOriginalFilename());
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        fout.close();
        return new ResponseEntity<>("File upload successful!", HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/file/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteFile(@RequestParam("file") String fileName) {
        try {
            File fileToDelete = new File(IMAGE_PATH + fileName);
            FileUtils.forceDelete(fileToDelete);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("File " + fileName + " was deleted.", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "file/all", method = RequestMethod.GET)
    public ResponseEntity<Set<String>> getAllFiles() {
        Set<String> fileNames = Stream.of(new File(IMAGE_PATH).listFiles())
                    .filter(file -> !file.isDirectory())
                    .map(File::getName)
                    .collect(Collectors.toSet());
        return new ResponseEntity<>(fileNames, HttpStatus.OK);
        /*List<String> fileNames = new LinkedList<>();
        for (final Resource res : resources) {
            fileNames.add(res.getFilename());
        }
        return new ResponseEntity<>(fileNames, HttpStatus.OK);*/
    }

    @Deprecated
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity<Object> downloadFile(@RequestParam("file") String fileName) throws IOException {
        String filename = IMAGE_PATH + fileName;
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object>
                responseEntity = ResponseEntity.ok().headers(headers).contentLength(
                file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);
        return responseEntity;
    }

    @Deprecated
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/display", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType(@RequestParam("file") String fileName) throws IOException {
        InputStream in = getClass()
                .getResourceAsStream(IMAGE_PATH + fileName);
        System.out.println(IMAGE_PATH + fileName);
        System.out.println(in);
        return in.readAllBytes();
    }
}