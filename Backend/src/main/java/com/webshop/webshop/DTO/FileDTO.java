package com.webshop.webshop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {
    private String fileName;
    private String contentType;
    private String url;
}

