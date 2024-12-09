package com.integrador.sistlms.controller;

import com.integrador.sistlms.model.Usuario;
import com.integrador.sistlms.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfirmacionController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/confirm")
    public String confirmarCorreo(@RequestParam String token) {
        Usuario usuario = usuarioRepository.findByConfirmationToken(token)
                .orElse(null);

        if (usuario == null) {
            return "Token inválido o expirado.";
        }

        usuario.setEmailConfirmed(true); // Confirmar el correo
        usuario.setConfirmationToken(null); // Limpia el token
        usuarioRepository.save(usuario);

        return "Correo confirmado con éxito. Ahora puedes iniciar sesión.";
    }
}
