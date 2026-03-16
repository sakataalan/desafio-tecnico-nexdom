package br.com.nexdom.estoque_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EstoqueApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(EstoqueApiApplication.class, args);

		System.out.println("Esta funcionando!");
	}

}
