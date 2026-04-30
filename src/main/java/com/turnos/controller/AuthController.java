package com.turnos.controller;

import com.turnos.dto.AuthResponseDTO;
import com.turnos.dto.LoginRequestDTO;
import com.turnos.dto.RegisterRequestDTO;
import com.turnos.model.Usuario;
import com.turnos.security.JwtUtils;
import com.turnos.service.AuthService;
import com.turnos.store.UsuarioStore;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired private AuthService authService;
    
    @Autowired private UsuarioStore usuarioStore;
    
    @Autowired private AuthenticationManager authenticationManager;
    
    @Autowired private JwtUtils jwtUtils;

    
    // ENDPOINT 1: POST /api/auth/register
    // Público. Rol asignado por defecto: ROLE_PACIENTE.
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDTO request) {
        
        // Verificar que username y email no estén en uso
        if (usuarioStore.existeUsername(request.getUsername())) 
            return ResponseEntity.status(409).body("El username ya esta en uso");
        
        if (usuarioStore.existeEmail(request.getEmail())) 
            return ResponseEntity.status(409).body("El email ya esta registrado");
        
        authService.registrar(request);
        
        return ResponseEntity.status(201).body("Usuario registrado correctamente");
    }

    // ENDPOINT 2: POST /api/auth/login
    // Público. Devuelve JWT con expiración de 1 hora.
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
    
         // Spring Security verifica username y password internamente
        // Si son incorrectos lanza BadCredentialsException → 401 automático
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        
       
        // Obtener el usuario autenticado
        Usuario usuario = (Usuario) auth.getPrincipal();
    
        // Armar la lista de roles para incluir en el JWT
        List<String> roles = new ArrayList<>();
        
        for (GrantedAuthority authority : usuario.getAuthorities()) 
            roles.add(authority.getAuthority());
        
        // Generar el JWT
        String token = jwtUtils.generarToken(usuario.getUsername(), roles);
        
        return ResponseEntity.ok(new AuthResponseDTO(token, jwtUtils.getExpirationSeconds(), usuario.getUsername()));
    }
}
