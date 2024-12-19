package com.luistapia.literalura.model;

import java.util.List;

public class Busqueda {
    private List<DatosLibro> resultados;

    //Constructor predeterminado
    public Busqueda(){}

    //constructor
    public Busqueda(DatosBusqueda datos){
        this.resultados = datos.resultados();
    }

    // getters and setters
    public List<DatosLibro> getResultados() {
        return resultados;
    }

    @Override
    public String toString() {
        return "resultados=" + resultados;
    }
}
