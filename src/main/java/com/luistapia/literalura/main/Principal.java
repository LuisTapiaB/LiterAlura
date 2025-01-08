package com.luistapia.literalura.main;

import com.luistapia.literalura.model.*;
import com.luistapia.literalura.service.AutorService;
import com.luistapia.literalura.service.ConsumoAPI;
import com.luistapia.literalura.service.ConvierteDatos;
import com.luistapia.literalura.service.LibroService;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books";
    private ConvierteDatos conversor = new ConvierteDatos();
    private Libro libro;
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
             // System.out.println(e.getClass());
                System.out.println("Entrada no valida");
                opcion = -1;
            }
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarPorTitulo();
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
                    buscarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    public void buscarPorTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar y guardar:");
        String nombreLibro = teclado.nextLine();
        Optional<DatosLibro> datosLibro = buscarLibroEnApi(nombreLibro);
        datosLibro.ifPresentOrElse(this::procesarLibro, () -> System.out.println("Libro no encontrado"));
    }

    private Optional<DatosLibro> buscarLibroEnApi(String nombreLibro) {
        try {
            String encodedNombre = nombreLibro.replace(" ", "%20");
            String json = consumoApi.obtenerDatos(URL_BASE + "/?search=" + encodedNombre);
            DatosBusqueda datosBusqueda = conversor.stringToClass(json, DatosBusqueda.class);
            return datosBusqueda.resultados().stream().findFirst();
        } catch (Exception e) {
            System.err.println("Error al buscar en la API: " + e.getMessage());
            return Optional.empty();
        }
    }

    private void procesarLibro(DatosLibro datosLibro) {
        libroService.busquedaDeLibroPorTitulo(datosLibro.titulo()).ifPresentOrElse(
                libro -> System.out.println("Libro ya registrado: "),
                () -> tratarLibroNuevo(datosLibro)
        );
    }
    @Transactional
    private void tratarLibroNuevo(DatosLibro datosLibro) {
        Optional<DatosAutor> autorLibro = datosLibro.autores().stream().findFirst();

        if (autorLibro.isEmpty()) {
            System.out.println("No se encontraron autores para el libro");
            return;
        }
        Autor autor = autorService.busquedaAutorPorNombre(autorLibro.get().nombre())
                .orElseGet(() -> {
                    Autor nuevoAutor = new Autor(autorLibro.get());
                    autorService.saveAutor(nuevoAutor);
                    return nuevoAutor;
                });

        Libro libro = new Libro(datosLibro, autor);
        autor.addLibro(libro);
        libroService.saveLibro(libro);
        System.out.println("Libro guardado: " + libro);
    }

    public void buscarLibrosRegistrados() {
        libros = libroService.buscarTodosLosLibros();
        if (libros != null) {
            System.out.println("Libros Registrados:");
            libros.forEach(System.out::println);
        } else {System.out.println("no hay libros registrados");}
    }
    public void buscarAutoresRegistrados() {
        autores = autorService.buscarTodosLosAutores();
        if (autores != null) {
            System.out.println("Autores Registrados:");
            autores.forEach(System.out::println);
        } else {
            System.out.println("no hay autores registrados");
        }
    }

    private void buscarLibrosPorIdioma() {
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
                    idioma = "tl";
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
                libros.forEach(System.out::println);
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
            System.out.println("Entrada no valida");
        }
        teclado.nextLine();
        System.out.println(autores.size() + " Resultados: ");
        autores.forEach(System.out::println);
    }
}