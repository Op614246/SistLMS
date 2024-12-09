package com.integrador.sistlms.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.integrador.sistlms.model.Curso;
import com.integrador.sistlms.model.Inscripcion;
import com.integrador.sistlms.model.Notificacion;
import com.integrador.sistlms.model.Progreso;
import com.integrador.sistlms.model.Usuario;
import com.integrador.sistlms.model.dto.NotificacionDTO;
import com.integrador.sistlms.model.dto.UsuarioDTO;
import com.integrador.sistlms.repository.CursoRepository;
import com.integrador.sistlms.repository.InscripcionRepository;
import com.integrador.sistlms.repository.NotificacionRepository;
import com.integrador.sistlms.repository.ProgresoRepository;
import com.integrador.sistlms.security.CustomUserDetails;
import com.integrador.sistlms.service.InscripcionService;

@Controller
public class NotificacionController {

    @Autowired
    private InscripcionService inscripcionService;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ProgresoRepository progresoRepository;

    @Autowired
    private NotificacionRepository notificacionRepository;

    private final InscripcionRepository inscripcionRepository;

    public NotificacionController(InscripcionRepository inscripcionRepository) {
        this.inscripcionRepository = inscripcionRepository;
    }

    @GetMapping("/comentarios")
    public String Comentarios(Model model, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Usuario usuario = userDetails.getUsuario();

        // Obtener los cursos en los que el usuario está inscrito
        List<Curso> cursosInscritos = inscripcionService.obtenerCursosPorUsuario(usuario);

        // Pasar la lista de cursos al modelo
        model.addAttribute("cursos", cursosInscritos);

        return "Terapeuta/comentarios";
    }

    @GetMapping("/alumnos-por-curso/{idCurso}")
    @ResponseBody
    public List<UsuarioDTO> obtenerAlumnosPorCurso(@PathVariable Long idCurso) {
        // Buscar el curso por ID
        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Filtrar alumnos con rol "1" (ESTUDIANTE) y mapear al DTO
        return curso.getInscripciones().stream()
                .map(Inscripcion::getUsuario)
                .filter(usuario -> usuario.getRoles().stream().anyMatch(rol -> rol.getIdrol() == 1))
                .map(usuario -> new UsuarioDTO(usuario.getIdusuario(), usuario.getNombre(), usuario.getApellido()))
                .collect(Collectors.toList());
    }

    @PostMapping("/notificaciones/enviar")
    public String enviarComentario(
            @RequestParam("cursoId") Long cursoId,
            @RequestParam("alumnoId") Long alumnoId,
            @RequestParam("comentario") String comentario) {

        // Buscar la inscripción del alumno en el curso
        Inscripcion inscripcion = inscripcionRepository.findByUsuarioIdAndCursoId(alumnoId, cursoId)
                .orElseThrow(() -> new RuntimeException("No se encontró la inscripción para el alumno en este curso."));

        // Buscar el progreso relacionado con la inscripción
        Progreso progreso = progresoRepository.findByInscripcionIdinscripcion(inscripcion.getIdinscripcion())
                .orElseThrow(() -> new RuntimeException("No se encontró el progreso del alumno."));

        // Crear una nueva notificación
        Notificacion notificacion = new Notificacion();
        notificacion.setProgreso(progreso);
        notificacion.setContenido(comentario);

        // Guardar la notificación en la base de datos
        notificacionRepository.save(notificacion);

        return "redirect:/comentarios?success"; // Redirigir a la página con un mensaje de éxito
    }

    @GetMapping("/notificaciones-enviadas")
    @ResponseBody
    public List<NotificacionDTO> obtenerNotificacionesEnviadas(Authentication authentication) {
        // Obtener al terapeuta (usuario autenticado)
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Usuario terapeuta = userDetails.getUsuario();

        // Validar que el usuario tiene el rol de terapeuta
        if (terapeuta.getRoles().stream().noneMatch(rol -> rol.getIdrol() == 2)) {
            throw new RuntimeException("El usuario autenticado no tiene permisos para enviar notificaciones.");
        }

        // Obtener todas las notificaciones relacionadas con este terapeuta
        List<Notificacion> notificaciones = notificacionRepository.findByTerapeutaId(terapeuta.getIdusuario());

        // Mapear las notificaciones a DTO
        return notificaciones.stream().map(notificacion -> {
            Usuario alumno = notificacion.getProgreso().getInscripcion().getUsuario(); // Alumno destinatario
            return new NotificacionDTO(
                    notificacion.getIdNotificacion(),
                    alumno.getNombre() + " " + alumno.getApellido(), // Destinatario (Alumno)
                    notificacion.getContenido() // Contenido del mensaje
            );
        }).collect(Collectors.toList());
    }

}
