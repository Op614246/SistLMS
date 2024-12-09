package com.integrador.sistlms.controller;

import com.integrador.sistlms.model.Curso;
import com.integrador.sistlms.model.Inscripcion;
import com.integrador.sistlms.model.Permiso;
import com.integrador.sistlms.model.Usuario;
import com.integrador.sistlms.repository.CursoRepository;
import com.integrador.sistlms.repository.InscripcionRepository;
import com.integrador.sistlms.repository.PermisoRepository;
import com.integrador.sistlms.repository.UsuarioRepository;
import com.integrador.sistlms.service.EmailService;
import com.integrador.sistlms.service.TokenGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PermisoRepository permisoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @PostMapping("/registrar")
    public String registrarUsuario(
            @RequestParam("name") String nombre,
            @RequestParam("surname") String apellido,
            @RequestParam("email") String email,
            @RequestParam("password") String contrasena,
            @RequestParam(value = "courses[]", required = false) List<String> cursosSeleccionados) {

        // Verificar si el correo ya existe
        if (usuarioRepository.existsByEmail(email)) {
            return "redirect:/login?mensaje=correoRegistrado";
        }
        // Crear nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setContrasena(new BCryptPasswordEncoder().encode(contrasena)); // Asegúrate de codificar la contraseña
        usuario.setEmailConfirmed(false);

        // Buscar el rol con ID 1
        Optional<Permiso> rol = permisoRepository.findById(1);
        if (rol.isPresent()) {
            usuario.getRoles().add(rol.get()); // Asignar el rol al usuario
        } else {
            throw new IllegalArgumentException("El rol con ID 1 no existe.");
        }

        // Generar token de confirmación
        String token = TokenGenerator.generateToken();
        usuario.setConfirmationToken(token);

        // Guardar usuario en la base de datos
        usuario = usuarioRepository.save(usuario);

        // Registrar inscripciones a cursos
        if (cursosSeleccionados != null && !cursosSeleccionados.isEmpty()) {
            for (String nombreCurso : cursosSeleccionados) {
                Curso curso = cursoRepository.findByNombre(nombreCurso)
                        .orElseThrow(() -> new IllegalArgumentException("El curso " + nombreCurso + " no existe."));

                Inscripcion inscripcion = new Inscripcion();
                inscripcion.setUsuario(usuario);
                inscripcion.setCurso(curso);
                inscripcion.setFechaInscripcion(LocalDate.now());
                inscripcion.setEstado("Activa"); // Estado inicial

                inscripcionRepository.save(inscripcion);
            }
        }

        // Enviar correo de confirmación
        emailService.sendConfirmationEmail(email, token);

        return "redirect:/login?mensaje=registroExitoso";
    }
}
