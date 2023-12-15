package com.promotick.request;

import java.util.List;

public class CarritoRequest {

    private Long id;

    private List<Integer> productos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Integer> getProductos() {
        return productos;
    }

    public void setProductos(List<Integer> productos) {
        this.productos = productos;
    }
}
