package com.turnos.service;

import com.turnos.dto.RegisterRequestDTO;
import com.turnos.model.Usuario;
import com.turnos.store.UsuarioStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {
    @Autowired private UsuarioStore usuarioStore;
    @Autowired private PasswordEncoder passwordEncoder;
    public void registrar(RegisterRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_PACIENTE");
        usuario.setRoles(roles);
        usuarioStore.guardar(usuario);
    }
}
