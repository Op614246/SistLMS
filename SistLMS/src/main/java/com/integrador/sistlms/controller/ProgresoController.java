package com.integrador.sistlms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.integrador.sistlms.model.Notificacion;
import com.integrador.sistlms.model.Usuario;
import com.integrador.sistlms.security.CustomUserDetails;
import com.integrador.sistlms.service.NotificacionService;

@Controller
public class ProgresoController {
    @Autowired
    private NotificacionService notificacionService;

    @GetMapping("/progreso")
    public String Progreso(Model model, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Usuario alumno = userDetails.getUsuario();

        // Obtener las 3 últimas notificaciones del alumno
        List<Notificacion> ultimasNotificaciones = notificacionService.obtenerUltimasNotificacionesPorAlumno(alumno);

        // Pasar las notificaciones al modelo
        model.addAttribute("notificaciones", ultimasNotificaciones);

        return "Alumno/progreso";
    }

}
