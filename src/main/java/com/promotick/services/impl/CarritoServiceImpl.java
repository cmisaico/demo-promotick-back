package com.promotick.services.impl;

import com.promotick.entities.Carrito;
import com.promotick.entities.Producto;
import com.promotick.entities.Usuario;
import com.promotick.repositories.CarritoRepository;
import com.promotick.services.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    CarritoRepository carritoRepository;

    @Override
    public void agregarProductoAlCarrito(Long usuarioId, Long productoId) {

    }

    @Override
    public Carrito registrarCarrito(Long usuarioId, List<Producto> productos) {
        Carrito carrito = new Carrito();
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        carrito.setUsuario(usuario);
        carrito.setProductos(productos);
        return carritoRepository.save(carrito);


    }

    @Override
    public Carrito obtenerProductosEnCarrito(Usuario usuario) {
        return carritoRepository.findByUsuario(usuario);
    }
}
