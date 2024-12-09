package com.integrador.sistlms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.integrador.sistlms.model.Progreso;

@Repository
public interface ProgresoRepository extends JpaRepository<Progreso, Long> {
    Optional<Progreso> findByInscripcionIdinscripcion(Long idinscripcion);
}

