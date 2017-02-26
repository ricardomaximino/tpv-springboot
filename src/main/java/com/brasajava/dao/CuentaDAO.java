/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brasajava.dao;

import com.brasajava.model.Cuenta;
import com.brasajava.model.Persona;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Ricardo
 */
public interface CuentaDAO extends CrudRepository<Cuenta, Long>{
    
    List<Cuenta> findByFecha(LocalDate fecha);
}
