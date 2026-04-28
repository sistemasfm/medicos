package com.turnos.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Usuario implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private String email;
    private List<String> roles;

    public Usuario() { this.roles = new ArrayList<>(); }
    public Usuario(Long id, String username, String password, String email, List<String> roles) {
        this.id = id; this.username = username; this.password = password; this.email = email; this.roles = roles;
    }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String rol : roles) authorities.add(new SimpleGrantedAuthority(rol));
        return authorities;
    }
    @Override public String getUsername() { return username; }
    @Override public String getPassword() { return password; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public List<String> getRoles() { return roles; }
    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
    public void setRoles(List<String> roles) { this.roles = roles; }
}
