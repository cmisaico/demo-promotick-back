package com.promotick.controllers;

import com.promotick.dto.LoginDTO;
import com.promotick.dto.LoginResDTO;
import com.promotick.entities.Usuario;
import com.promotick.security.UsuarioDetailsService;
import com.promotick.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
            if (usuarioDetallesServicio.validarCredenciales(loginDTO.getEmail(), loginDTO.getContrasenia())) {
                // Autenticación
                Authentication authentication = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getContrasenia());
                String email = authentication.getName();
                Usuario usuario = new Usuario();
                usuario.setEmail(email);
                String token = jwtUtil.createToken(usuario);
                LoginResDTO loginResDTO = new LoginResDTO(email, token);
                return ResponseEntity.ok(loginResDTO);
            } else {
                return ResponseEntity.status(401).body("Nombre de usuario o contraseña incorrectos");
            }
        } catch (Exception e) {
            // Manejar excepciones durante la autenticación, si es necesario
            return ResponseEntity.status(401).body("Nombre de usuario o contraseña incorrectos");
        }
    }

    @Secured("ROLE_USER")
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // Lógica de cierre de sesión
        return ResponseEntity.ok("Cierre de sesión exitoso");
    }
}
