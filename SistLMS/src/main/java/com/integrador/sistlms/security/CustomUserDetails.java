package com.integrador.sistlms.security;

import com.integrador.sistlms.model.Usuario;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.stream.Collectors;

public class CustomUserDetails extends User {
    private final String nombre;
    private final String apellido;
    private final Usuario usuario;

    public CustomUserDetails(Usuario usuario) {
        super(
            usuario.getEmail(),
            usuario.getContrasena(),
            usuario.getRoles().stream()
                    .map(rol -> new SimpleGrantedAuthority(rol.getRol()))
                    .collect(Collectors.toList())
        );
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Usuario getUsuario() {
        return usuario; // Método para obtener el objeto Usuario
    }
}
