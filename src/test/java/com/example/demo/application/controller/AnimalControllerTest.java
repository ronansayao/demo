package com.example.demo.application.controller;

import com.example.demo.domain.Animal;
import com.example.demo.domain.Status;
import com.example.demo.domain.dto.AnimalDTO;
import com.example.demo.domain.dto.AnimalStatusDTO;
import com.example.demo.domain.dto.CategoryDTO;
import com.example.demo.domain.dto.FilterParams;
import com.example.demo.utils.PaginatedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnimalControllerTest {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @BeforeEach
    public void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());

    }

    @Test
    public void createNewAnimal() {
        String createAnimalUrl = "http://localhost:" + port + "/animal";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("image", new FileSystemResource("src/test/resources/dog.png"));
        body.add("animal", getAnimalDTO());
        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);

        Animal animal = restTemplate.postForObject(createAnimalUrl, requestEntity, Animal.class);
        assertThat(animal.getId()).isNotNull();
        assertThat(animal.getName()).isEqualTo("Lili");
        assertThat(animal.getStatus()).isEqualTo(Status.AVAILABLE);
        assertThat(animal.getCreationDate()).isEqualTo(LocalDate.now());
        assertThat(animal.getImageURL()).isEqualTo("http://localhost:" + port + "/animal/" + animal.getId() + "/image/dog.png");
    }

    @Test
    public void updateAnimalStatus() {

        String createAnimalUrl = "http://localhost:" + port + "/animal";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("image", new FileSystemResource("src/test/resources/dog1.png"));
        body.add("animal", getAnimalDTOUpdateStatus());
        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);

        Animal animal = restTemplate.postForObject(createAnimalUrl, requestEntity, Animal.class);

        Animal animalUpdated = restTemplate.patchForObject(createAnimalUrl + "/status", new AnimalStatusDTO(animal.getId(), "adotado"), Animal.class);
        assertThat(animalUpdated.getStatus()).isEqualTo(Status.ADOPTED);

    }

    @Test
    public void findAnimalNotFound() {
        String createAnimalUrl = "http://localhost:" + port + "/animal";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        FilterParams filterParams = new FilterParams();
        filterParams.setTerm("xpto");
        filterParams.setPage(0);
        filterParams.setSize(10);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("filterParams", filterParams);

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);
        ParameterizedTypeReference<PaginatedResponse<Animal>> responseType = new ParameterizedTypeReference<>() { };

        Page<Animal> animals = restTemplate.exchange(URI.create(createAnimalUrl),
                HttpMethod.GET, requestEntity, responseType).getBody();

        assertThat(animals.getContent().size()).isEqualTo(0L);

    }

    private void createAnimal(String createAnimalUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("image", new FileSystemResource("src/test/resources/dog1.png"));
        body.add("animal", getAnimalDTOUpdateStatus());
        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);

        restTemplate.postForObject(createAnimalUrl, requestEntity, Animal.class);

    }

    @Test
    public void getImageFromAnimal() {

        String baseAnimalUrl = "http://localhost:" + port + "/animal";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        FilterParams filterParams = new FilterParams();
        filterParams.setTerm("Meg");
        filterParams.setPage(0);
        filterParams.setSize(10);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("filterParams", filterParams);
        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);

        ParameterizedTypeReference<PaginatedResponse<Animal>> responseType = new ParameterizedTypeReference<>() { };

        Page<Animal> animals = restTemplate.exchange(URI.create(baseAnimalUrl),
                HttpMethod.GET, requestEntity, responseType).getBody();

        byte[] file = restTemplate.getForObject(URI.create(animals.getContent().get(0).getImageURL()), byte[].class);

        assertThat(file).isNotNull();

    }

    @Test
    public void findAnimalFound() {

        String createAnimalUrl = "http://localhost:" + port + "/animal";

        createAnimal(createAnimalUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        FilterParams filterParams = new FilterParams();
        filterParams.setTerm("Meg");
        filterParams.setPage(0);
        filterParams.setSize(10);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("filterParams", filterParams);

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);
        ParameterizedTypeReference<PaginatedResponse<Animal>> responseType = new ParameterizedTypeReference<>() { };

        Page<Animal> animals = restTemplate.exchange(URI.create(createAnimalUrl),
                HttpMethod.GET, requestEntity, responseType).getBody();

        assertThat(animals.getContent().size()).isEqualTo(1L);

    }

    private AnimalDTO getAnimalDTO() {
        return new AnimalDTO("Lili", "Pincher is a type of dog developed originally as ratters on farms and for fighting or guarding, although today they are most often kept as pets. The breed is powerful and muscular with an elegant appearance. It originated from Germany.",
                new CategoryDTO(1L, "Dogs"), "disponivel", LocalDate.now());
    }

    private AnimalDTO getAnimalDTOUpdateStatus() {
        return new AnimalDTO("Meg", "Pincher is a type of dog developed originally as ratters on farms and for fighting or guarding, although today they are most often kept as pets. The breed is powerful and muscular with an elegant appearance. It originated from Germany.",
                new CategoryDTO(1L, "Dogs"), "disponivel", LocalDate.now());
    }

}

