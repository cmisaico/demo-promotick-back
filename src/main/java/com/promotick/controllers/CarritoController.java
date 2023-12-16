package com.promotick.controllers;

import com.promotick.entities.Carrito;
import com.promotick.entities.Producto;
import com.promotick.entities.Usuario;
import com.promotick.repositories.CarritoRepository;
import com.promotick.repositories.ProductoRepository;
import com.promotick.repositories.UsuarioRepository;
import com.promotick.request.ItemRequest;
import com.promotick.services.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carritos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CarritoController {
    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<Producto>> obtenerProductosEnCarrito(@PathVariable Long usuarioId) {

        return usuarioRepository.findById(usuarioId).map(user -> {
            Carrito carrito = carritoService.obtenerProductosEnCarrito(user);
            return new ResponseEntity<>(carrito.getProductos(), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/agregar")
    public void agregarProductoAlCarrito(@RequestBody ItemRequest item) {
        usuarioRepository.findById(item.getUsuarioId()).map(usuario -> {
            Producto producto = productoRepository.findById(item.getProductoId()).orElse(null);
            if (producto != null) {
                usuario.getCarrito().getProductos().add(producto);
                usuarioRepository.save(usuario);
            }
            return null;
        });
    }

    @DeleteMapping("/{usuarioId}/eliminar/{productoId}")
    public void eliminarProductoDelCarrito(@PathVariable Long usuarioId, @PathVariable Long productoId) {
        usuarioRepository.findById(usuarioId).map(usuario -> {
            Producto producto = productoRepository.findById(productoId).orElse(null);
            if (producto != null) {
                usuario.getCarrito().getProductos().remove(producto);
                usuarioRepository.save(usuario);
                return ResponseEntity.ok("Producto eliminado del carrito con exito.");
            } else {
                return ResponseEntity.badRequest().body("Producto no encontrado en el carrito.");
            }
        }).orElse(ResponseEntity.notFound().build());
    }
}