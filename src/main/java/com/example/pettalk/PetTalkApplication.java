package com.example.pettalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PetTalkApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetTalkApplication.class, args);
	}

}
