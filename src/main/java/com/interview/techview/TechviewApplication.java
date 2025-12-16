package com.interview.techview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TechviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechviewApplication.class, args);
	}

}
