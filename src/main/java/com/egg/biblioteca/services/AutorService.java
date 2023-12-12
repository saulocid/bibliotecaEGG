package com.egg.biblioteca.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.egg.biblioteca.entities.Autor;
import com.egg.biblioteca.exceptions.MyException;
import com.egg.biblioteca.repositories.AutorRepository;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepositorio;

    @Transactional
    public void crearAutor(String nombre) throws MyException {
        validar(nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autorRepositorio.save(autor);
    }

    public List<Autor> listarAutores() {
        List<Autor> autores = new ArrayList<>();
        autores = autorRepositorio.findAll();
        return autores;
    }

    @Transactional
    public void modificarAutor(String id, String nombre) throws MyException {
        validar(nombre);
        validar(id);
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        }

    }

    public Autor autor(String id){
        Autor autor = autorRepositorio.getReferenceById(id);
        return autor;
    }

    private void validar(String nombre) throws MyException {
        if (nombre.isEmpty()) {
            System.out.println("Error");
            throw new MyException("El dato no puede ser nulo");
        }
    }

}
