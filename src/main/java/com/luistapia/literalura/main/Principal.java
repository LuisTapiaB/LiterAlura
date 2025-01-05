package com.luistapia.literalura.main;

import com.luistapia.literalura.model.*;
import com.luistapia.literalura.service.AutorService;
import com.luistapia.literalura.service.ConsumoAPI;
import com.luistapia.literalura.service.ConvierteDatos;
import com.luistapia.literalura.service.LibroService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books";
    private ConvierteDatos conversor = new ConvierteDatos();
    private Libro libro;
    private Autor autor;
    private List<Libro> libros;
    private List<Autor> autores;
    private LibroService libroService;
    private AutorService autorService;

    public Principal(LibroService bookService, AutorService authorService) {
        this.libroService = bookService;
        this.autorService = authorService;
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
            try{
                opcion = teclado.nextInt();
            } catch (Exception e) {
                System.out.println(e.getClass());
                System.out.println("Entrada no valida");
                opcion = -1;
            }
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarxTitulo();
                    break;
                case 2:
                    buscarLibrosRegistrados();
                    break;
                case 3:
                    buscarAutoresRegistrados();
                    break;
                case 4:
                    buscarAutoresVivos();
                    break;
                case 5:
                    buscarLibrosxIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    public void buscarxTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar y guardar");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "/?search=" + nombreLibro.replace(" ", "%20"));
        DatosBusqueda datosBusqueda = conversor.stringToClass(json, DatosBusqueda.class);
        Busqueda busqueda = new Busqueda(datosBusqueda);
        Optional<DatosLibro> buscado = busqueda.getResultados().stream().findFirst();
        if (buscado.isPresent()){
            try {
                var libroBuscado = buscado.get();
                libro = new Libro(libroBuscado);
                libroService.saveLibro(libro);
                System.out.println("libro guardado: "+libro.getTitulo() + "["+libro.getIdioma()+"]");
            } catch (Exception e) {
                e.toString();
            }
        } else {
            System.out.println("Libro no encontrado");
        }
    }

    public void buscarLibrosRegistrados() {
        System.out.println("Libros Registrados----------------------------------------------------");
        libros = libroService.buscarTodosLosLibros();
        libros.forEach(s ->{
            System.out.println("* " + s.getTitulo() + ", [" + s.getIdioma()+"]");
        });
    }
    public void buscarAutoresRegistrados() {
        System.out.println("Autores Registrados---------------------------------------------------");
        autores = autorService.buscarTodosLosAutores();
        autores.forEach(s ->{
            System.out.println( "* " + s.getNombre() + " (" +s.getAnoNacimiento()+" - "+s.getAnoMuerte()+")");
        });
    }

    private void buscarLibrosxIdioma() {
        var opcion = -1;
        String idioma = null;
        while (opcion != 0) {
            var menu = """
                    1 - ESPAÑOL (ES)
                    2 - INGLES (EN)
                    3 - FRANCES (FR)
                    4 - ITALIANO (IT)
                                                     
                    0 - Salir
                    """;
            System.out.println(menu);
            try{
                opcion = teclado.nextInt();
            } catch (Exception e) {
                System.out.println(e.getClass());
                System.out.println("Entrada no valida");
                opcion = -1;
            }
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    idioma = "es";
                    break;
                case 2:
                    idioma = "en";
                    break;
                case 3:
                    idioma = "fr";
                    break;
                case 4:
                    idioma = "it";
                    break;
                case 0:
                    System.out.println("Cerrando...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
            if(opcion > 0 && opcion < 5 ) {
                libros = libroService.buscarxTitulo(idioma);
                System.out.println(libros.size() + " Resultados: ");
                libros.forEach(s ->{
                    System.out.println("* " + s.getTitulo());
                });
            }
        }
    }
    private void buscarAutoresVivos() {
        System.out.println("Ingrese el año a verificar autores vivos");
        int ano;
        try{
            ano = teclado.nextInt();
            autores = autorService.autoresVivos(ano);
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println("Entrada no valida");
        }
        teclado.nextLine();
        System.out.println(autores.size() + " Resultados: ");
        autores.forEach(s ->{
            System.out.println("* " + s.getNombre());
        });
    }
}