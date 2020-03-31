package com.payconiq.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockApplication {

	private static final Logger logger = LoggerFactory.getLogger(StockApplication.class);

	/**
	 * @param args
	 * Spring boot main application
	 */
	public static void main(String[] args) {
		logger.info("Inside application start");
		SpringApplication.run(StockApplication.class, args);
	}

}
