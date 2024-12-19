package com.luistapia.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    private List<String> personajes;
    @ManyToOne
    private Autor autor;
    private String idiomas;
    private Integer descargas;

    public Libro(DatosLibro d) {
        this.titulo = d.titulo();
        this.personajes = d.personajes();
        this.idiomas = d.idiomas().stream().findFirst().get();
        this.descargas = d.descargas();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(List<String> personajes) {
        this.personajes = personajes;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return  "titulo::'" + titulo + '\'' +
                ", personajes::" + personajes +
                ", idiomas::'" + idiomas + '\'' +
                ", descargas::" + descargas;
    }
}
