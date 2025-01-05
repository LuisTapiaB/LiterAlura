package com.luistapia.literalura.repository;

import com.luistapia.literalura.model.Autor;
import com.luistapia.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM libro WHERE idioma ILIKE :idioma")
    List<Libro> seriesPorIdioma(String idioma);
}
