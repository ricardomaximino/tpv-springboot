package com.brasajava.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * Esta clase representa un grupo.
 * @author Ricardo Maximino
 */
@Entity
public class Grupo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nombre;
    private String descripcion;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "GRUPO_PRODUCTO",
            joinColumns = @JoinColumn(name = "GRUPO_ID",referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCTO_ID",referencedColumnName = "ID"))
    private List<Producto> productos;
    private String image;
    private boolean activo;
    
    /**
     * Único constructor para crear una instancia desta clase.
     */
    public Grupo(){
        productos = new ArrayList();
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
     * @param id del tip long.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retorna el valor de la variable nombre.
     * @return del tipo java.lang.String.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Configura el valor de la variable nombre.
     * @param nombre del tipo java.lang.String.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Retorna el valor de la variable descripcion.
     * @return del tipo java.lang.String.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Configura el valor de la variable descripcion.
     * @param descripcion del tipo java.lang.String.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Retorna el valor de la variable productos.
     * @return del tipo java.util.List&lt;Producto&gt;.
     */
    public List<Producto> getProductos() {
        return productos;
    }

    /**
     * Configura el valor de la variable productos.
     * @param productos del tipo java.util.List&lt;Producto&gt;.
     */
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    /**
     * Retorna el valor de la variable image.
     * @return del tipo java.lang.String.
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
     * Override toString para que se le pueda utilizar en los Model por ejemplo
     * ListModel.
     * @return del tipo java.lang.String.
     */
    @Override
    public String toString(){
        return nombre;
    }

    /**
     * Override hashCode para que utilice id para generar el hashCode.
     * @return del tipo int.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    /**
     * Override equals para que utilice id para determinar se una instancia
     * de la clase Grupo es igual a otra.
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
        final Grupo other = (Grupo) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
