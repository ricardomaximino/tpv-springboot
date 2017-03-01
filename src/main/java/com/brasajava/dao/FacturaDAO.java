package com.brasajava.dao;

import com.brasajava.model.Factura;
import com.brasajava.model.Persona;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Esta interfaz será usada por spring data para crear una class concreta.
 * @author Ricardo Maximino
 */
public interface FacturaDAO extends CrudRepository<Factura, Long>{
    /**
     * Lista todas las facturas de la base de datos cuyo cliente relacionado sea
     * igual al parametro.
     * @param cliente del tipo com.brasajava.model.Cliente.
     * @return del tipo java.util.List&lt;Factura&gt;.
     */
    List<Factura> findByCliente(Persona cliente);
    /**
     * Lista todas las facturas de la base de datos cuyo usuario relacionado sea
     * igual al parametro.
     * @param usuario del tipo com.brasajava.model.Usuario.
     * @return del tipo java.util.List&lt;Factura&gt;.
     */
    List<Factura> findByUsuario(Persona usuario);
    
    /**
     * Lista todas las facturas de la base de datos cuya fecha relacionado sea
     * igual al parametro.
     * @param fecha del tipo java.time.LocalDate.
     * @return del tipo java.util.List&lt;Factura&gt;.
     */
    List<Factura> findByFecha(LocalDate fecha);
    
}
