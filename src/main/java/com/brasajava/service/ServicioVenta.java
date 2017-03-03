package com.brasajava.service;

import com.brasajava.dao.VentaDAO;
import com.brasajava.model.Persona;
import com.brasajava.model.Venta;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Esta Clase hace el puente entre la aplicación y la base de datos.
 * @author Ricardo Maximino
 */
@Service
public class ServicioVenta {
    
    @Autowired
    private VentaDAO dao;
    
    /**
     * Guarda o actualiza la venta en la base de datos
     * @param venta del tipo com.brasajava.model.Venta.
     * @return del tipo com.brasajava.model.Venta.
     */
    public Venta save(Venta venta){
        return dao.save(venta);
    }
    
    /**
     * Borra definitivamente la venta pasada por parametro de la base de datos.
     * @param venta del tipo com.brasajava.model.Venta.
     */
    public void delete(Venta venta){
        dao.delete(venta);
    }
    /**
     * Retorna todas las ventas registradas en la base de datos.
     * @return del tipo java.lang.Iterable&lt;Venta&gt;.
     */
    public Iterable<Venta> findAll(){
        return dao.findAll();
    }
    /**
     * Retorna todas las ventas registradas en la base de datos cuyo cliente
     * relacionada sea igual al parametro.
     * @param cliente del tipo com.brasajava.model.Persona.
     * @return del tipo java.lang.Iterable&lt;Venta&gt;.
     */
    public Iterable<Venta> findByCliente(Persona cliente){
        return dao.findByCliente(cliente);
    }
    /**
     * Retorna todas las venta registradas en la base de datos cuyo usuario
     * relacionado sea igual al parametro.
     * @param usuario del tipo com.brasajava.model.Persona.
     * @return del tipo java.lang.Iterable&lt;Venta&gt;.
     */
    public Iterable<Venta> findByUsuario(Persona usuario){
        return dao.findByUsuario(usuario);
    }
    /**
     * Retorna todas las venta registradas en la base de datos cuya fecha
     * sea igual al parametro.
     * @param fecha del tipo java.time.LocalDate.
     * @return del tipo java.lang.Iterable&lt;Venta&gt;.
     */
    public Iterable<Venta> findByFecha(LocalDate fecha){
        return dao.findByFecha(fecha);
    }
}
