package org.memestream.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MemestreamApiApplication {

	static void main(String[] args) {
		SpringApplication.run(MemestreamApiApplication.class, args);
	}

}
