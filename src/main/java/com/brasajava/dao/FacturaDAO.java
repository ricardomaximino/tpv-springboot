/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brasajava.dao;

import com.brasajava.model.Factura;
import com.brasajava.model.Persona;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Ricardo
 */
public interface FacturaDAO extends CrudRepository<Factura, Long>{
    List<Factura> findByCliente(Persona cliente);
    List<Factura> findByUsuario(Persona usuario);
    List<Factura> findByFecha(LocalDate fecha);
    
}
