package com.webshop.webshop.controller;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
public class FileController {

    private String IMAGE_PATH = "../Frontend/images/";

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

    @RequestMapping(value = "/file/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteFile(@RequestParam("file") String fileName) {
        try {
            File fileTodelete = new File(IMAGE_PATH + fileName);
            FileUtils.forceDelete(fileTodelete);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("File " + fileName + " was deleted.", HttpStatus.OK);
    }


    @Deprecated
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