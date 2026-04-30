package com.turnos.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("turnoSecurityService")
public class TurnoSecurityService {
    
    // En una implementación real con BD, aquí se inyectaría el repositorio.
    // Para el ejemplo didáctico, se pasa la lista de turnos directamente.

    /**
     * Verifica si el médico autenticado es el asignado al turno con el id dado.
     */

    public boolean esMedicoDelTurno(Authentication authentication, Long turnoId, List<Map<String, Object>> turnos) {
        
        String medicoUsername = authentication.getName();
        
        for (Map<String, Object> turno : turnos) {
            Long id = (Long) turno.get("id");
            if (id.equals(turnoId)) 
                return medicoUsername.equals((String) turno.get("medico"));
        }
        
        return false;
    }
}
