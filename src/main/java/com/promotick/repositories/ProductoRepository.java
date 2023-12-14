package com.promotick.repositories;

import com.promotick.dto.ProductoDTO;
import com.promotick.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("SELECT p.id, p.nombre, p.stock, p.imagen, p.precio FROM Producto p WHERE p.categoria.id IN :categoriaIds")
    List<Object[]>  filtrarPorCategoriaIds(@Param("categoriaIds") List<Long> categoriaIds);

    @Query("SELECT p.id, p.nombre, p.stock, p.imagen, p.precio FROM Producto p")
    List<Object[]> obtenerTodosLosProductos();

}