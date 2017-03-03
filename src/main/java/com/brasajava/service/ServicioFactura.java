package com.brasajava.service;

import com.brasajava.dao.FacturaDAO;
import com.brasajava.model.Factura;
import com.brasajava.model.Persona;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Esta Clase hace el puente entre la aplicación y la base de datos.
 * @author Ricardo Maximino
 */
@Service
public class ServicioFactura {
    
    @Autowired
    private FacturaDAO dao;
    
    /**
     * Guarda o actualiza una factura en la base de datos.
     * @param factura del tipo com.brasajava.model.Factura.
     * @return del tipo com.brasajava.model.Factura.
     */
    public Factura save(Factura factura){
        return dao.save(factura);
    }
    
    /**
     * Borra definitivamente una factura de la base de datos.
     * @param factura del tipo com.brasajava.model.Factura.
     */
    public void delete(Factura factura){
        dao.delete(factura);
    }
    
    /**
     * Retorna todas las facturas de la base de datos.
     * @return del tipo java.lang.Iterable&lt;Factura&gt;.
     */
    public Iterable<Factura> findAll(){
        return dao.findAll();
    }
    
    /**
     * Busca todas las facturas en la base de datos que tengan el campo cliente
     * igual al parametro.
     * @param cliente del tipo com.brasajava.model.Persona.
     * @return del tipo java.lang.Iterable&lt;Factura&gt;.
     */
    public Iterable<Factura> findByCliente(Persona cliente){
        return dao.findByCliente(cliente);
    }
    
    /**
     * Busca todas las facturas en la base de datos que tengan el campo usuario
     * igual al parametro.
     * @param usuario del tipo com.brasajava.model.Persona.
     * @return del tipo java.lang.Iterable&lt;Factura&gt;.
     */
    public Iterable<Factura> findByUsuario(Persona usuario){
        return dao.findByUsuario(usuario);
    }
    
    /**
     * Busca todas las facturas en la base de datos que tengan el campo fecha
     * igual al parametro.
     * @param fecha del tipo java.time.LocalDate.
     * @return del tipo java.lang.Iterable&lt;Factura&gt;.
     */
    public Iterable<Factura> findByFecha(LocalDate fecha){
        return dao.findByFecha(fecha);
    }
}
