package com.luistapia.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("birth_year")Integer anoNacimiento,
        @JsonAlias( "death_year") Integer anoMuerte,
        @JsonAlias("name") String nombre
        ) {
}
