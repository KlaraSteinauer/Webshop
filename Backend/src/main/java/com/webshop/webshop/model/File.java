package com.webshop.webshop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "images")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "filename")
    private String fileName;
    @Column(name = "docFile")
    @Lob
    private byte[] docFile;

}
