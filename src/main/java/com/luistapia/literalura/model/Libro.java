package com.luistapia.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(nullable = false)
    private String titulo;
    private List<String> tematicas;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;
    private String idioma;
    private Integer descargas;

    public Libro(){}

    public Libro(DatosLibro d, Autor autor) {
        this.titulo = d.titulo();
        this.tematicas = d.tematicas();
        this.idioma = d.idiomas().stream().findFirst().orElse(null);
        this.descargas = d.descargas();
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getTematicas() {
        return tematicas;
    }

    public void setTematicas(List<String> tematicas) {
        this.tematicas = tematicas;
    }
    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return  "* " + this.getTitulo() + ", [" + this.getIdioma()+"]" + ", ( autor: "+ this.getAutor().getNombre()+")";
    }
}
