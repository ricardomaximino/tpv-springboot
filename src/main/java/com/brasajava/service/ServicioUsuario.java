package com.brasajava.service;

import com.brasajava.dao.UsuarioDAO;
import com.brasajava.model.Cliente;
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

    public ServicioUsuario() {
    }
@Override
    public Usuario save(Usuario usuario) {
        return dao.save(usuario);
    }

    @Override
    public void delete(Long id) {
        dao.delete(id);
    }

    @Override
    public Usuario findOne(Long id) {
        return dao.findOne(id);
    }

    @Override
    public Usuario findByNif(String nif) {
        return dao.findByNif(nif);
    }

    @Override
    public Iterable<Usuario> findAll() {
        return dao.findAll();
    }

    @Override
    public Iterable<Usuario> findByActivo(boolean isActivo) {
        return dao.findByActivo(isActivo);
    }

    @Override
    public Iterable<Usuario> findByNombre(String nombre) {
        return dao.findByNombre(nombre);
    }

    @Override
    public Iterable<Usuario> findByNombreLike(String nombre){
        return dao.findByNombreLike(nombre);
    }

    @Override
    public Iterable<Usuario> findByPrimerApellido(String primerApellido) {
        return dao.findByPrimerApellido(primerApellido);
    }

    @Override
    public Iterable<Usuario> findBySegundoApellido(String segundoApellido) {
        return dao.findBySegundoApellido(segundoApellido);
    }

    @Override
    public Iterable<Usuario> findByFechaNacimiento(LocalDate fechaDeNacimiento) {
        return dao.findByFechaNacimiento(fechaDeNacimiento);
    }

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
