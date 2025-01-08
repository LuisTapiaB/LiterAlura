package com.luistapia.literalura.service;

import com.luistapia.literalura.model.Libro;
import com.luistapia.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> buscarTodosLosLibros() {
        return libroRepository.findAll();
    }
    public void saveLibro(Libro libro){
        libroRepository.save(libro);
    }
    public List<Libro> buscarxTitulo(String idioma) {
        return libroRepository.seriesPorIdioma(idioma);
    }
    public Optional<Libro> busquedaDeLibroPorTitulo(String titulo){
        return libroRepository.BusquedaDeLibro(titulo);
    }
}
