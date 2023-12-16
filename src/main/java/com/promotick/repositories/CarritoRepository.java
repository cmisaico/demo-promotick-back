package com.promotick.repositories;

import com.promotick.entities.Carrito;
import com.promotick.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    Carrito findByUsuario(Usuario usuario);


}