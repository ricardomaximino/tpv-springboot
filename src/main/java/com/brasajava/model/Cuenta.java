package com.brasajava.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nombre;
    @OneToMany(mappedBy = "cuenta",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Venta> ventas;
    private BigDecimal total;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FACTURA_ID")
    private Factura factura;
    @Transient
    private boolean ticket;
    private boolean cobrada;
    
    public Cuenta(){
        ventas = new ArrayList();
        total = new BigDecimal("0.00");
    }
    public Cuenta(String nombre){
        this();
        this.nombre = nombre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public boolean isTicket() {
        return ticket;
    }

    public void setTicket(boolean ticket) {
        this.ticket = ticket;
    }

    public boolean isCobrada() {
        return cobrada;
    }

    public void setCobrada(boolean cobrada) {
        this.cobrada = cobrada;
    }
    
    public void addVenta(Venta venta){
        ventas.add(venta);
        total = total.add(venta.getTotal());
    }
    
    public void removeVenta(Venta venta){
        if(ventas.contains(venta)){
            ventas.remove(venta);
            total = total.subtract(venta.getTotal());
        }
    }
}
