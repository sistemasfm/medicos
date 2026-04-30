package com.turnos.store;

import com.turnos.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioStore {
    // Lista en memoria que actúa como "base de datos"
    // Se inicializa con un usuario ADMIN de ejemplo
    private List<Usuario> usuarios = new ArrayList<>();
    
    // Contador para simular el auto-incremento de IDs
    private long contadorId = 1;

    // El PasswordEncoder se inyecta para inicializar los usuarios de prueba
    public UsuarioStore(PasswordEncoder encoder) {
       
        // Usuario ADMIN de prueba (password: "admin123")
        List<String> rolesAdmin = new ArrayList<>(); 
        rolesAdmin.add("ROLE_ADMIN");
        usuarios.add(new Usuario(contadorId++, "admin", encoder.encode("admin123"), "admin@turnos.com", rolesAdmin));
        
        // Usuario MEDICO de prueba (password: "medico123")
        List<String> rolesMedico = new ArrayList<>(); 
        rolesMedico.add("ROLE_MEDICO");
        usuarios.add(new Usuario(contadorId++, "dra_garcia", encoder.encode("medico123"), "garcia@turnos.com", rolesMedico));
    }
    
     // Guardar un nuevo usuario en la lista
    public void guardar(Usuario usuario) { 
        usuario.setId(contadorId++); 
        usuarios.add(usuario); 
    }
   
    // Buscar un usuario por su username
    // Retorna null si no lo encuentra
    public Usuario buscarPorUsername(String username) {
        for (Usuario u : usuarios) 
            if (u.getUsername().equals(username)) 
                return u;
        return null;
    }
    
    // Verificar si ya existe un usuario con ese username
    public boolean existeUsername(String username) {
        for (Usuario u : usuarios) 
            if (u.getUsername().equals(username)) 
                return true;
        return false;
    }
    

    // Verificar si ya existe un usuario con ese email
    public boolean existeEmail(String email) {
        for (Usuario u : usuarios) 
            if (u.getEmail().equals(email)) 
                return true;
        return false;
    }

    // Retornar todos los usuarios (útil para debugging)
    public List<Usuario> obtenerTodos() { 
        return usuarios; 
    }
}
