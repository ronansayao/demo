package com.example.demo.application.controller;

import com.example.demo.domain.Animal;
import com.example.demo.domain.dto.FilterParams;
import com.example.demo.utils.PaginatedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@Sql(scripts = "/scripts/search_input.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AnimalControllerSearchTest {
    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @BeforeEach
    public void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    public void findAnimalFoundByCategory() {

        String createAnimalUrl = "http://localhost:" + port + "/animal";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        FilterParams filterParams = new FilterParams();
        filterParams.setCategory(1L);
        filterParams.setPage(0);
        filterParams.setSize(10);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("filterParams", filterParams);

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);
        ParameterizedTypeReference<PaginatedResponse<Animal>> responseType = new ParameterizedTypeReference<>() {
        };

        Page<Animal> animals = restTemplate.exchange(URI.create(createAnimalUrl),
                HttpMethod.GET, requestEntity, responseType).getBody();

        assertThat(animals.getContent().size()).isEqualTo(5L);

    }

    @Test
    public void findAnimalFoundByTermDescription() {

        String createAnimalUrl = "http://localhost:" + port + "/animal";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        FilterParams filterParams = new FilterParams();
        filterParams.setTerm("dogkinds");
        filterParams.setPage(0);
        filterParams.setSize(10);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("filterParams", filterParams);

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);
        ParameterizedTypeReference<PaginatedResponse<Animal>> responseType = new ParameterizedTypeReference<>() {
        };

        Page<Animal> animals = restTemplate.exchange(URI.create(createAnimalUrl),
                HttpMethod.GET, requestEntity, responseType).getBody();

        assertThat(animals.getContent().size()).isEqualTo(2L);

    }

    @Test
    public void findAnimalFoundByTermName() {

        String createAnimalUrl = "http://localhost:" + port + "/animal";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        FilterParams filterParams = new FilterParams();
        filterParams.setTerm("Troica");
        filterParams.setPage(0);
        filterParams.setSize(10);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("filterParams", filterParams);

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);
        ParameterizedTypeReference<PaginatedResponse<Animal>> responseType = new ParameterizedTypeReference<>() {
        };

        Page<Animal> animals = restTemplate.exchange(URI.create(createAnimalUrl),
                HttpMethod.GET, requestEntity, responseType).getBody();

        assertThat(animals.getContent().size()).isEqualTo(1L);

    }

    @Test
    public void findAnimalFoundByStatus() {

        String createAnimalUrl = "http://localhost:" + port + "/animal";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        FilterParams filterParams = new FilterParams();
        filterParams.setStatus("disponivel");
        filterParams.setPage(0);
        filterParams.setSize(10);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("filterParams", filterParams);

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);
        ParameterizedTypeReference<PaginatedResponse<Animal>> responseType = new ParameterizedTypeReference<>() {
        };

        Page<Animal> animals = restTemplate.exchange(URI.create(createAnimalUrl),
                HttpMethod.GET, requestEntity, responseType).getBody();

        assertThat(animals.getContent().size()).isEqualTo(5L);

    }

    @Test
    public void findAnimalFoundByStatusAdopted() {

        String createAnimalUrl = "http://localhost:" + port + "/animal";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        FilterParams filterParams = new FilterParams();
        filterParams.setStatus("adotado");
        filterParams.setPage(0);
        filterParams.setSize(10);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("filterParams", filterParams);

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);
        ParameterizedTypeReference<PaginatedResponse<Animal>> responseType = new ParameterizedTypeReference<>() {
        };

        Page<Animal> animals = restTemplate.exchange(URI.create(createAnimalUrl),
                HttpMethod.GET, requestEntity, responseType).getBody();

        assertThat(animals.getContent().size()).isEqualTo(5L);

    }

    @Test
    public void findAnimalFoundByCreatedAt() {

        String createAnimalUrl = "http://localhost:" + port + "/animal";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        FilterParams filterParams = new FilterParams();
        filterParams.setCreatedAt(LocalDate.now().plusDays(1));
        filterParams.setPage(0);
        filterParams.setSize(10);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("filterParams", filterParams);

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);
        ParameterizedTypeReference<PaginatedResponse<Animal>> responseType = new ParameterizedTypeReference<>() {
        };

        Page<Animal> animals = restTemplate.exchange(URI.create(createAnimalUrl),
                HttpMethod.GET, requestEntity, responseType).getBody();

        assertThat(animals.getContent().size()).isEqualTo(1L);

    }

    @Test
    public void findAnimalFoundByAll() {

        String createAnimalUrl = "http://localhost:" + port + "/animal";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        FilterParams filterParams = new FilterParams();
        filterParams.setTerm("Lili");
        filterParams.setStatus("disponivel");
        filterParams.setCategory(1L);
        filterParams.setCreatedAt(LocalDate.now());
        filterParams.setPage(0);
        filterParams.setSize(10);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("filterParams", filterParams);

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);
        ParameterizedTypeReference<PaginatedResponse<Animal>> responseType = new ParameterizedTypeReference<>() {
        };

        Page<Animal> animals = restTemplate.exchange(URI.create(createAnimalUrl),
                HttpMethod.GET, requestEntity, responseType).getBody();

        assertThat(animals.getContent().size()).isEqualTo(1L);

    }
}
