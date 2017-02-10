/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brasajava.dao;

import com.brasajava.model.Producto;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Ricardo
 */
public interface ProductoDAO extends CrudRepository<Producto, Long>{
    
    List<Producto> findByNombre(String nombre);
    List<Producto> findByNombreLikeIgnoreCase(String nombre);
    List<Producto> findByPrecioMasIvaBetween(BigDecimal valor1,BigDecimal valor2);
    
    
}
