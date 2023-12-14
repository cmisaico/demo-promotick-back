package com.promotick.services.impl;

import com.promotick.dto.CategoriaDTO;
import com.promotick.dto.ProductoDTO;
import com.promotick.entities.Categoria;
import com.promotick.repositories.CategoriaRepository;
import com.promotick.repositories.ProductoRepository;
import com.promotick.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<CategoriaDTO> obtenerCategorias() {
        List<Object[]> categorias = categoriaRepository.obtenerCategoriasConCantidadDeProductos();
        List<CategoriaDTO> categoriasDTO = new ArrayList<>();

        for(Object[] categoria : categorias) {
            CategoriaDTO categoriaDTO = new CategoriaDTO();
            categoriaDTO.setId((Long) categoria[0]);
            categoriaDTO.setNombre((String) categoria[1]);
            categoriaDTO.setCantidadDeProductos((Long) categoria[2]);
            categoriasDTO.add(categoriaDTO);
        }
        return categoriasDTO;
    }

    public List<ProductoDTO> obtenerProductosPorCategorias(List<Long> categoriaIds) {
        List<Object[]> objects;
        if (categoriaIds == null) {
            objects = productoRepository.obtenerTodosLosProductos();
        } else {
            objects = productoRepository.filtrarPorCategoriaIds(categoriaIds);
        }
        List<ProductoDTO> productosDTO = new ArrayList<>();
        for(Object[] objectDTO : objects) {
            ProductoDTO productoDTO = new ProductoDTO();
            productoDTO.setId((Long) objectDTO[0]);
            productoDTO.setNombre((String) objectDTO[1]);
            productoDTO.setStock((Integer) objectDTO[2]);
            productoDTO.setImagen((String)objectDTO[3]);
            productoDTO.setPrecio((Double) objectDTO[4]);
            productosDTO.add(productoDTO);
        }
        return productosDTO;
    }


}
