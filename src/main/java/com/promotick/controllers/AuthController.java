package com.promotick.controllers;

import com.promotick.dto.LoginDTO;
import com.promotick.response.LoginResponse;
import com.promotick.entities.Usuario;
import com.promotick.security.UsuarioDetailsService;
import com.promotick.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UsuarioDetailsService usuarioDetallesServicio;

    private final AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        try {
            // Validar credenciales
            Optional<Usuario> usuarioOptional = usuarioDetallesServicio.validarCredenciales(loginDTO.getEmail(), loginDTO.getContrasenia());
            if (usuarioOptional.isPresent()) {
                // Autenticacion
                Authentication authentication = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getContrasenia());
                String email = authentication.getName();
                Usuario usuario = usuarioOptional.get();
                usuario.setEmail(email);
                String token = jwtUtil.createToken(usuario);
                LoginResponse loginResDTO = new LoginResponse(usuario.getId(), email, token, usuario.getNombre());
                return ResponseEntity.ok(loginResDTO);
            } else {
                return ResponseEntity.status(401).body("Nombre de usuario o contrasena incorrectos");
            }
        } catch (Exception e) {
            // Manejar excepciones durante la autenticacion, si es necesario
            return ResponseEntity.status(401).body("Nombre de usuario o contrasena incorrectos");
        }
    }

    @Secured("ROLE_USER")
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // Logica de cierre de sesion
        return ResponseEntity.ok("Cierre de sesion exitoso");
    }
}
