package com.integrador.sistlms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.integrador.sistlms.model.Curso;
import com.integrador.sistlms.repository.CursoRepository;

@Controller
public class LoginController {

    @Autowired
    private CursoRepository cursoRepository;
    
    @GetMapping("/login")
    public String login(@RequestParam(value = "mensaje", required = false) String mensaje,
            Model model) {

        List<Curso> cursos = cursoRepository.findAll();
        model.addAttribute("cursos", cursos);
        
        if (mensaje != null) {
            model.addAttribute("mensaje", mensaje);
        }
        return "login";
    }

}
