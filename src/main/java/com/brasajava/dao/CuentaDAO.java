package com.brasajava.dao;

import com.brasajava.model.Cuenta;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Esta interfaz será usada por spring data para crear una class concreta.
 * @author Ricardo Maximino
 */
public interface CuentaDAO extends CrudRepository<Cuenta, Long>{
    
    /**
     * Lista todas las cuentas cuya fecha sea igual al parametro.
     * @param fecha del tipo java.time.LocalDate.
     * @return del tipo java.util.List&lt;Cuenta&gt;.
     */
    List<Cuenta> findByFecha(LocalDate fecha);
}
