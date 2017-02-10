/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brasajava.dao;

import com.brasajava.model.Cuenta;
import com.brasajava.model.Factura;
import com.brasajava.model.Persona;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Ricardo
 */
public interface FacturaDAO extends CrudRepository<Factura, Long>{
    List<Cuenta> findByCliente(Persona cliente);
    List<Cuenta> findByUsuario(Persona usuario);
    
}
