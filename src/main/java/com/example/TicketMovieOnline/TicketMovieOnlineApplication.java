package com.example.TicketMovieOnline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TicketMovieOnlineApplication {
	public static void main(String[] args) {
		SpringApplication.run(TicketMovieOnlineApplication.class, args);
	}
}
