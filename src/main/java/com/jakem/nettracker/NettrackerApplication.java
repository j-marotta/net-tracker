package com.jakem.nettracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NettrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NettrackerApplication.class, args);
	}

}
