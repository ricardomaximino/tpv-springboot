package com.brasajava.dao;

import com.brasajava.model.Producto;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Esta interfaz será usada por spring data para crear una class concreta.
 * @author Ricardo Maximino
 */
public interface ProductoDAO extends CrudRepository<Producto, Long>{
    /**
     * Lista todos los producto cuyo nombre sea igual al parametro.
     * @param nombre del tipo java.lang.String.
     * @return del tipo java.util.List&lt;Producto&gt;.
     */
    List<Producto> findByNombre(String nombre);
    /**
     * Lista todos los producto cuyo nombre contenga la secuencia de caracteres
     * del parametro.
     * @param nombre del tipo java.lang.String.
     * @return del tipo java.util.List&lt;Producto&gt;.
     */
    List<Producto> findByNombreLikeIgnoreCase(String nombre);
    
    /**
     * Lista todos los producto cuyo el precio este entre los valores pasados 
     * por parametro.
     * @param valor1 del tipo java.math.BigDecimal.
     * @param valor2 del tipo java.math.BigDecimal.
     * @return del tipo java.util.List&lt;Producto&gt;.
     */
    List<Producto> findByPrecioMasIvaBetween(BigDecimal valor1,BigDecimal valor2);
    
    
}
