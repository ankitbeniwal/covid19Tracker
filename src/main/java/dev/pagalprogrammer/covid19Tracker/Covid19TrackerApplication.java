package dev.pagalprogrammer.covid19Tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Covid19TrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Covid19TrackerApplication.class, args);
	}

}
