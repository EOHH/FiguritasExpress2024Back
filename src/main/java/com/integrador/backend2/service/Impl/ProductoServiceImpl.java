package com.integrador.backend2.service.Impl;

import com.integrador.backend2.model.Producto;
import com.integrador.backend2.repository.ProductoRepository;
import com.integrador.backend2.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Producto addProducto(Producto producto) {
        return productoRepository.save(producto); // Guardar el nuevo producto
    }

    @Override
    public Producto getProductoById(Integer idProducto) {
        Optional<Producto> optionalProducto = productoRepository.findById(idProducto);
        return optionalProducto.orElse(null); // Retorna el producto si existe o null si no
    }

    @Override
    public List<Producto> getAllProductos() {
        return productoRepository.findAll(); // Obtener todos los productos
    }

    @Override
    public Producto updateProducto(Producto producto, Integer idProducto) {
        Optional<Producto> optionalProducto = productoRepository.findById(idProducto);
        if (optionalProducto.isPresent()) {
            Producto productoExistente = optionalProducto.get();
            productoExistente.setNombre(producto.getNombre());
            productoExistente.setPrecio(producto.getPrecio());
            productoExistente.setCondicion(producto.getCondicion());
            productoExistente.setStock(producto.getStock());
            // Actualizar otros campos según sea necesario
            return productoRepository.save(productoExistente); // Guardar el producto actualizado
        }
        return null; // Si no existe el producto, retorna null
    }

    @Override
    public void deleteProducto(Integer idProducto) {
        productoRepository.deleteById(idProducto); // Eliminar el producto por su ID
    }
}
