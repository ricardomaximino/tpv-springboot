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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne(fetch = FetchType.EAGER,targetEntity = Cliente.class)
    @JoinColumn(name = "CLIENTE_ID")
    private Persona cliente;
    @OneToOne(fetch = FetchType.EAGER,targetEntity = Usuario.class)
    @JoinColumn(name = "USUARIO_ID")
    private Persona usuario;
    @OneToMany(mappedBy = "factura", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Cuenta> cuentas;
    private BigDecimal total;
    private boolean cobrada;

    public Factura(){
        cuentas = new ArrayList();
        total = new BigDecimal("0.00");
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public boolean isCobrada() {
        return cobrada;
    }

    public void setCobrada(boolean cobrada) {
        this.cobrada = cobrada;
    }    
    
}
