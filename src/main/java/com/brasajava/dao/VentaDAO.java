package com.brasajava.dao;

import com.brasajava.model.Persona;
import com.brasajava.model.Venta;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Esta interfaz será usada por spring data para crear una class concreta.
 * @author Ricardo Maximino
 */
public interface VentaDAO extends CrudRepository<Venta, Long>{
    
    /**
     * Lista todas las venta de la base de datos cuyo cliente relacionado sea
     * igual al parametro.
     * @param cliente del tipo com.brasajava.model.Cliente.
     * @return del tipo java.util.List&lt;Venta&gt;.
     */
    List<Venta> findByCliente(Persona cliente);
    /**
     * Lista todas las venta de la base de datos cuyo usuario relacionado sea
     * igual al parametro.
     * @param usuario del tipo com.brasajava.model.Usuario.
     * @return del tipo java.util.List&lt;Venta&gt;.
     */
    List<Venta> findByUsuario(Persona usuario);
    /**
     * Lista todas las venta de la base de datos cuya fecha relacionado sea
     * igual al parametro.
     * @param fecha del tipo java.time.LocalDate.
     * @return del tipo java.util.List&lt;Venta&gt;.
     */
    List<Venta> findByFecha(LocalDate fecha);
}
