package com.turnos.store;

import com.turnos.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioStore {
    private List<Usuario> usuarios = new ArrayList<>();
    private long contadorId = 1;

    public UsuarioStore(PasswordEncoder encoder) {
        
        List<String> rolesAdmin = new ArrayList<>(); 
        rolesAdmin.add("ROLE_ADMIN");
        usuarios.add(new Usuario(contadorId++, "admin", encoder.encode("admin123"), "admin@turnos.com", rolesAdmin));
        
        List<String> rolesMedico = new ArrayList<>(); 
        rolesMedico.add("ROLE_MEDICO");
        usuarios.add(new Usuario(contadorId++, "dra_garcia", encoder.encode("medico123"), "garcia@turnos.com", rolesMedico));
    }
    public void guardar(Usuario usuario) { 
        usuario.setId(contadorId++); 
        usuarios.add(usuario); 
    }
    public Usuario buscarPorUsername(String username) {
        for (Usuario u : usuarios) 
            if (u.getUsername().equals(username)) 
                return u;
        return null;
    }
    public boolean existeUsername(String username) {
        for (Usuario u : usuarios) 
            if (u.getUsername().equals(username)) 
                return true;
        return false;
    }
    public boolean existeEmail(String email) {
        for (Usuario u : usuarios) 
            if (u.getEmail().equals(email)) 
                return true;
        return false;
    }
}
