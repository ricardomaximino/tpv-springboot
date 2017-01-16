package com.brasajava.service;

import java.time.LocalDate;
import java.time.Month;

/**
 * Esta interfaz es para manejo simples de personas.
 * @author Ricardo Maximino
 * <p>Este interfaz es igual que la interfaz PersonaDAO, tiene los mismos 
 * metodos.</p>
 */
public interface ServicioPersona<T> {
    
     /**
      * Este metodo fue pensado para añadir y atualizar un registro en la base de 
      * datos.
      * @param persona del tipo especifica utilizando Generics &lt;T&gt;.
      * @return persona del tipo especifica utilizando Generics &lt;T&gt;.
      * <p>La idea general de este metodo es:</p>
      * dao.añadirNuevoRegistro(parametro);
      */
     T save(T persona);
     
     
     /**
      * Este metodo fue pensado para borrar un registro en la base de datos.
      * @param id del tipo Long, la llave primaria de la tabla.
      */
     void delete(Long id);
     
     /**
      * Este metodo fue pensado para buscar el registro de una persona en la
      * base de datos utilizando el parametro introducido.
      * @param id del tipo String, identificador único en la tabla.
      * @return del tipo especificado utilizando Generics &lt;T&gt;.
      * <p>La idea general de este metodo es:</p>
      * dao.buscarRegistroPorNIF(parametro)
      */
     T findOne(Long id);
     
     /**
      * Este metodo fue pensado para buscar el registro de una persona en la
      * base de datos utilizando el parametro introducido.
      * @param nif del tipo String, identificador único en la tabla.
      * @return del tipo especificado utilizando Generics &lt;T&gt;.
      * <p>La idea general de este metodo es:</p>
      * dao.buscarRegistroPorNIF(parametro)
      */
     T findByNif(String nif);
     
     /**
      * Este metodo fue pensado para listar todos los registros de la tabla
      * referente a la clase especificada utilizando Generics &lt;T&gt;.
      * @return del tipo java.lang.Iterable&lt;T&gt;.
      */
     Iterable<T> findAll();
     
     /**
      * Este metodo fue pensado para listar todos los registros de la tabla
      * referente a la clase especificada utilizando Generics &lt;T&gt;, y 
      * filtrado por el parametro introducido.
      * @param nombre del tipo String;
      * @return del tipo java.lang.Iterable&lt;T&gt;.
      * <p>La idea general de este metodo es:</p>
      * dao.buscarRegistroPorNombre(parametro);
      */
     Iterable<T> findByNombre(String nombre);     
     
     /**
      * Este metodo fue pensado para listar todos los registros de la tabla
      * referente a la clase especificada utilizando Generics &lt;T&gt;, y 
      * filtrado por el parametro introducido.
      * @param primerApellido del tipo String;
      * @return del tipo java.lang.Iterable&lt;T&gt;.
      * <p>La idea general de este metodo es:</p>
      * dao.buscarRegistroPorPrimerApellido(parametro);
      */
     Iterable<T> findByPrimerApellido(String primerApellido);
     
     /**
      * Este metodo fue pensado para listar todos los registros de la tabla
      * referente a la clase especificada utilizando Generics &lt;T&gt;, y 
      * filtrado por el parametro introducido.
      * @param segundoApellido del tipo String;
      * @return del tipo java.lang.Iterable&lt;T&gt;.
      * <p>La idea general de este metodo es:</p>
      * dao.buscarRegistroPorSegundoApellido(parametro);
      */
     Iterable<T> findBySegundoApellido(String segundoApellido);
     
     /**
      * Este metodo fue pensado para listar todos los registros de la tabla
      * referente a la clase especificada utilizando Generics &lt;T&gt;, y 
      * filtrado por el parametro ativo indicado por el parametro.
     *  @param isActivo del tipo boolean.
      * @return del tipo java.lang.Iterablet&lt;T&gt;.
      * <p>La idea general de este metodo es:</p>
      * dao.buscarRegistrosDadoDeAlta();
      */
     Iterable<T> findByActivo(boolean isActivo);
     
     /**
      * Este metodo fue pensado para listar todos los registros de la tabla
      * referente a la clase especificada utilizando Generics &lt;T&gt;, y 
      * filtrado por el parametro introducido.
      * @param fechaDeNacimiento del tipo java.time.LocalDate.
      * @return del tipo java.lang.Iterable&lt;T&gt;.
      * <p>La idea general de este metodo es:</p>
      * dao.cumpleañerosDelMes((parametro);
      */
     Iterable<T> findByFechaNacimiento(LocalDate fechaDeNacimiento);
     
     /**
      * Este metodo fue pensado para listar todos los registros de la tabla
      * referente a la clase especificada utilizando Generics &lt;T&gt;, y 
      * filtrado por el parametro introducido.
      * @param mes del tipo java.time.Month.
      * @return del tipo java.lang.Iterable&lt;T&gt;.
      * <p>La idea general de este metodo es:</p>
      * dao.cumpleañerosDelMes((parametro);
      */
     Iterable<T> cumpleañerosDelMes(Month mes);

}
