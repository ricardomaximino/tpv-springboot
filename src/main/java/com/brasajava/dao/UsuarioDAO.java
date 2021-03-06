package com.brasajava.dao;

import com.brasajava.model.Usuario;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Esta interfaz es de operaciones simples para una persona, fue creada para 
 * quitar la dependencia de un DAO especifico para los servicios.
 * @author Ricardo Maximino
 */
public interface UsuarioDAO extends CrudRepository<Usuario, Long>{
    /**
     * Este metodo esta pensado para buscar un registro en la tabla 
     * correspondiente de la base de datos, donde la columna nif sea igual al
     * parametro introducido.
     * @param nif del tipo String, Identificador único en la tabla de la base 
     * de datos.
     * @return del tipo com.brasajava.model.Usuario.
     */     
    Usuario findByNif(String nif);
  
    /**
     * Este metodo esta pensado para listar todos lo registros de la tabla 
     * correspondiente de la base de datos donde la columna primerApellido sea
     * igual al parametro introducido.
     * @param primerApellido del tipo String.
     * @return del tipo java.util.List&lt;com.brasajava.model.Usuario&gt;.
     */
    List<Usuario> findByPrimerApellido(String primerApellido);
    
    /**
     * Este metodo esta pensado para listar todos lo registros de la tabla 
     * correspondiente de la base de datos donde la columna segundoApellido sea
     * igual al parametro introducido.
     * @param segundoApellido del tipo String.
     * @return del tipo java.util.List&lt;com.brasajava.model.Usuario&gt;.
     */
    List<Usuario> findBySegundoApellido(String segundoApellido);
    
    /**
     * Este metodo esta pensado para listar todos lo registros de la tabla 
     * correspondiente de la base de datos donde la columna nombre sea
     * igual al parametro introducido.
     * @param nombre del tipo String.
     * @return del tipo java.util.List&lt;com.brasajava.model.Usuario&gt;.
     */
    List<Usuario> findByNombre(String nombre);
    
    /**
     * Este metodo esta pensado para listar todos lo registros de la tabla 
     * correspondiente de la base de datos donde la columna nombre contenga
     * una sequencia de caracteres igual al parametro introducido.
     * @param nombre del tipo String.
     * @return del tipo java.util.List&lt;com.brasajava.model.Usuario&gt;.
     */
    List<Usuario> findByNombreLike(String nombre);
    
    /**
     * Este metodo esta pensado para listar todos lo registros de la tabla 
     * correspondiente de la base de datos donde la columna activo sea
     * igual al parametro introducido.
     * @param activo del tipo boolean.
     * @return del tipo java.util.List&lt;com.brasajava.model.Usuario&gt;.
     */
    List<Usuario> findByActivo(boolean activo);
    
    /**
     * Este metodo esta pensado para listar todos lo registros de la tabla 
     * correspondiente de la base de datos donde la columna fechaNacimiento sea
     * igual al parametro introducido.
     * @param fechaNacimiento del tipo java.time.LocalDate.
     * @return del tipo java.util.List&lt;com.brasajava.model.Usuario&gt;.
     */
    List<Usuario> findByFechaNacimiento(LocalDate fechaNacimiento);
}
