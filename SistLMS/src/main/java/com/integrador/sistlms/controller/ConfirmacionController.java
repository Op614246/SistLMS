package com.integrador.sistlms.controller;

import com.integrador.sistlms.model.Usuario;
import com.integrador.sistlms.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConfirmacionController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/confirm")
    public String confirmarCorreo(@RequestParam String token, Model model) {
        Usuario usuario = usuarioRepository.findByConfirmationToken(token)
                .orElse(null);

        if (usuario == null) {
            model.addAttribute("mensaje", "Token inválido o expirado.");
            return "validacion";
        }

        usuario.setEmailConfirmed(true); // Confirmar el correo
        usuario.setConfirmationToken(null); // Limpia el token
        usuarioRepository.save(usuario);

        model.addAttribute("mensaje", "Correo confirmado con éxito. Ahora puedes iniciar sesión.");
        return "validacion";
    }
    
}
