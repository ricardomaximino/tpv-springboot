package com.brasajava.model;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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
    private Persona vendedor;
    private BigDecimal total;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CUENTA_ID")
    private Cuenta cuenta;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public Persona getVendedor() {
        return vendedor;
    }

    public void setVendedor(Persona vendedor) {
        this.vendedor = vendedor;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public void setVenta(int cantidad,Producto producto){
        if(cantidad > 0 && producto != null){
            this.cantidad = cantidad;
            this.producto = producto;
            total = producto.getPrecioMasIva().setScale(2,BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(cantidad));
            
        }
    }
}
