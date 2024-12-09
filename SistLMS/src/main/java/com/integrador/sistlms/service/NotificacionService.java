package com.integrador.sistlms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import com.integrador.sistlms.model.Notificacion;
import com.integrador.sistlms.model.Usuario;
import com.integrador.sistlms.repository.NotificacionRepository;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    public List<Notificacion> obtenerUltimasNotificacionesPorAlumno(Usuario alumno) {
        Pageable pageable = PageRequest.of(0, 3); // Página 0, con un límite de 3 elementos
        return notificacionRepository.findTop3ByUsuarioOrderByFechaDesc(alumno, pageable);
    }
}

