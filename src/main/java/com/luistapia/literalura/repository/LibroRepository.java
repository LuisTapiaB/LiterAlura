package com.luistapia.literalura.repository;

import com.luistapia.literalura.model.Autor;
import com.luistapia.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM libro WHERE idioma ILIKE :idioma")
    List<Libro> seriesPorIdioma(String idioma);
    @Query(nativeQuery = true,value = "SELECT * FROM libro WHERE titulo LIKE :titulo")
    Optional<Libro> BusquedaDeLibro(String titulo);
}
