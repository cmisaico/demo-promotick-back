package com.promotick.services;

import com.promotick.dto.CategoriaDTO;
import com.promotick.dto.ProductoDTO;

import java.util.List;

public interface ProductoService {
    public List<CategoriaDTO> obtenerCategorias();

    public List<ProductoDTO> obtenerProductosPorCategorias(List<Long> categoriaIds);

}
