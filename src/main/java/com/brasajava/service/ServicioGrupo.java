package com.brasajava.service;

import com.brasajava.dao.GrupoDAO;
import com.brasajava.model.Grupo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Esta Clase hace el puente entre la aplicación y la base de datos.
 * @author Ricardo Maximino
 */
@Service
public class ServicioGrupo {
    @Autowired
    private GrupoDAO dao;
    
    /**
     * Guarda o actualiza un grupo en la base de datos.
     * @param grupo del tipo com.brasajava.model.Grupo.
     * @return del tipo com.brasajava.model.Grupo.
     */
    public Grupo save(Grupo grupo){
        return dao.save(grupo);
    }
    
    /**
     * Borra definitivamente un grupo de la base de datos.
     * @param grupo del tipo com.brasajava.model.Grupo.
     */
    public void delete(Grupo grupo){
        dao.delete(grupo);
    }
    
    /**
     * Busca un grupo por el id.
     * @param id del tipo long.
     * @return del tipo com.brasajava.model.Grupo.
     */
    public Grupo findOne(Long id){
        return dao.findOne(id);
    }
    /**
     * Retorna todos los grupos de la base de datos.
     * @return del tipo java.lang.Iterable&lt;Grupo&gt;.
     */
    public Iterable<Grupo> findAll(){
        return dao.findAll();
    }
}
