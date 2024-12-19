package com.luistapia.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Integer anoNacimiento;
    private Integer anoMuerte;
    @Column(unique = true)
    private String nombre;
    @OneToMany
    private List<Libro> libros;

    public Autor(){}

    public Autor(DatosAutor autor){
        this.anoNacimiento = autor.anoNacimiento();
        this.anoMuerte =autor.anoMuerte();
        this.nombre = autor.nombre();
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
        return "Autor: " + nombre +
                " anoNacimiento= " + anoNacimiento +
                ", anoMuerte= " + anoMuerte;
    }
}
