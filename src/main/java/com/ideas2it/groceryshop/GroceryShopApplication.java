package com.ideas2it.groceryshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * <p>
 *     Application starting point to run grocery application
 * </p>
 */
@EnableJpaAuditing
@SpringBootApplication
public class GroceryShopApplication {
	public static void main(String[] args) {
		SpringApplication.run(GroceryShopApplication.class, args);
	}

}
