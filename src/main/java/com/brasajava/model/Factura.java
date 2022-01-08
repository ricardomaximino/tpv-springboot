package com.brasajava.model;

import com.brasajava.util.LocalDateAttributeConverter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Esta clase representa una factura.
 * @author Ricardo Maximino
 */
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
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate fecha;

    /**
     * Único constructor para crear una instancia desta clase.
     */
    public Factura(){
        cuentas = new ArrayList<>();
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
     * Retorna el valor de la variable cuentas.
     * @return del tipo java.util.List&lt;Cuenta&gt;.
     */
    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    /**
     * Configura el valor de la variable cuentas.
     * @param cuentas del tipo java.util.List&lt;Cuenta&gt;.
     */
    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    /**
     * Retorna el valor de la variable total.
     * @return del tipo java.math.BigDecimal.
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * Configura el valor de la variable total.
     * @param total del tipo java.math.BigDecimal.
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * Retorna el valor de la variable cobrada.
     * @return del tipo boolean.
     */
    public boolean isCobrada() {
        return cobrada;
    }

    /**
     * Configura el valor de la variable cobrada.
     * @param cobrada del tipo boolean.
     */
    public void setCobrada(boolean cobrada) {
        this.cobrada = cobrada;
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
     * Override equals para que utilice id para determinar se una instancia
     * de la clase Factura es igual a otra.
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
        final Factura other = (Factura) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
