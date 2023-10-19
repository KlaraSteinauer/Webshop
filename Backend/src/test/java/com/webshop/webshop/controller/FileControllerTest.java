package com.webshop.webshop.controller;

import com.webshop.webshop.enums.Role;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.repository.KimUserRepository;
import com.webshop.webshop.service.TokenService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ActiveProfiles("test")
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@ComponentScan("com.webshop.webshop.controller")
@ComponentScan("com.webshop.webshop.service")
@ComponentScan("com.webshop.webshop.repository")
@ComponentScan("com.webshop.webshop.config")
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FileController fileController;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private KimUserRepository kimUserRepository;

    private String token = "";

/*
    @BeforeEach
    void setup() throws GeneralJwtException, IOException {
        String tmpdir = Files.createTempDirectory("tmp").toFile().getAbsolutePath();
        targetDir = tmpdir;
        Path absolutePath = FileSystems.getDefault().getPath(IMAGE_FOLDER_PATH).toAbsolutePath();
        File oldDirectory = new File(new File(absolutePath.toString()).getCanonicalPath());
        File newDirectory = new File(tmpdir);
        if (oldDirectory.isDirectory()) {
            if (!newDirectory.exists()) {
                newDirectory.mkdir();
            }
            File[] children = oldDirectory.listFiles();
            for (int i=0; i < children.length; i++) {
                InputStream in = new FileInputStream(children[i]);
                String fileName = children[i].getName();
                File newFile = new File(newDirectory.getAbsolutePath() + "/" + fileName);
                OutputStream out = new FileOutputStream(newFile);
                byte[] buf = new byte[1024];
                int length;
                while ((length = in.read(buf)) > 0) {
                    out.write(buf, 0, length);
                }
            }
        }
        KimUser admin = new KimUser();
        admin.setUserId(2L);
        admin.setUserName("admin");
        admin.setUserPassword("adminPassword");
        admin.setUserEmail("admin@email.com");
        admin.setRole(Role.ADMIN);
        admin.setGender("male");
        admin.setFirstName("adminFirst");
        admin.setLastName("adminLast");
        token = tokenService.generateToken(admin);
        this.fileController.IMAGE_PATH = targetDir;
    }

 */

    @AfterEach
    void clean() {
        kimUserRepository.deleteAll();
    }
/*
    @Disabled
    @Test
    void deleteFile() throws Exception {
        String tmpdir = Files.createTempDirectory("tmp").toFile().getAbsolutePath();
        targetDir = tmpdir;
        Path absolutePath = FileSystems.getDefault().getPath(IMAGE_FOLDER_PATH).toAbsolutePath();
        File oldDirectory = new File(new File(absolutePath.toString()).getCanonicalPath());
        File newDirectory = new File(tmpdir);
        if (oldDirectory.isDirectory()) {
            if (!newDirectory.exists()) {
                newDirectory.mkdir();
            }
            File[] children = oldDirectory.listFiles();
            for (int i=0; i < children.length; i++) {
                InputStream in = new FileInputStream(children[i]);
                String fileName = children[i].getName();
                File newFile = new File(newDirectory.getAbsolutePath() + "/" + fileName);
                OutputStream out = new FileOutputStream(newFile);
                byte[] buf = new byte[1024];
                int length;
                while ((length = in.read(buf)) > 0) {
                    out.write(buf, 0, length);
                }
            }
        }
        KimUser admin = new KimUser();
        admin.setId(2L);
        admin.setUserName("admin");
        admin.setUserPassword("adminPassword");
        admin.setUserEmail("admin@email.com");
        admin.setRole(Role.ADMIN);
        admin.setGender("male");
        admin.setFirstName("adminFirst");
        admin.setLastName("adminLast");
        token = tokenService.generateToken(admin);
        this.fileController.IMAGE_PATH = targetDir;
        String fileNameToDelete = "play.png";
        String[] fileNames = new File(targetDir).list();
        long countBefore = Arrays.stream(fileNames)
                .filter(fn -> fn.equals(fileNameToDelete))
                .count();
        assertEquals(1, countBefore);
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/file/delete?file=" + fileNameToDelete)
                .header("Authorization", token))
                .andExpect(status().isOk());
        fileNames = new File(targetDir).list();
        long countAfter = Arrays.stream(fileNames)
                .filter(fn -> fn.equals(fileNameToDelete))
                .count();
        assertEquals(0, countAfter);


    }

 */

}
