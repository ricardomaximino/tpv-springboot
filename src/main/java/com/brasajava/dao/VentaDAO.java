/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brasajava.dao;

import com.brasajava.model.Persona;
import com.brasajava.model.Venta;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Ricardo
 */
public interface VentaDAO extends CrudRepository<Venta, Long>{
    
    List<Venta> findByCliente(Persona cliente);
    List<Venta> findByUsuario(Persona usuario);
    List<Venta> findByFecha(LocalDate fecha);
}
