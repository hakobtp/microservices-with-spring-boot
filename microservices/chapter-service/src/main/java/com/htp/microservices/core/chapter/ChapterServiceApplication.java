package com.htp.microservices.core.chapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.htp")
public class ChapterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChapterServiceApplication.class, args);
	}

}
