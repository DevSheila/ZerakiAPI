package com.devsheila.ZerakiAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class ZerakiApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZerakiApiApplication.class, args);
	}

}
