package com.integrador.sistlms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.integrador.sistlms.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    Optional<Curso> findByNombre(String nombre);
}
