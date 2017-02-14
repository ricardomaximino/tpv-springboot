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

    @Override
    public Cliente save(Cliente cliente) {
        return dao.save(cliente);
    }

    @Override
    public void delete(Long id) {
        dao.delete(id);
    }

    @Override
    public Cliente findOne(Long id) {
        return dao.findOne(id);
    }

    @Override
    public Cliente findByNif(String nif) {
        return dao.findByNif(nif);
    }

    @Override
    public Iterable<Cliente> findAll() {
        return dao.findAll();
    }

    @Override
    public Iterable<Cliente> findByActivo(boolean isActivo) {
        return dao.findByActivo(isActivo);
    }

    @Override
    public Iterable<Cliente> findByNombre(String nombre) {
        return dao.findByNombre(nombre);
    }
    
    @Override
    public Iterable<Cliente> findByNombreLike(String nombre){
        return dao.findByNombreLike(nombre);
    }

    @Override
    public Iterable<Cliente> findByPrimerApellido(String primerApellido) {
        return dao.findByPrimerApellido(primerApellido);
    }

    @Override
    public Iterable<Cliente> findBySegundoApellido(String segundoApellido) {
        return dao.findBySegundoApellido(segundoApellido);
    }

    @Override
    public Iterable<Cliente> findByFechaNacimiento(LocalDate fechaDeNacimiento) {
        return dao.findByFechaNacimiento(fechaDeNacimiento);
    }

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
