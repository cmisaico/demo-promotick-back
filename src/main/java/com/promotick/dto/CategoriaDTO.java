package com.promotick.dto;

public class CategoriaDTO {

    private Long id;

    private String nombre;

    private Long cantidadDeProductos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getCantidadDeProductos() {
        return cantidadDeProductos;
    }

    public void setCantidadDeProductos(Long cantidadDeProductos) {
        this.cantidadDeProductos = cantidadDeProductos;
    }
}
