package com.integrador.sistlms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integrador.sistlms.model.Curso;
import com.integrador.sistlms.repository.CursoRepository;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public Curso saveCurso(Curso curso) {
        return cursoRepository.save(curso);
    }
}
