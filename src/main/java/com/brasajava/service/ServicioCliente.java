package com.brasajava.service;

import com.brasajava.dao.ClienteDAO;
import com.brasajava.model.Cliente;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Esta clase es la implementación de la interfaz
 * ServicioPersona&lt;Cliente&gt;.
 *
 * @author Ricardo Maximino
 */
@Service
public class ServicioCliente implements ServicioPersona<Cliente> {

    @Autowired
    private ClienteDAO dao;

    /**
     * Guarda en la base de datos el cliente pasado como parametro.
     * @param cliente del tipo com.brasajava.model.Cliente.
     * @return del tipo com.brasajava.model.Cliente.
     */
    @Override
    public Cliente save(Cliente cliente) {
        return dao.save(cliente);
    }

    /**
     * Borrar definitivamente el registro cuyo id sea igual al parametro id.
     * @param id del tipo long.
     */
    @Override
    public void delete(Long id) {
        dao.delete(id);
    }

    /**
     * Busca el la base de datos por id igual al parametro.
     * @param id del tipo long.
     * @return del tipo com.brasajava.model.Cliente.
     */
    @Override
    public Cliente findOne(Long id) {
        return dao.findOne(id);
    }

    /**
     * Busca el la base de datos por nif igual al parametro.
     * @param nif del tipo java.lang.String.
     * @return del tipo com.brasajava.model.Cliente.
     */
    @Override
    public Cliente findByNif(String nif) {
        return dao.findByNif(nif);
    }

    /**
     * Lista todos los registros de cliente en la base de datos.
     * @return java.lang.Iterable&lt;del tipo com.brasajava.model.Cliente&gt;.
     */
    @Override
    public Iterable<Cliente> findAll() {
        return dao.findAll();
    }

    /**
     * Lista todos los registros de cliente en la base de datos cuya propriedade
     * activo sea igual al parametro.
     * @param isActivo del tipo boolean.
     * @return java.lang.Iterable&lt;del tipo com.brasajava.model.Cliente&gt;.
     */
    @Override
    public Iterable<Cliente> findByActivo(boolean isActivo) {
        return dao.findByActivo(isActivo);
    }

    /**
     * Lista todos los registros de clientes en la base de datos cuya propriedade
     * nombre sea igual al parametro.
     * @param nombre del tipo java.lang.String.
     * @return java.lang.Iterable&lt;del tipo com.brasajava.model.Cliente&gt;.
     */
    @Override
    public Iterable<Cliente> findByNombre(String nombre) {
        return dao.findByNombre(nombre);
    }
    
    /**
     * Lista todos los registros de clientes en la base de datos cuya propriedade
     * nombre contena la secuencia de caracteres del parametro.
     * @param nombre del tipo java.lang.String.
     * @return java.lang.Iterable&lt;del tipo com.brasajava.model.Cliente&gt;.
     */
    @Override
    public Iterable<Cliente> findByNombreLike(String nombre){
        return dao.findByNombreLike(nombre);
    }

    /**
     * Lista todos los registros de clientes en la base de datos cuya propriedade
     * primerApellido sea igual al parametro.
     * @param primerApellido del tipo java.lang.String.
     * @return java.lang.Iterable&lt;del tipo com.brasajava.model.Cliente&gt;.
     */
    @Override
    public Iterable<Cliente> findByPrimerApellido(String primerApellido) {
        return dao.findByPrimerApellido(primerApellido);
    }

    /**
     * Lista todos los registros de clientes en la base de datos cuya propriedade
     * segundoApellido sea igual al parametro.
     * @param segundoApellido del tipo java.lang.String.
     * @return java.lang.Iterable&lt;del tipo com.brasajava.model.Cliente&gt;.
     */
    @Override
    public Iterable<Cliente> findBySegundoApellido(String segundoApellido) {
        return dao.findBySegundoApellido(segundoApellido);
    }

     /**
     * Lista todos los registros de clientes en la base de datos cuya propriedade
     * fechaDeNacimiento sea igual al parametro.
     * @param fechaDeNacimiento del tipo java.lang.String.
     * @return java.lang.Iterable&lt;del tipo com.brasajava.model.Cliente&gt;.
     */
    @Override
    public Iterable<Cliente> findByFechaNacimiento(LocalDate fechaDeNacimiento) {
        return dao.findByFechaNacimiento(fechaDeNacimiento);
    }

    /**
     * Lista todos los registros de clientes en la base de datos cuya propriedade
     * fechaDeNacimiento tenga el mes igual al parametro.
     * @param mes del tipo java.lang.String.
     * @return java.lang.Iterable&lt;del tipo com.brasajava.model.Cliente&gt;.
     */
    @Override
    public Iterable<Cliente> cumpleañerosDelMes(Month mes) {
        List<Cliente> list = new ArrayList<>();
        for (Cliente cli : dao.findAll()) {
            if (cli.getFechaNacimiento() != null) {
                if (cli.getFechaNacimiento().getMonth().equals(mes)) {
                    list.add(cli);
                }
            }
        }
        return list;
    }
}
