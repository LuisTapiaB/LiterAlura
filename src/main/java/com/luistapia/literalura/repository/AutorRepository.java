package com.luistapia.literalura.repository;

import com.luistapia.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor,Long> {
    @Query(nativeQuery = true,value = "SELECT * FROM autor WHERE ano_nacimiento <= :ano AND ano_muerte > :ano")
    List<Autor> autoresVivos(int ano);
    @Query(nativeQuery = true,value = "SELECT * FROM autor WHERE nombre ILIKE :busqueda")
    Autor autorBuscado(String busqueda);
}
