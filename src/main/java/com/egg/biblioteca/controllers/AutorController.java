package com.egg.biblioteca.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.egg.biblioteca.entities.Autor;
import com.egg.biblioteca.exceptions.MyException;
import com.egg.biblioteca.services.AutorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping("/registrar") // localhost:8080/autor/registrar
    public String registrar() {
        return "autorForm";
    }

    @PostMapping("/registro") // localhost:8080/autor/registro
    public String registro(@RequestParam String nombre, ModelMap model) {
        try {
            autorService.crearAutor(nombre);
            model.put("exito", "El autor se cargó correctamente");
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "autorForm";
        }
        return "index";
    }

    @GetMapping("/lista")
    public String listar(ModelMap model) {
        List<Autor> autores = autorService.listarAutores();
        model.addAttribute("autores", autores);
        return "autorList";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap model) {
        model.addAttribute("autor", autorService.autor(id));
        return "autorChange";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap model) {
        try {
            autorService.modificarAutor(id, nombre);
            List<Autor> autores = autorService.listarAutores();
            model.addAttribute("autores", autores);
            return "autorList";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            model.addAttribute("autor", autorService.autor(id));
            return "autorChange";
        }
    }

}
