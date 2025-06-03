package com.jakem.nettracker;

import com.jakem.nettracker.plaid.PlaidProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(PlaidProperties.class)
@EnableScheduling
public class NettrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NettrackerApplication.class, args);
	}

}
