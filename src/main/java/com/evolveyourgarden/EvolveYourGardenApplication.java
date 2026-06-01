package com.evolveyourgarden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class EvolveYourGardenApplication {
	public static void main(String[] args) {
		SpringApplication.run(EvolveYourGardenApplication.class, args);
	}
}
