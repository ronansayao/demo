package com.example.demo;

import com.example.demo.application.controller.AnimalController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private AnimalController animalController;

	@Test
	void contextLoads() {
		assertThat(animalController).isNotNull();
	}

}
