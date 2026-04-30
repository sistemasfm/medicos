package com.turnos.controller;

import com.turnos.model.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> miPerfil(Authentication auth) {
       
        Usuario usuario = (Usuario) auth.getPrincipal();
       
        Map<String, Object> perfil = new HashMap<>();
       
        perfil.put("id", usuario.getId());
        perfil.put("username", usuario.getUsername());
        perfil.put("email", usuario.getEmail());
        perfil.put("roles", usuario.getRoles());
       
        return ResponseEntity.ok(perfil);
    }
}
