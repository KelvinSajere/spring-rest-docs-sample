package com.meistermeier.beer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.hateoas.hal.DefaultCurieProvider;

@SpringBootApplication
public class BeerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeerApplication.class, args);
	}

	@Bean
	public CurieProvider curieProvider() {
		return new DefaultCurieProvider("beerapi", new UriTemplate("/documentation/{rel}.html"));
	}
}
