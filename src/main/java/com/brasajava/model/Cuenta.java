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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * Esta clase representa una cuenta.
 *
 * @author Ricardo Maximino
 */
@Entity
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nombre;
    @OneToMany(mappedBy = "cuenta", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Venta> ventas;
    private BigDecimal total;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FACTURA_ID")
    private Factura factura;
    @Transient
    private boolean ticket;
    @Transient
    private boolean ajustada;
    @Transient
    private boolean reabrir;
    private boolean cobrada;
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate fecha;

    /**
     * Constructo para crear una instancia desta clase sin argumento.
     */
    public Cuenta() {
        ventas = new ArrayList<>();
        total = new BigDecimal("0.00");
        fecha = LocalDate.now();
    }

    /**
     * Constructor para crear una instancia desta clase con el argumento nombre.
     *
     * @param nombre del tipo java.lang.String.
     */
    public Cuenta(String nombre) {
        this();
        this.nombre = nombre;
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
     * @param nombre del tipo java.lang.String.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Retorna el valor de la variable ventas.
     * @return del tipo java.util.List&lt;Venta&gt;.
     */
    public List<Venta> getVentas() {
        return ventas;
    }

    /**
     * Configura el valor de la variable ventas.
     * @param ventas del tipo java.util.List&lt;Venta&gt;.
     */
    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
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

    /**
     * Retorna el valor de la variable ticket.
     * @return del tipo boolean.
     */
    public boolean isTicket() {
        return ticket;
    }

    /**
     * Configura el valor de la variable ticket.
     * @param ticket del tipo boolean.
     */
    public void setTicket(boolean ticket) {
        this.ticket = ticket;
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
     * Retorna el valor de la variable ajustada.
     * @return del tipo boolean.
     */
    public boolean isAjustada() {
        return ajustada;
    }

    /**
     * Configura el valor de la variable ajustada.
     * @param ajustada del tipo boolean.
     */
    public void setAjustada(boolean ajustada) {
        this.ajustada = ajustada;
    }

    /**
     * Retorna el valor de la variable reabrir.
     * @return del tipo boolean.
     */
    public boolean isReabrir() {
        return reabrir;
    }

    /**
     * Configura el valor de la variable reabrir.
     * @param reabrir del tipo boolean.
     */
    public void setReabrir(boolean reabrir) {
        this.reabrir = reabrir;
    }

    /**
     * Añade una venta a esta cuenta y actualiza la variable total.
     * @param venta del tipo com.brasajava.model.Venta.
     */
    public void addVenta(Venta venta) {
        ventas.add(venta);
        total = total.add(venta.getTotal());
    }

    /**
     * Quita una venta a esta cuenta y actualiza la variable total.
     * @param venta del tipo com.brasajava.model.Venta.
     */
    public void removeVenta(Venta venta) {
        if (ventas.contains(venta)) {
            ventas.remove(venta);
            total = total.subtract(venta.getTotal());
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
     * Override hashCode para que utilice id para generar el hashCode.
     * @return del tipo int.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    /**
     * Override equals para que utilice id para determinar se una instancia
     * de la clase Cuenta es igual a otra.
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
        final Cuenta other = (Cuenta) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
