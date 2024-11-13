package com.integrador.backend2.service;

import com.integrador.backend2.model.Producto;
import java.util.List;

public interface ProductoService {
    Producto addProducto(Producto producto); // Para agregar un nuevo producto
    Producto getProductoById(Integer idProducto); // Para obtener un producto por su ID
    List<Producto> getAllProductos(); // Para obtener la lista de todos los productos
    Producto updateProducto(Producto producto, Integer idProducto); // Para actualizar un producto
    void deleteProducto(Integer idProducto); // Para eliminar un producto
}
