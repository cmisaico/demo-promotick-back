package com.promotick.controllers;

import com.promotick.request.UsuarioRequest;
import com.promotick.response.LoginResponse;
import com.promotick.entities.Carrito;
import com.promotick.entities.Usuario;
import com.promotick.repositories.CarritoRepository;
import com.promotick.repositories.UsuarioRepository;
import com.promotick.security.UsuarioDetailsService;
import com.promotick.services.CarritoService;
import com.promotick.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CarritoService carritoService;

    @Autowired
    UsuarioDetailsService usuarioDetallesServicio;
    private final AuthenticationManager authenticationManager;

    private JwtUtil jwtUtil;
    public UsuarioController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

    }

    @GetMapping
    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> ResponseEntity.ok().body(usuario))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/registro")
    public ResponseEntity registrarUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        // Verificar si el usuario ya existe por su email
        if (usuarioRepository.findByEmail(usuarioRequest.getUsuario().getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("El email ya esta registrado.");
        }

        Carrito carrito = new Carrito();
        carrito.setUsuario(usuarioRequest.getUsuario());
        Usuario newUsuario = usuarioRepository.save(usuarioRequest.getUsuario());

        Carrito newCarrito = carritoService.registrarCarrito(newUsuario.getId(), usuarioRequest.getCarrito());
        newUsuario.setCarrito(newCarrito);
        usuarioRepository.save(newUsuario);
        Authentication authentication = new UsernamePasswordAuthenticationToken(usuarioRequest.getUsuario().getEmail(), usuarioRequest.getUsuario().getContrasenia());
        String email = authentication.getName();
        Usuario usuarioToken = new Usuario();
        usuarioRequest.getUsuario().setEmail(email);
        String token = jwtUtil.createToken(usuarioToken);
        LoginResponse loginResDTO = new LoginResponse(newUsuario.getId(), newUsuario.getEmail(), token, newUsuario.getNombre());

        return ResponseEntity.ok(loginResDTO);
    }

}