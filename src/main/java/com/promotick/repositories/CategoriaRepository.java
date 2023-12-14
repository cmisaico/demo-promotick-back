package com.promotick.repositories;

import com.promotick.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query("SELECT c.id, c.nombre, COUNT(p) as cantidadDeProductos " +
            "FROM Categoria c LEFT JOIN c.productos p " +
            "GROUP BY c.id")
    List<Object[]> obtenerCategoriasConCantidadDeProductos();
}