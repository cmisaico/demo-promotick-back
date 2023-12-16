package com.promotick.request;

import com.promotick.entities.Producto;
import com.promotick.entities.Usuario;

import java.util.List;

public class UsuarioRequest {

    Usuario usuario;
    List<Producto> carrito;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Producto> getCarrito() {
        return carrito;
    }

    public void setCarrito(List<Producto> carrito) {
        this.carrito = carrito;
    }
}
