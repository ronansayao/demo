package com.example.demo.infrastructure.settings;

import com.example.demo.domain.adapter.service.AnimalServiceImpl;
import com.example.demo.domain.ports.file.FileAdapterPort;
import com.example.demo.domain.ports.repositories.AnimalRepositoryPort;
import com.example.demo.domain.ports.interfaces.AnimalServicePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    AnimalServicePort animalService(AnimalRepositoryPort animalRepositoryPort, FileAdapterPort fileAdapterPort) {
        return new AnimalServiceImpl(animalRepositoryPort, fileAdapterPort);
    }

}
