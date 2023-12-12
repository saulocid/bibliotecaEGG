package com.egg.biblioteca.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.egg.biblioteca.entities.Autor;
import com.egg.biblioteca.entities.Editorial;
import com.egg.biblioteca.entities.Libro;
import com.egg.biblioteca.exceptions.MyException;
import com.egg.biblioteca.services.AutorService;
import com.egg.biblioteca.services.EditorialService;
import com.egg.biblioteca.services.LibroService;

@Controller
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    private LibroService libroService;
    @Autowired
    private EditorialService editorialService;
    @Autowired
    private AutorService autorService;

    @GetMapping("/registrar")
    public String registrar(ModelMap model) {
        List<Autor> autores = autorService.listarAutores();
        List<Editorial> editoriales = editorialService.listarEditoriales();
        model.addAttribute("autores", autores);
        model.addAttribute("editoriales", editoriales);
        return "libroForm";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo,
            @RequestParam(required = false) Integer ejemplares, @RequestParam String idAutor,
            @RequestParam String idEditorial, ModelMap model) {
        try {
            System.out.println("El título es: " + titulo);
            libroService.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            model.put("exito", "El libro se cargó correctamente");
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            List<Autor> autores = autorService.listarAutores();
            List<Editorial> editoriales = editorialService.listarEditoriales();
            model.addAttribute("autores", autores);
            model.addAttribute("editoriales", editoriales);
            return "libroForm";
        }
        return "index";
    }

    @GetMapping("/lista")
    public String listar(ModelMap model) {
        List<Libro> libros = libroService.listarLibros();
        model.addAttribute("libros", libros);
        return "libroList";
    }

    @GetMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, ModelMap model) {
        model.addAttribute("libro", libroService.libro(isbn));
        return "libroChange";
    }

    @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, String nombre, @RequestParam String titulo,
            @RequestParam(required = false) Integer ejemplares, @RequestParam String idAutor,
            @RequestParam String idEditorial, ModelMap model) {
        try {
            libroService.modificarLibro(isbn, titulo, idAutor, idEditorial, ejemplares);
            List<Libro> libros = libroService.listarLibros();
            model.addAttribute("libros", libros);
            return "libroList";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            model.addAttribute("libro", libroService.libro(isbn));
            return "libroChange";
        }
    }

}
