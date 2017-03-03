package com.brasajava.service;

import com.brasajava.dao.CuentaDAO;
import com.brasajava.model.Cuenta;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Esta Clase hace el puente entre la aplicación y la base de datos.
 * @author Ricardo Maximino
 */
@Service
public class ServicioCuenta {
    
    @Autowired
    private CuentaDAO dao;
    
    /**
     * Guarda o actualiza una cuenta en la base de datos.
     * @param cuenta del tipo com.brasajava.model.Cuenta.
     * @return del tipo com.brasajava.model.Cuenta.
     */
    public Cuenta save(Cuenta cuenta){
        return dao.save(cuenta);
    }
    
    /**
     * Borra definitivamente una cuenta de la base de datos.
     * @param cuenta del tipo com.brasajava.model.Cuenta.
     */
    public void delete(Cuenta cuenta){
        dao.delete(cuenta);
    }
    
    /**
     * Retorna todas las cuentas de la base de datos.
     * @return del tipo java.lang.Iterable&lt;Cuenta&gt;.
     */
    public Iterable<Cuenta> findAll(){
        return dao.findAll();
    }
    
    /**
     * Busca todas la cuentas de la base de datos que tengan el campo fecha
     * igual al parametro.
     * @param fecha del tipo java.time.LocalDate.
     * @return del tipo java.lang.Iterable&lt;Cuenta&gt;.
     */
    public Iterable<Cuenta> findByFecha(LocalDate fecha){
        return dao.findByFecha(fecha);
    }
}
