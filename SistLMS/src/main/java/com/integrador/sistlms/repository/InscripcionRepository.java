package com.integrador.sistlms.repository;

import com.integrador.sistlms.model.Inscripcion;
import com.integrador.sistlms.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    List<Inscripcion> findByUsuarioEmail(String email);

    @Query("SELECT i.usuario FROM Inscripcion i " +
            "JOIN i.usuario.roles r " +
            "WHERE i.curso.idcurso = :idCurso " +
            "AND r.rol = 'ROLE_TERAPEUTA'")
    Optional<Usuario> findTerapeutaByCursoId(Long idCurso);

    List<Inscripcion> findByUsuario(Usuario usuario);

    @Query("SELECT i.usuario FROM Inscripcion i WHERE i.curso.idcurso = :idCurso AND 1 IN (SELECT p.idrol FROM i.usuario.roles p)")
    List<Usuario> findUsuariosPorCursoConRol(@Param("idCurso") Long idCurso);

    @Query("SELECT i FROM Inscripcion i WHERE i.usuario.idusuario = :alumnoId AND i.curso.idcurso = :cursoId")
    Optional<Inscripcion> findByUsuarioIdAndCursoId(@Param("alumnoId") Long alumnoId, @Param("cursoId") Long cursoId);
}
