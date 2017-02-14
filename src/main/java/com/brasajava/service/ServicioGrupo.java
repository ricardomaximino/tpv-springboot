/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brasajava.service;

import com.brasajava.dao.GrupoDAO;
import com.brasajava.model.Grupo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ricardo
 */
@Service
public class ServicioGrupo {
    @Autowired
    private GrupoDAO dao;
    
    public Grupo save(Grupo grupo){
        return dao.save(grupo);
    }
    
    public void delete(Grupo grupo){
        dao.delete(grupo);
    }
    
    public Grupo findOne(Long id){
        return dao.findOne(id);
    }
    public Iterable<Grupo> findAll(){
        return dao.findAll();
    }
}
