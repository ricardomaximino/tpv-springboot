/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brasajava.service;

import com.brasajava.dao.ProductoDAO;
import com.brasajava.model.Producto;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ricardo
 */
@Service
public class ServicioProducto {
    @Autowired
    ProductoDAO dao;
    
    
    public Producto save(Producto producto){
        return dao.save(producto);
    }
    
    public void delete(Producto producto){
        dao.delete(producto);
    }
    
    public Producto findOne(Long id){
        return dao.findOne(id);
    }
    
    public Iterable<Producto> findAll(){
        return dao.findAll();
    }
    public Iterable<Producto> findByNombre(String nombre){
        return dao.findByNombre(nombre);
    }
    public Iterable<Producto> findByNombreLike(String nombre){
        return dao.findByNombreLikeIgnoreCase("%" + nombre + "%");
    }
    public Iterable<Producto> findByPrecioMasIvaBetween(BigDecimal valor1,BigDecimal valor2){
        return dao.findByPrecioMasIvaBetween(valor1,valor2);
    }
}
