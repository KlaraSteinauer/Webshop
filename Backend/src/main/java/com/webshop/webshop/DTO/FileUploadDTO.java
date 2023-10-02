package com.webshop.webshop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadDTO {
    private String fileName;
    private String contentType;
    private String url;
}
