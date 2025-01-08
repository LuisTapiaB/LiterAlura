package com.luistapia.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Integer anoNacimiento;
    private Integer anoMuerte;
    @Column(nullable = false, unique = true)
    private String nombre;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    public List<Libro> getLibros() {
        return libros;
    }

    public Autor(){}

    public Autor(DatosAutor autor){
        this.anoNacimiento = autor.anoNacimiento() != null ? autor.anoNacimiento() : 0;
        this.anoMuerte =autor.anoMuerte() != null ? autor.anoMuerte() : 0;
        this.nombre = autor.nombre();
    }

    public void addLibro(Libro libro){
        libros.add(libro);
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public Integer getAnoNacimiento() {
        return anoNacimiento;
    }

    public void setAnoNacimiento(Integer anoNacimiento) {
        this.anoNacimiento = anoNacimiento;
    }

    public Integer getAnoMuerte() {
        return anoMuerte;
    }

    public void setAnoMuerte(Integer anoMuerte) {
        this.anoMuerte = anoMuerte;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "* " + this.getNombre() + " (" +this.getAnoNacimiento()+" - "+this.getAnoMuerte()+") ("+ this.libros.size()+" libros)";
    }
}
