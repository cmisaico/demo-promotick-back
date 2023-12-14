package com.promotick.security;

import com.promotick.entities.Usuario;
import com.promotick.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
        return usuarioOptional.map(usuario -> User.withUsername(
                usuario.getEmail())
                .password(usuario.getContrasenia())
                .roles("USER")
                .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
    }

    public boolean validarCredenciales(String username, String password) {
        Optional<Usuario> usuarioOptional = usuarioRepository
                .findByEmailAndContrasenia(username, password);

        return usuarioOptional.isPresent();
    }
}