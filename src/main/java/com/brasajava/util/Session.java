package com.brasajava.util;

import com.brasajava.model.Cuenta;
import com.brasajava.model.Factura;
import com.brasajava.model.Persona;

/**
 * Esta clase representa la sesión, todo que deva ser sabido el la sesión se
 * podrá acceder desde la session.
 * @author Ricardo Maximino
 */
public class Session {
    private Persona cliente;
    private Persona usuario;
    private Cuenta cuenta;
    private Factura factura;
    
    /**
     * Constuctor sin argumento, esta clase es realmente un POJO.
     */
    public Session(){}
    

    /**
     * Retorna el valor de la variable cliente.
     * @return del tipo com.brasajava.model.Persona.
     */
    public Persona getCliente() {
        return cliente;
    }

    /**
     * Configura la variable cliente.
     * @param cliente del tipo com.brasajava.model.Persona.
     */
    public void setCliente(Persona cliente) {
        this.cliente = cliente;
    }

    /**
     * Retorna el valor de la variable usuario.
     * @return del tipo com.brasajava.model.Persona.
     */
    public Persona getUsuario() {
        return usuario;
    }

    /**
     * Configura el valor de la variable usuario.
     * @param usuario del tipo com.brasajava.model.Persona.
     */
    public void setUsuario(Persona usuario) {
        this.usuario = usuario;
    }

    /**
     * Retorna el valor de la variable cuenta.
     * @return del tipo com.brasajava.model.Cuenta.
     */
    public Cuenta getCuenta() {
        return cuenta;
    }

    /**
     * Configura el valor de la variable cuenta.
     * @param cuenta del tipo com.brasajava.model.Cuenta.
     */
    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * Retorna el valor de la variable factura.
     * @return del tipo com.brasajava.model.Factura.
     */
    public Factura getFactura() {
        return factura;
    }

    /**
     * Configura el valor de la variable factura.
     * @param factura del tipo com.brasajava.model.Factura.
     */
    public void setFactura(Factura factura) {
        this.factura = factura;
    }
}
