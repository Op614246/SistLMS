package com.integrador.sistlms.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import com.integrador.sistlms.model.Usuario;
import com.integrador.sistlms.repository.UsuarioRepository;

import java.security.Principal;

@Controller
public class PasswordController {


    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordController(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/actualizarContrasena")
    public String updatePassword() {
        return "Alumno/updtPass"; // Renderiza la página del formulario
    }

    @PostMapping("/actualizarContrasena")
    public String updatePassword(
            @RequestParam("old_pass") String oldPassword,
            @RequestParam("new_pass") String newPassword,
            @RequestParam("c_pass") String confirmPassword,
            Model model,
            Principal principal) {

        // Obtener el usuario autenticado
        String email = principal.getName(); // El nombre del usuario autenticado (email)
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar la contraseña actual
        if (!passwordEncoder.matches(oldPassword, usuario.getContrasena())) {
            model.addAttribute("error", "La contraseña actual es incorrecta.");
            return "Alumno/updtPass";
        }

        // Verificar que la nueva contraseña y la confirmación coincidan
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "La nueva contraseña y su confirmación no coinciden.");
            return "Alumno/updtPass";
        }

        // Actualizar la contraseña
        usuario.setContrasena(passwordEncoder.encode(newPassword));
        usuarioRepository.save(usuario);

        model.addAttribute("success", "Contraseña actualizada con éxito.");
        return "Alumno/updtPass";
    }

    @GetMapping("/actualizarContrasenaProf")
    public String updatePasswordProf() {
        return "Terapeuta/updtPass"; // Renderiza la página del formulario
    }

    @PostMapping("/actualizarContrasenaProf")
    public String updatePasswordProf(
            @RequestParam("old_pass") String oldPassword,
            @RequestParam("new_pass") String newPassword,
            @RequestParam("c_pass") String confirmPassword,
            Model model,
            Principal principal) {

        // Obtener el usuario autenticado
        String email = principal.getName(); // El nombre del usuario autenticado (email)
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar la contraseña actual
        if (!passwordEncoder.matches(oldPassword, usuario.getContrasena())) {
            model.addAttribute("error", "La contraseña actual es incorrecta.");
            return "Terapeuta/updtPass";
        }

        // Verificar que la nueva contraseña y la confirmación coincidan
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "La nueva contraseña y su confirmación no coinciden.");
            return "Terapeuta/updtPass";
        }

        // Actualizar la contraseña
        usuario.setContrasena(passwordEncoder.encode(newPassword));
        usuarioRepository.save(usuario);

        model.addAttribute("success", "Contraseña actualizada con éxito.");
        return "Terapeuta/updtPass";
    }
}


