package com.luistapia.literalura.service;

import com.luistapia.literalura.model.Autor;
import com.luistapia.literalura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> buscarTodosLosAutores() {
        return autorRepository.findAll();
    }
    public void saveAutor(Autor autor){
        autorRepository.save(autor);
    }
    public List<Autor> autoresVivos(int ano){
        return autorRepository.autoresVivos(ano);
    }
    public Optional<Autor> busquedaAutorPorNombre(String nombre) {
        return autorRepository.autorBuscado(nombre);
    }
}
