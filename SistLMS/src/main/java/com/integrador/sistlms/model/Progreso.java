package com.integrador.sistlms.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Progreso")
public class Progreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProgreso;

    @ManyToOne
    @JoinColumn(name = "idInscripcion", nullable = false)
    private Inscripcion inscripcion;

    private String progresoEvaluacion;

    private String progresoObjetivo;

    @Column(columnDefinition = "TEXT")
    private String comentarios;

    @OneToMany(mappedBy = "progreso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notificacion> notificaciones;

    // Getters y setters
    public Long getIdProgreso() {
        return idProgreso;
    }

    public void setIdProgreso(Long idProgreso) {
        this.idProgreso = idProgreso;
    }

    public String getProgresoEvaluacion() {
        return progresoEvaluacion;
    }

    public void setProgresoEvaluacion(String progresoEvaluacion) {
        this.progresoEvaluacion = progresoEvaluacion;
    }

    public String getProgresoObjetivo() {
        return progresoObjetivo;
    }

    public void setProgresoObjetivo(String progresoObjetivo) {
        this.progresoObjetivo = progresoObjetivo;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public Inscripcion getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(Inscripcion inscripcion) {
        this.inscripcion = inscripcion;
    }
}

