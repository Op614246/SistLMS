package com.integrador.sistlms.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.integrador.sistlms.model.Curso;
import com.integrador.sistlms.model.Inscripcion;
import com.integrador.sistlms.repository.InscripcionRepository;
import com.integrador.sistlms.security.CustomUserDetails;
import com.integrador.sistlms.service.CursoService;

@Controller
public class HomeController {
    private final InscripcionRepository inscripcionRepository;

    public HomeController(InscripcionRepository inscripcionRepository) {
        this.inscripcionRepository = inscripcionRepository;
    }

    @Autowired
    private CursoService cursoService;

    @GetMapping("/")
    public String home(Model model) {
        // Obtener al usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // Obtener los cursos asociados al usuario autenticado
        List<Curso> cursos = inscripcionRepository.findByUsuarioEmail(userDetails.getUsername())
                .stream()
                .map(inscripcion -> inscripcion.getCurso())
                .collect(Collectors.toList());

        // Agregar los cursos al modelo para pasarlos al HTML
        model.addAttribute("cursos", cursos);
        return "Terapeuta/CursosProf";
    }

    @PostMapping("/registrarCurso")
    public String registrarCurso(@ModelAttribute Curso curso, @RequestParam("image") MultipartFile image) {

        curso.setDuracion(12);

        if (!image.isEmpty()) {
            try {
                // Define la carpeta donde guardar las imágenes
                String uploadsDir = "./uploads/";
                Path uploadPath = Paths.get(uploadsDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Genera un nombre único para evitar colisiones
                String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(image.getInputStream(), filePath);

                // Establece la URL de la imagen en el curso
                curso.setImagenUrl("/uploads/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        cursoService.saveCurso(curso);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // Realiza la inscripción automáticamente
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setCurso(curso);
        inscripcion.setFechaInscripcion(LocalDate.now());
        inscripcion.setUsuario(userDetails.getUsuario()); // Aquí asumes que `CustomUserDetails` tiene un método
                                                          // `getUsuario`.
        inscripcion.setEstado("Activa");

        inscripcionRepository.save(inscripcion);

        return "redirect:/";
    }

}
