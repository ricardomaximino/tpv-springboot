package com.brasajava.service;

import com.brasajava.dao.UsuarioDAO;
import com.brasajava.model.Usuario;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Esta clase es la implementación de la interfaz
 * ServicioPersona&lt;Usuario&gt;.
 *
 * @author Ricardo Maximino
 */
@Service
public class ServicioUsuario implements ServicioPersona<Usuario> {

    @Autowired
    private UsuarioDAO dao;

    /**
     * Único constructor para crear una instancia desta clase.
     */
    public ServicioUsuario() {
    }

    /**
     * Guarda o actualiza un usuario el la base de datos.
     * @param usuario del tipo com.brasajava.model.Usuario.
     * @return del tipo com.brasajava.model.Usuario.
     */
    @Override
    public Usuario save(Usuario usuario) {
        return dao.save(usuario);
    }

    /**
     * Borra definitivamente un usuario de la base de datos.
     * @param id del tipo long.
     */
    @Override
    public void delete(Long id) {
        dao.delete(id);
    }

    /**
     * Busca un usuario por el id.
     * @param id del tipo long.
     * @return del tipo com.brasajava.model.Usuario.
     */
    @Override
    public Usuario findOne(Long id) {
        return dao.findOne(id);
    }

    /**
     * Busca un usuario por el nif.
     * @param nif del tipo java.lang.String.
     * @return del tipo com.brasajava.model.Usuario.
     */
    @Override
    public Usuario findByNif(String nif) {
        return dao.findByNif(nif);
    }

    /**
     * Retorna todos los usuarios de la base de datos.
     * @return del tipo java.lang.Iterable&lt;Usuario&gt;.
     */
    @Override
    public Iterable<Usuario> findAll() {
        return dao.findAll();
    }

    /**
     * Busca todos los usuarios de la base de datos que tengan el campo activo
     * igual al parametro.
     * @param isActivo del tipo boolean.
     * @return del tipo java.lang.Iterable&lt;Usuario&gt;.
     */
    @Override
    public Iterable<Usuario> findByActivo(boolean isActivo) {
        return dao.findByActivo(isActivo);
    }

    /**
     * Busca todos los usuarios de la base de datos que tengan el campo nombre
     * igual al parametro.
     * @param nombre del tipo java.lang.String.
     * @return del tipo java.lang.Iterable&lt;Usuario&gt;.
     */
    @Override
    public Iterable<Usuario> findByNombre(String nombre) {
        return dao.findByNombre(nombre);
    }

    /**
     * Busca todos los usuarios de la base de datos que tengan en el campo
     * nombre la secuencia de caracteres del parametro.
     * @param nombre del tipo java.lang.String.
     * @return del tipo java.lang.Iterable&lt;Usuario&gt;.
     */
    @Override
    public Iterable<Usuario> findByNombreLike(String nombre) {
        return dao.findByNombreLike(nombre);
    }

    /**
     * Busca todos los usuarios de la base de datos que tengan el campo
     * primerApellido igual al parametro.
     * @param primerApellido del tipo java.lang.String.
     * @return del tipo java.lang.Iterable&lt;Usuario&gt;.
     */
    @Override
    public Iterable<Usuario> findByPrimerApellido(String primerApellido) {
        return dao.findByPrimerApellido(primerApellido);
    }

    /**
     * Busca todos los usuarios de la base de datos que tengan el campo 
     * segundoApellido igual al parametro.
     * @param segundoApellido del tipo java.lang.String.
     * @return del tipo java.lang.Iterable&lt;Usuario&gt;.
     */
    @Override
    public Iterable<Usuario> findBySegundoApellido(String segundoApellido) {
        return dao.findBySegundoApellido(segundoApellido);
    }

    /**
     * Busca todos los usuarios de la base de datos que tengan el campo 
     * fechaDeNacimiento igual al parametro.
     * @param fechaDeNacimiento del tipo java.time.LocalDate.
     * @return del tipo java.lang.Iterable&lt;Usuario&gt;.
     */
    @Override
    public Iterable<Usuario> findByFechaNacimiento(LocalDate fechaDeNacimiento) {
        return dao.findByFechaNacimiento(fechaDeNacimiento);
    }

    /**
     * Busca todos los usuarios de la base de datos que tengan en el campo
     * fechaDeNacimiento el mes igual al parametro.
     * @param mes del tipo java.time.Month.
     * @return del tipo java.lang.Iterable&lt;Usuario&gt;.
     */
    @Override
    public Iterable<Usuario> cumpleañerosDelMes(Month mes) {
        List<Usuario> list = new ArrayList<>();
        for (Usuario usu : dao.findAll()) {
            if (usu.getFechaNacimiento() != null) {
                if (usu.getFechaNacimiento().getMonth().equals(mes)) {
                    list.add(usu);
                }
            }
        }
        return list;
    }
}
