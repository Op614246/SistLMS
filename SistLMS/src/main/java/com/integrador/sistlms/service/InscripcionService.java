package com.integrador.sistlms.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integrador.sistlms.model.Curso;
import com.integrador.sistlms.model.Inscripcion;
import com.integrador.sistlms.model.Usuario;
import com.integrador.sistlms.repository.InscripcionRepository;

@Service
public class InscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    public List<Curso> obtenerCursosPorUsuario(Usuario usuario) {
        return inscripcionRepository.findByUsuario(usuario)
                                     .stream()
                                     .map(Inscripcion::getCurso)
                                     .collect(Collectors.toList());
    }

    public List<Usuario> obtenerAlumnosPorCurso(Long idCurso) {
        return inscripcionRepository.findUsuariosPorCursoConRol(idCurso);
    }

}
