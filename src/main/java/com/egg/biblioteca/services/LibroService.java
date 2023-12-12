package com.egg.biblioteca.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.egg.biblioteca.entities.Autor;
import com.egg.biblioteca.entities.Editorial;
import com.egg.biblioteca.entities.Libro;
import com.egg.biblioteca.exceptions.MyException;
import com.egg.biblioteca.repositories.AutorRepository;
import com.egg.biblioteca.repositories.EditorialRepository;
import com.egg.biblioteca.repositories.LibroRepository;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepositorio;
    @Autowired
    private AutorRepository autorRepositorio;
    @Autowired
    private EditorialRepository editorialRepositorio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial)
            throws MyException {

        validar(isbn, titulo, idAutor, idEditorial, ejemplares);

        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();
        Libro libro = new Libro();

        libro.setAlta(new Date());
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
    }

    public List<Libro> listarLibros() {
        List<Libro> libros = new ArrayList<>();
        libros = libroRepositorio.findAll();
        return libros;
    }

    @Transactional
    public void modificarLibro(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares)
            throws MyException {

        validar(isbn, titulo, idAutor, idEditorial, ejemplares);

        Optional<Libro> respuestaLibro = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

        Autor autor = new Autor();
        Editorial editorial = new Editorial();

        if (respuestaAutor.isPresent()) {
            autor = respuestaAutor.get();
        }

        if (respuestaEditorial.isPresent()) {
            editorial = respuestaEditorial.get();
        }

        if (respuestaLibro.isPresent()) {
            Libro libro = respuestaLibro.get();
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setEjemplares(ejemplares);
            libroRepositorio.save(libro);
        }

    }

    private void validar(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares)
            throws MyException {
        if (isbn == null) {
            throw new MyException("El isbn no puede ser nulo");
        }
        if (titulo == null || titulo.isEmpty()) {
            throw new MyException("El título no puede ser nulo o estar vacío");
        }
        if (ejemplares == null) {
            throw new MyException("Los ejemplares no pueden ser nulos");
        }
        if (idAutor == null || idAutor.isEmpty()) {
            throw new MyException("El autor no puede ser nulo o estar vacío");
        }
        if (idEditorial == null || idEditorial.isEmpty()) {
            throw new MyException("La editorial no puede ser nula o estar vacía");
        }
    }

    public Libro libro(Long isbn){
        Libro libro = libroRepositorio.getReferenceById(isbn);
        return libro;
    }

}
