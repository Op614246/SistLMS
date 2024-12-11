package com.integrador.sistlms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.integrador.sistlms.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByConfirmationToken(String confirmationToken);

    @Query("SELECT u FROM Usuario u JOIN u.roles r WHERE r.idrol = :roleId")
    List<Usuario> findUsuariosConRol(@Param("roleId") int roleId);
}
