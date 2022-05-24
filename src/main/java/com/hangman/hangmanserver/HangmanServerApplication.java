package com.hangman.hangmanserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

import java.util.Comparator;
import java.util.Date;

@EnableConfigServer
@SpringBootApplication
public class HangmanServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HangmanServerApplication.class, args);
	}


}

