package com.example.demo.domain.ports.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface FileAdapterPort {
    void saveFile(UUID animalId, MultipartFile imageFile) throws IOException;

    byte[] getFile(UUID animalId, String filename);

}
