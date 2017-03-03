package com.brasajava.model;

import com.brasajava.util.LocalDateAttributeConverter;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Esta clase representa una venta.
 * @author Ricardo Maximino
 */
@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int cantidad;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRODUCTO_ID")
    private Producto producto;
    @OneToOne(fetch = FetchType.EAGER,targetEntity = Usuario.class)
    @JoinColumn( name = "USUARIO_ID")
    private Persona usuario;
    @OneToOne(fetch = FetchType.EAGER,targetEntity = Cliente.class)
    @JoinColumn( name = "CLIENTE_ID")
    private Persona cliente;
    private BigDecimal total;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CUENTA_ID")
    private Cuenta cuenta;
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate fecha;
    
    /**
     * Único constructo para crear una instancia desta clase.
     */
    public Venta(){
        total = new BigDecimal("0.00");
        fecha = LocalDate.now();
    }

    /**
     * Retorna el valor de la variable id.
     * @return del tipo long.
     */
    public long getId() {
        return id;
    }

    /**
     * Configura el valor de la variable id.
     * @param id del tipo long.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retorna el valor de la variable cantidad.
     * @return del tipo int.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Retorna el valor de la variable producto.
     * @return del tipo com.brasajava.model.Producto.
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Retorna el valor de la variable Usuario.
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
     * Retorna el valor de la variable cliente.
     * @return del tipo com.brasajava.model.Persona.
     */
    public Persona getCliente() {
        return cliente;
    }

    /**
     * Configura el valor de la variable cliente.
     * @param cliente del tipo com.brasajava.model.Persona.
     */
    public void setCliente(Persona cliente) {
        this.cliente = cliente;
    }

    /**
     * Retorna el valor de la variable total.
     * @return del tipo java.math.BigDecimal.
     */
    public BigDecimal getTotal() {
        return total;
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
     * Único metodo necesario para configurar una venta.
     * @param cantidad del tipo int.
     * @param producto del tipo com.brasajava.model.Producto.
     * 
     * <p>Se indica la cantida del producto que se ha vendido y el producto que
     * se ha vendido y yá, automaticamente se suma el total.</p>
     */
    public void setVenta(int cantidad,Producto producto){
        if(cantidad > 0 && producto != null){
            this.cantidad = cantidad;
            this.producto = producto;
            total = producto.getPrecioMasIva().setScale(2,BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(cantidad));
            
        }
    }

    /**
     * Retorna el valor de la variable fecha.
     * @return del tipo java.time.LocalDate.
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Configura el valor de la variable fecha.
     * @param fecha del tipo java.time.LocalDate.
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * Override el hashCode para que utilice el id para generar el hashCode.
     * @return del tipo int.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    /**
     * Override equals para que utilice el id como parametro para definir se
     * un objeto Venta es igual a otro.
     * @param obj del tipo Objeto.
     * @return del tipo boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Venta other = (Venta) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
