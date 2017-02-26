/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brasajava.service;

import com.brasajava.dao.VentaDAO;
import com.brasajava.model.Persona;
import com.brasajava.model.Venta;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ricardo
 */
@Service
public class ServicioVenta {
    
    @Autowired
    private VentaDAO dao;
    
    public Venta save(Venta venta){
        return dao.save(venta);
    }
    
    public void delete(Venta venta){
        dao.delete(venta);
    }
    
    public Iterable<Venta> findAll(){
        return dao.findAll();
    }
    public Iterable<Venta> findByCliente(Persona cliente){
        return dao.findByCliente(cliente);
    }
    public Iterable<Venta> findByUsuario(Persona usuario){
        return dao.findByUsuario(usuario);
    }
    public Iterable<Venta> findByFecha(LocalDate fecha){
        return dao.findByFecha(fecha);
    }
    
}
