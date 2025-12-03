package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
@ComponentScan({"com.example.demo","Controller","Service","Util"})
@SpringBootApplication
public class NyscApplication {

	public static void main(String[] args) {
		SpringApplication.run(NyscApplication.class, args);
	}

}
