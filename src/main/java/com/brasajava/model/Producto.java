package com.brasajava.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * Esta clase representa un producto.
 *
 * @author Ricardo Maximino
 */
@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nombre;
    private String descripcion;
    private int almacen;
    @ManyToMany(mappedBy = "productos", fetch = FetchType.EAGER)
    private List<Grupo> grupos;
    private BigDecimal custo;
    private double margen;
    private double iva;
    private BigDecimal precioSinIva;
    private BigDecimal precioMasIva;
    private boolean activo;
    private String image;

    /**
     * Único constructor para crear una instancia desta clase.
     */
    public Producto() {
        grupos = new ArrayList();
    }

    /**
     * Retorna el valor de la variable id.
     *
     * @return del tipo long.
     */
    public long getId() {
        return id;
    }

    /**
     * Configura el valor de la variable id.
     *
     * @param id del tipo long.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retorna el valor de la variable nombre.
     *
     * @return del tipo java.lang.String.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Configura el valor de la variable nombre.
     *
     * @param nombre del tipo java.lang.String.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Retorna el valor de la variable descripcion.
     *
     * @return del tipo java.lang.String.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Configura el valor de la variable descripcion.
     *
     * @param descripcion del tipo java.lang.String.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Retorna el valor de la variable almacen.
     *
     * @return del tipo int.
     */
    public int getAlmacen() {
        return almacen;
    }

    /**
     * Configura el valor de la variable almacen.
     *
     * @param almacen del tipo int.
     */
    public void setAlmacen(int almacen) {
        this.almacen = almacen;
    }

    /**
     * Retorna el valor de la variable grupos.
     *
     * @return del tipo java.util.List&lt;Grupo&gt;.
     */
    public List<Grupo> getGrupos() {
        return grupos;
    }

    /**
     * Configura el valor de la variable grupos.
     * @param grupos del tipo java.util.List&lt;Grupo&gt;.
     */
    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    /**
     * Retorna el valor de la variable custo.
     * @return del tipo java.math.BigDecimal.
     */
    public BigDecimal getCusto() {
        return custo;
    }

    /**
     * Configura el valor de la variable custo.
     * @param custo del tipo java.math.BigDecimal.
     */
    public void setCusto(BigDecimal custo) {
        this.custo = custo;
    }

    /**
     * Retorna el valor de la variable margen.
     * @return del tipo double.
     */
    public double getMargen() {
        return margen;
    }

    /**
     * Configura el valor de la variable margen.
     * @param margen del tipo double.
     */
    public void setMargen(double margen) {
        this.margen = margen;
    }

    /**
     * Retorna el valor de la variable iva.
     * @return del tipo double.
     */
    public double getIva() {
        return iva;
    }

    /**
     * Configura el valor de la variable iva.
     * @param iva del tipo double.
     */
    public void setIva(double iva) {
        this.iva = iva;
    }

    /**
     * Retorna el valor de la variable precioSinIva.
     * @return del tipo java.math.BigDecimal.
     */
    public BigDecimal getPrecioSinIva() {
        return precioSinIva;
    }

    /**
     * Configura el valor de la variable precioSinIva.
     * @param precioSinIva del tipo java.math.BigDecimal.
     */
    public void setPrecioSinIva(BigDecimal precioSinIva) {
        this.precioSinIva = precioSinIva;
    }

    /**
     * Retorna el valor de la variable precioMasIva.
     * @return del tipo java.math.BigDecimal.
     */
    public BigDecimal getPrecioMasIva() {
        return precioMasIva;
    }

    /**
     * Configura el valor de la variable precioMasIva.
     * @param precioMasIva del tipo java.math.BigDecimal.
     */
    public void setPrecioMasIva(BigDecimal precioMasIva) {
        this.precioMasIva = precioMasIva;
    }

    /**
     * Retorna el valor de la variable activo.
     * @return del tipo boolean.
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Configura el valor de la variable activo.
     * @param activo del tipo boolean.
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Retorna el valor de la variable image.
     * @return del tipo java.lang.String.
     * Las imagenes no se guardaran el la base de datos, se guardará solamente
     * el nombre del archivo eje.: image.png y la aplicación siempre buscará esta
     * imagen en la carpeta images de la aplicación.
     */
    public String getImage() {
        return image;
    }

    /**
     * Configura el valor de la variable image.
     * @param image del tipo java.lang.String.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Override hashCode para que utilice id para generar el hashCode.
     * @return del tipo int.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    /**
     * Override equals para que utilice el id para definir se una instancia
     * de la clase Producto es igual a otra.
     * @param obj del tipo java.lang.Object.
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
        final Producto other = (Producto) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
