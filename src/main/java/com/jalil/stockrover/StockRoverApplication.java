package com.jalil.stockrover;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class StockRoverApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockRoverApplication.class, args);
		log.info("HEY MAN ---------------");
	}

}
