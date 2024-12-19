package com.luistapia.literalura.service;

public interface iConvierteDatos {
    <T> T stringToClass(String json, Class<T> clase);
}
