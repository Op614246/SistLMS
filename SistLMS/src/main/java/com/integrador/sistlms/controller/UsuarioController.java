package com.integrador.sistlms.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.integrador.sistlms.security.CustomUserDetails;

@Controller
public class UsuarioController {

    @GetMapping("/header")
    public String getHeaderData(Authentication authentication, Model model) {
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            model.addAttribute("nombre", userDetails.getNombre());
            model.addAttribute("apellido", userDetails.getApellido());
            model.addAttribute("roles", userDetails.getAuthorities());

        }
        
        return "header"; // El nombre del fragmento de Thymeleaf para el header
    }

    @GetMapping("/headerProf")
    public String getHeaderProfData(Authentication authentication, Model model) {
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            model.addAttribute("nombre", userDetails.getNombre());
            model.addAttribute("apellido", userDetails.getApellido());
            model.addAttribute("roles", userDetails.getAuthorities());

        }
        
        return "headerProf"; // El nombre del fragmento de Thymeleaf para el header
    }

}
