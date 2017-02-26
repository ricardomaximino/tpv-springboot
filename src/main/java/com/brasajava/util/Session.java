/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brasajava.util;

import com.brasajava.model.Cuenta;
import com.brasajava.model.Persona;

/**
 *
 * @author Ricardo
 */
public class Session {
    private Persona cliente;
    private Persona usuario;
    private Cuenta cuenta;
    
    public Session(){}
    
    public Session(Persona usuario,Persona cliente){
        this.usuario = usuario;
        this.cliente = cliente;
    }
    public Session(Persona usuario){
        this.usuario = usuario;
    }

    public Persona getCliente() {
        return cliente;
    }

    public void setCliente(Persona cliente) {
        this.cliente = cliente;
    }

    public Persona getUsuario() {
        return usuario;
    }

    public void setUsuario(Persona usuario) {
        this.usuario = usuario;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
    
}
