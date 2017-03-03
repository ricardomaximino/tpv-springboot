package com.brasajava.service;

import com.brasajava.dao.ProductoDAO;
import com.brasajava.model.Producto;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Esta Clase hace el puente entre la aplicación y la base de datos.
 * @author Ricardo Maximino
 */
@Service
public class ServicioProducto {
    @Autowired
    ProductoDAO dao;
    
    /**
     * Guarda o actualiza el producto en la base de datos.
     * @param producto del tipo com.brasajava.model.Producto.
     * @return del tipo com.brasajava.model.Producto.
     */
    public Producto save(Producto producto){
        return dao.save(producto);
    }
    
    /**
     * Borra definitivamente un producto de la base de datos.
     * @param producto del tipo com.brasajava.model.Producto.
     */
    public void delete(Producto producto){
        dao.delete(producto);
    }
    
    /**
     * Busca un producto en la base de datos por el id.
     * @param id del tipo long.
     * @return del tipo com.brasajava.model.Producto.
     */
    public Producto findOne(Long id){
        return dao.findOne(id);
    }
    
    /**
     * Retorna todos los productos de la base de datos.
     * @return del tipo java.lang.Iterable&lt;Producto&gt;.
     */
    public Iterable<Producto> findAll(){
        return dao.findAll();
    }
    /**
     * Busca todos los productos de la base de datos que tengan el campo nombre
     * igual al parametro.
     * @param nombre del tipo java.lang.String.
     * @return del tipo java.lang.Iterable&lt;Producto&gt;.
     */
    public Iterable<Producto> findByNombre(String nombre){
        return dao.findByNombre(nombre);
    }
    /**
     * Busca todos los productos de la base de datos que tengan en el campo
     * nombre la secuencia de caracteres del parametro.
     * @param nombre del tipo java.lang.String.
     * @return del tipo java.lang.Iterable&lt;Producto&gt;.
     */
    public Iterable<Producto> findByNombreLike(String nombre){
        return dao.findByNombreLikeIgnoreCase("%" + nombre + "%");
    }
    /**
     * Busca todos los productos de la base de datos que tengan el campo
     * precioMasIva entre los valores del valor1 y valor2.
     * @param valor1 del tipo java.math.BigDecimal.
     * @param valor2 del tipo java.math.BigDecimal.
     * @return del tipo java.lang.Iterable.
     */
    public Iterable<Producto> findByPrecioMasIvaBetween(BigDecimal valor1,BigDecimal valor2){
        return dao.findByPrecioMasIvaBetween(valor1,valor2);
    }
}
