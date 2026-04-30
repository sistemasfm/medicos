package com.turnos.config;

import com.turnos.security.JwtUtils;
import com.turnos.security.UserDetailsServiceImpl;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
   
    @Autowired private JwtUtils jwtUtils;
   
    @Autowired private UserDetailsServiceImpl userDetailsService;
   
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) 
            throws ServletException, IOException {

        // 1. Leer el header Authorization
        String header = request.getHeader("Authorization");
        
        // 2. Si no hay token, continuar sin autenticar
        if (header == null || !header.startsWith("Bearer ")) { 
            filterChain.doFilter(request, response); 
            return; 
        }
        
        // 3. Extraer el token (quitar "Bearer ")
        String token = header.substring(7);
        
        // 4. Validar firma y expiración
        if (!jwtUtils.validarToken(token)) { 
            filterChain.doFilter(request, response); 
            return;
        }
        
        // 5. Obtener el username del token
        String username = jwtUtils.obtenerUsername(token);
       
        // 6. Cargar el usuario con sus roles desde el store
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // 7. Registrar la autenticación en el SecurityContext
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
       
        // 8. Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
