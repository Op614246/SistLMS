package com.integrador.sistlms.controller;

import com.integrador.sistlms.model.Curso;
import com.integrador.sistlms.repository.InscripcionRepository;
import com.integrador.sistlms.security.CustomUserDetails;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CursoController {

    private final InscripcionRepository inscripcionRepository;

    public CursoController(InscripcionRepository inscripcionRepository) {
        this.inscripcionRepository = inscripcionRepository;
    }

    @GetMapping("/cursos")
    public String cursos(Model model) {
        // Obtener al usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // Obtener los cursos asociados al usuario autenticado
        List<Curso> cursos = inscripcionRepository.findByUsuarioEmail(userDetails.getUsername())
                .stream()
                .map(inscripcion -> inscripcion.getCurso())
                .collect(Collectors.toList());

        // Crear un mapa para asociar cada curso con su terapeuta
        Map<Long, String> cursoTerapeutas = cursos.stream().collect(Collectors.toMap(
                Curso::getIdcurso,
                curso -> inscripcionRepository.findTerapeutaByCursoId(curso.getIdcurso())
                        .map(usuario -> usuario.getNombre() + " " + usuario.getApellido()) // Concatenar nombre y
                                                                                           // apellido
                        .orElse("No asignado") // Valor por defecto si no hay terapeuta asignado
        ));

        // Agregar los cursos al modelo para pasarlos al HTML
        model.addAttribute("cursos", cursos);
        model.addAttribute("cursoTerapeutas", cursoTerapeutas);

        return "Alumno/cursos";
    }

    @GetMapping("/verPerfil")
    public String perfil(Authentication authentication, Model model) {
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            model.addAttribute("nombre", userDetails.getNombre());
            model.addAttribute("apellido", userDetails.getApellido());
            model.addAttribute("roles", userDetails.getAuthorities());

        }
        return "Alumno/perfil";
    }

    @GetMapping("/verPerfilProf")
    public String perfilProf(Authentication authentication, Model model) {
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            model.addAttribute("nombre", userDetails.getNombre());
            model.addAttribute("apellido", userDetails.getApellido());
            model.addAttribute("roles", userDetails.getAuthorities());

        }
        return "Terapeuta/perfilProf";
    }
}
