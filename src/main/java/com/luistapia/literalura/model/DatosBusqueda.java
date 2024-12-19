package com.luistapia.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosBusqueda(
    @JsonAlias("count") Long cantidad,
    @JsonAlias("next") String siguiente,
    @JsonAlias("previous") String anterior,
    @JsonAlias("results") List<DatosLibro> resultados
    ){
}
