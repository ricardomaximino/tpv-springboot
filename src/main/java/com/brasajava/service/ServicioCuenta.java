/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brasajava.service;

import com.brasajava.dao.CuentaDAO;
import com.brasajava.model.Cuenta;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ricardo
 */
@Service
public class ServicioCuenta {
    
    @Autowired
    private CuentaDAO dao;
    
    public Cuenta save(Cuenta cuenta){
        return dao.save(cuenta);
    }
    
    public void delete(Cuenta cuenta){
        dao.delete(cuenta);
    }
    
    public Iterable<Cuenta> findAll(){
        return dao.findAll();
    }
    
    public Iterable<Cuenta> findByFecha(LocalDate fecha){
        return dao.findByFecha(fecha);
    }
}
