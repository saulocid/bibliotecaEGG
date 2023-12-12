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
import com.egg.biblioteca.entities.Editorial;
import com.egg.biblioteca.exceptions.MyException;
import com.egg.biblioteca.services.EditorialService;

@Controller
@RequestMapping("/editorial")
public class EditorialController {
    
    @Autowired
    private EditorialService editorialService;

    @GetMapping("/registrar")
    public String registrar() {
        return "editorialForm";
    }

     @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap model) {
        try {
            editorialService.crearEditorial(nombre);
            model.put("exito","La editorial se cargo correctamente");
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "editorialForm";
        }
        return "index";
    }

    @GetMapping("/lista")
    public String listar(ModelMap model){
        List<Editorial> editorial = editorialService.listarEditoriales();
        model.addAttribute("editoriales", editorial);
        return "editorialList";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap model) {
        model.addAttribute("editorial", editorialService.editorial(id));
        return "editorialChange";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap model) {
        try {
            editorialService.modificarEditorial(id, nombre);
            List<Editorial> editoriales = editorialService.listarEditoriales();
            model.addAttribute("editoriales", editoriales);
            return "editorialList";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            model.addAttribute("editorial", editorialService.editorial(id));
            return "editorialChange";
        }
    }

}
