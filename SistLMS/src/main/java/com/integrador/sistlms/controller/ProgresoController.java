package com.integrador.sistlms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ProgresoController {
    @GetMapping("/progreso")
    public String Progreso() {
        return "Alumno/progreso";
    }
    
}
