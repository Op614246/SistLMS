package com.integrador.sistlms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.integrador.sistlms.security.CustomAuthenticationSuccessHandler;
import com.integrador.sistlms.security.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final CustomAuthenticationSuccessHandler successHandler;

    // Inyectamos el CustomAuthenticationSuccessHandler
    public SecurityConfig(CustomUserDetailsService userDetailsService,
            CustomAuthenticationSuccessHandler successHandler) {
        this.userDetailsService = userDetailsService;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/confirm", "/registrar", "/images/**", "/css/**", "/js/**", "/uploads/**",
                                "/login", "/inscribirse")
                        .permitAll()
                        .requestMatchers("/cursos", "/verPerfil", "/progreso", "/header", "/actualizarContrasena", "/actualizarCorreo")
                        .hasRole("ESTUDIANTE")
                        .requestMatchers("/", "/registrarCurso", "/comentarios", "/alumnos-por-curso/**", "/notificaciones-enviadas", "/notificaciones/**", "/verPerfilProf", "/headerProf", "/actualizarContrasenaProf", "/actualizarCorreoProf")
                        .hasRole("TERAPEUTA")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .permitAll()
                        .successHandler(successHandler))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .and()
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
