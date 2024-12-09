package com.integrador.sistlms.model.dto;

public class NotificacionDTO {
    private Long idNotificacion;
    private String destinatario;
    private String contenido;

    public NotificacionDTO(Long idNotificacion, String destinatario, String contenido) {
        this.idNotificacion = idNotificacion;
        this.destinatario = destinatario;
        this.contenido = contenido;
    }

    public Long getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    
}