package com.egg.biblioteca.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/")
public class ProtalController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

}
