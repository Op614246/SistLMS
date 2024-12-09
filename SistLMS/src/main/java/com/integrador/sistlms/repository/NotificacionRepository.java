package com.integrador.sistlms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.integrador.sistlms.model.Notificacion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    @Query("SELECT n FROM Notificacion n " +
           "WHERE n.progreso.inscripcion.curso IN " +
           "(SELECT i.curso FROM Inscripcion i WHERE i.usuario.idusuario = :terapeutaId)")
    List<Notificacion> findByTerapeutaId(@Param("terapeutaId") Long terapeutaId);
}
