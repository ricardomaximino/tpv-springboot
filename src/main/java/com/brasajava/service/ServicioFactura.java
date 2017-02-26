/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brasajava.service;

import com.brasajava.dao.FacturaDAO;
import com.brasajava.model.Factura;
import com.brasajava.model.Persona;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ricardo
 */
@Service
public class ServicioFactura {
    
    @Autowired
    private FacturaDAO dao;
    
    public Factura save(Factura factura){
        return dao.save(factura);
    }
    
    public void delete(Factura factura){
        dao.delete(factura);
    }
    
    public Iterable<Factura> findAll(){
        return dao.findAll();
    }
    
    public Iterable<Factura> findByCliente(Persona cliente){
        return dao.findByCliente(cliente);
    }
    
    public Iterable<Factura> findByUsuario(Persona usuario){
        return dao.findByUsuario(usuario);
    }
    
    public Iterable<Factura> findByFecha(LocalDate fecha){
        return dao.findByFecha(fecha);
    }
}
