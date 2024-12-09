package com.integrador.sistlms.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest request,
            jakarta.servlet.http.HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        // Obtener el rol del usuario autenticado
        String role = authentication.getAuthorities().stream()
                                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                                    .findFirst()
                                    .orElse("");

        // Redirigir según el rol
        String redirectUrl = "/default"; // URL por defecto

        if (role.equals("ROLE_TERAPEUTA")) {
            redirectUrl = "/"; // Página para terapeutas
        } else if (role.equals("ROLE_ESTUDIANTE")) {
            redirectUrl = "/cursos"; // Página para estudiantes
        }

        // Redirigir al destino correspondiente
        response.sendRedirect(redirectUrl);
    }
}
