package com.integrador.sistlms.model.dto;

public class UsuarioDTO {
    private Long idusuario;
    private String nombre;
    private String apellido;

    // Constructor
    public UsuarioDTO(Long idusuario, String nombre, String apellido) {
        this.idusuario = idusuario;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    // Getters y Setters
    public Long getIdusuario() {
        return idusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }
}

