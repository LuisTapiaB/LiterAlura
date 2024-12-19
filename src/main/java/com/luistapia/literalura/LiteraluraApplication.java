package com.luistapia.literalura;

import com.luistapia.literalura.main.Principal;
import com.luistapia.literalura.repository.AutorRepository;
import com.luistapia.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository repoLibro;
	@Autowired
	private AutorRepository repoAutor;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repoLibro,repoAutor );
		principal.muestraElMenu();
	}
}
