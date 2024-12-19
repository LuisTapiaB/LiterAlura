package com.luistapia.literalura.main;

import com.luistapia.literalura.model.*;
import com.luistapia.literalura.repository.AutorRepository;
import com.luistapia.literalura.repository.LibroRepository;
import com.luistapia.literalura.service.ConsumoAPI;
import com.luistapia.literalura.service.ConvierteDatos;

import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books";
    private ConvierteDatos conversor = new ConvierteDatos();
    private Libro libro;
    private Autor autor;
    private LibroRepository repositorio;
    private AutorRepository repoAutor;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.repositorio = libroRepository;
        this.repoAutor = autorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - BUSCAR LIBRO POR TITULO 
                    2 - LISTAR LIBROS REGISTRADOS
                    3 - LISTAR AUTORES REGISTRADOS
                    4 - LISTAR AUTORES VIVOS EN UN DETERMINADO AÑO
                    5 - LISTAR LIBROS POR IDIOMA
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarxTitulo();
                    break;
                case 2:
//                    buscarLibroRegistrados();
                    break;
                case 3:
//                    buscarAutoresRegistrados();
                    break;
                case 4:
//                    buscarAutoresVivos();
                    break;
                case 5:
//                    buscarLibrosxIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private Libro buscarxTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "/?search=" + nombreLibro.replace(" ", "%20"));
        DatosBusqueda datosBusqueda = conversor.stringToClass(json, DatosBusqueda.class);
        Busqueda busqueda = new Busqueda(datosBusqueda);
        Optional<DatosLibro> buscado = busqueda.getResultados().stream().findFirst();
        if (buscado.isPresent()){
            var libroBuscado = buscado.get();
            libro = new Libro(libroBuscado);
            autor = new Autor(libroBuscado.autores().stream().findFirst().get());
            repositorio.save(libro);
            repoAutor.save(autor);
            System.out.println("libro guardado: "+libro.getTitulo());
            System.out.println("libro guardado: "+autor.getNombre());
        } else {
            System.out.println("Libro no encontrado");
        }
        return libro;
    }
}