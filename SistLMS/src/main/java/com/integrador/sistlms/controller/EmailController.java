package com.integrador.sistlms.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.integrador.sistlms.model.Usuario;
import com.integrador.sistlms.repository.UsuarioRepository;

@Controller
public class EmailController {

    private final UsuarioRepository usuarioRepository;

    public EmailController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/actualizarCorreo")
    public String updateEmail() {
        return "Alumno/update"; // Renderiza la página del formulario
    }

    @PostMapping("/actualizarCorreo")
    public String updateEmail(
            @RequestParam("Cemail") String currentEmail,
            @RequestParam("Nemail") String newEmail,
            Model model,
            Principal principal) {

        // Obtener el usuario autenticado
        String authenticatedEmail = principal.getName(); // Email del usuario autenticado
        Usuario usuario = usuarioRepository.findByEmail(authenticatedEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar que el correo actual coincida con el registrado
        if (!usuario.getEmail().equals(currentEmail)) {
            model.addAttribute("error", "El correo actual no coincide con el registrado.");
            return "Alumno/update";
        }

        // Verificar si el nuevo correo ya está en uso
        if (usuarioRepository.findByEmail(newEmail).isPresent()) {
            model.addAttribute("error", "El nuevo correo ya está en uso.");
            return "Alumno/update";
        }

        // Actualizar el correo
        usuario.setEmail(newEmail);
        usuarioRepository.save(usuario);

        model.addAttribute("success", "Correo actualizado con éxito. Cierre y vuelva a iniciar sesion");
        return "Alumno/update";
    }

    @GetMapping("/actualizarCorreoProf")
    public String updateEmailProf() {
        return "Terapeuta/update"; // Renderiza la página del formulario
    }

    @PostMapping("/actualizarCorreoProf")
    public String updateEmailProf(
            @RequestParam("Cemail") String currentEmail,
            @RequestParam("Nemail") String newEmail,
            Model model,
            Principal principal) {

        // Obtener el usuario autenticado
        String authenticatedEmail = principal.getName(); // Email del usuario autenticado
        Usuario usuario = usuarioRepository.findByEmail(authenticatedEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar que el correo actual coincida con el registrado
        if (!usuario.getEmail().equals(currentEmail)) {
            model.addAttribute("error", "El correo actual no coincide con el registrado.");
            return "Terapeuta/update";
        }

        // Verificar si el nuevo correo ya está en uso
        if (usuarioRepository.findByEmail(newEmail).isPresent()) {
            model.addAttribute("error", "El nuevo correo ya está en uso.");
            return "Terapeuta/update";
        }

        // Actualizar el correo
        usuario.setEmail(newEmail);
        usuarioRepository.save(usuario);

        model.addAttribute("success", "Correo actualizado con éxito. Cierre y vuelva a iniciar sesion");
        return "Terapeuta/update";
    }
}
