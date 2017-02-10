/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brasajava.dao;

import com.brasajava.model.Grupo;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Ricardo
 */
public interface GrupoDAO extends CrudRepository<Grupo, Long>{
        
}
