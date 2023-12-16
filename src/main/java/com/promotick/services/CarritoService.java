package com.promotick.services;

import com.promotick.entities.Carrito;
import com.promotick.entities.Producto;
import com.promotick.entities.Usuario;

import java.util.List;

public interface CarritoService {

    public void agregarProductoAlCarrito(Long usuarioId, Long productoId);

    public Carrito registrarCarrito(Long usuarioId, List<Producto> productos);

    public Carrito obtenerProductosEnCarrito(Usuario usuario);
}
