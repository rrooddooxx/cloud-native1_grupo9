package dev.bast.ecommerce.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"dev.bast.ecommerce"})
@EntityScan(basePackages = {"dev.bast.ecommerce.usuarios.model", "dev.bast.ecommerce.productos.model"})
@EnableJpaRepositories(basePackages = {"dev.bast.ecommerce.usuarios.repository", "dev.bast.ecommerce.productos.repository"})
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

}
