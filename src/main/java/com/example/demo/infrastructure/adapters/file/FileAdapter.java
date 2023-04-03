package com.example.demo.infrastructure.adapters.file;

import com.example.demo.domain.ports.file.FileAdapterPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileAdapter implements FileAdapterPort {

    @Value("${demo.images.folder}")
    private String externalFolder;

    @Override
    public void saveFile(UUID animalId, MultipartFile imageFile) throws IOException {
        String pathFile = externalFolder + "/" + animalId;
        Files.createDirectories(Paths.get(pathFile));
        imageFile.transferTo(new File(pathFile + "/" + imageFile.getOriginalFilename()));
    }

    @Override
    public byte[] getFile(UUID animalId, String filename)  {
        String pathFile = externalFolder + "/" + animalId + "/" +filename;
        File file = new File(pathFile);
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
