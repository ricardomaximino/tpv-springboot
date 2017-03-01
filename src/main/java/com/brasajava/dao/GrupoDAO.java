package com.brasajava.dao;

import com.brasajava.model.Grupo;
import org.springframework.data.repository.CrudRepository;

/**
 * Esta interfaz será usada por spring data para crear una class concreta.
 * @author Ricardo Maximino
 */
public interface GrupoDAO extends CrudRepository<Grupo, Long>{
        
}
