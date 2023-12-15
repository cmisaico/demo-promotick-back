package com.promotick.controllers;

import com.promotick.entities.Producto;
import com.promotick.repositories.CarritoRepository;
import com.promotick.repositories.ProductoRepository;
import com.promotick.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carritos")
@CrossOrigin(origins = "*")
public class CarritoController {
    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<Producto>> obtenerProductosEnCarrito(@PathVariable Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .map(usuario -> ResponseEntity.ok().body(usuario.getCarrito().getProductos()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{usuarioId}/agregar/{productoId}")
    public ResponseEntity<String> agregarProductoAlCarrito(@PathVariable Long usuarioId, @PathVariable Long productoId) {
        return usuarioRepository.findById(usuarioId).map(usuario -> {
            Producto producto = productoRepository.findById(productoId).orElse(null);
            if (producto != null) {
                usuario.getCarrito().getProductos().add(producto);
                usuarioRepository.save(usuario);
                return ResponseEntity.ok("Producto agregado al carrito con éxito.");
            } else {
                return ResponseEntity.badRequest().body("Producto no encontrado.");
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{usuarioId}/registrarCarrito")
    public ResponseEntity<String> agregarProductosAlCarrito(@PathVariable String usuarioId, @RequestBody List<Producto> productos) {
        return usuarioRepository.findById((Long.parseLong(usuarioId))).map(usuario -> {
            usuario.getCarrito().getProductos().addAll(productos);
            usuarioRepository.save(usuario);
            return ResponseEntity.ok("Productos agregados al carrito con éxito.");
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{usuarioId}/eliminar/{productoId}")
    public ResponseEntity<String> eliminarProductoDelCarrito(@PathVariable Long usuarioId, @PathVariable Long productoId) {
        return usuarioRepository.findById(usuarioId).map(usuario -> {
            Producto producto = productoRepository.findById(productoId).orElse(null);
            if (producto != null) {
                usuario.getCarrito().getProductos().remove(producto);
                usuarioRepository.save(usuario);
                return ResponseEntity.ok("Producto eliminado del carrito con éxito.");
            } else {
                return ResponseEntity.badRequest().body("Producto no encontrado en el carrito.");
            }
        }).orElse(ResponseEntity.notFound().build());
    }
}