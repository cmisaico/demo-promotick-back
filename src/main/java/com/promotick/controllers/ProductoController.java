package com.promotick.controllers;

import com.promotick.dto.CategoriaDTO;
import com.promotick.dto.ProductoDTO;
import com.promotick.entities.Producto;
import com.promotick.repositories.ProductoRepository;
import com.promotick.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoService productService;

    @GetMapping
    public List<ProductoDTO> obtenerTodosProductos(@RequestParam(name = "categoriaIds", required = false) List<Long> categoriaIds) {
       return productService.obtenerProductosPorCategorias(categoriaIds);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        return productoRepository.findById(id)
                .map(producto -> ResponseEntity.ok().body(producto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/agregar")
    public ResponseEntity<String> agregarNuevoProducto(@RequestBody Producto producto) {
        // Puedes agregar validaciones u lógica específica antes de guardar el producto
        productoRepository.save(producto);

        return ResponseEntity.ok("Producto agregado con éxito.");
    }

    @GetMapping("/categorias")
    public List<CategoriaDTO> obtenerTodasLasCategoriasConCantidadDeProductos() {
        return productService.obtenerCategorias();
    }

}