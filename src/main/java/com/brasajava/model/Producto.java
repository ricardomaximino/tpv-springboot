package com.brasajava.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String descripcion;
    private int almacen;
    @ManyToMany(mappedBy = "productos")
    private List<Grupo> grupos;
    private BigDecimal custo;
    private double margen;
    private double iva;
    private BigDecimal precioSinIva;
    private BigDecimal precioMasIva;
    /*@ManyToMany(targetEntity = PromocionLeve3X2.class)
    @JoinTable(name = "PRODUCTO_PROMOCION",
            joinColumns = @JoinColumn(name = "PRODUCTO_ID",referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "PROMOCION_ID", referencedColumnName = "ID"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Promocion> promociones;*/
    private boolean activo;
    
    public Producto(){
        grupos = new ArrayList();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getAlmacen() {
        return almacen;
    }

    public void setAlmacen(int almacen) {
        this.almacen = almacen;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public BigDecimal getCusto() {
        return custo;
    }

    public void setCusto(BigDecimal custo) {
        this.custo = custo;
    }

    public double getMargen() {
        return margen;
    }

    public void setMargen(double margen) {
        this.margen = margen;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public BigDecimal getPrecioSinIva() {
        return precioSinIva;
    }

    public void setPrecioSinIva(BigDecimal precioSinIva) {
        this.precioSinIva = precioSinIva;
    }

    public BigDecimal getPrecioMasIva() {
        return precioMasIva;
    }

    public void setPrecioMasIva(BigDecimal precioMasIva) {
        this.precioMasIva = precioMasIva;
    }

    /*public List<Promocion> getPromociones() {
        return promociones;
    }

    public void setPromociones(List<Promocion> promociones) {
        this.promociones = promociones;
    }*/

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    
    
}
