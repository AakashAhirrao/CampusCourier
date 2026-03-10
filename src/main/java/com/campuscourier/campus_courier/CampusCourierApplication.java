package com.campuscourier.campus_courier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // turns on background task manager
public class CampusCourierApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampusCourierApplication.class, args);
	}

}
