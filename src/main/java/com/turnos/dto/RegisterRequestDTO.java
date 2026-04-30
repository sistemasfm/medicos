package com.turnos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequestDTO {
    @NotBlank(message = "El nombre no puede ser nulo")  
    @Size(min = 4, max = 20, message = "La longitud de ser mínimo 4 y máximo 20 caracteres.")
    private String username;
    @NotBlank (message = "El email no puede ser nulo")  
    @Email(message = "Debe ser una dirección de correo válida")  
    private String email;
    @NotBlank (message = "El password no puede ser nulo")  
    @Size(min = 8, message = "La longitud de contraseña debe ser de al menos 8 caracteres")
    private String password;
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}
