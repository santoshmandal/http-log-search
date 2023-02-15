package com.jpmorgan.log.aggregator.httplogsearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class HttpLogSearchApplication {
	Logger logger = LoggerFactory.getLogger(HttpLogSearchApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HttpLogSearchApplication.class, args);
	}

}
