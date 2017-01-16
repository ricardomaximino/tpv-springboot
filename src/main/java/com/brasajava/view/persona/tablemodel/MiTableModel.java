package com.brasajava.view.persona.tablemodel;

import com.brasajava.model.Persona;
import com.brasajava.util.ApplicationLocale;
import com.brasajava.util.interfaces.Internationalizable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import org.springframework.context.MessageSource;

/**
 * Esta es una clase abstrata que extende la clase
 * javax.swing.table.AbstractTableModel e implementa la interz
 * com.brasajava.util.interfaces.Internationalizable.
 *
 * @author Ricardo Maximino
 * <p>
 * Esta clase implementa todos los getter, setter y variable globales dejando a
 * las clases concretas la obligación de implementar dos metodos:
 * </p>
 * <ul>
 * <li>@Override<br> public abstract Object getValueAt(int rowIndex, int
 * columnIndex);</li>
 * <li>public abstract void colunmSizeConfig();</li>
 * </ul>
 */
public abstract class MiTableModel extends AbstractTableModel implements Internationalizable {

    /**
     * variable global del tipo org.springframework.context.MessageSource
     * utilizada para implementar la interfaz com.brasajava.util.interfaces.Internationalizable.
     */
    protected MessageSource messageSource;
    /**
     * variable global del tipo com.brasajaba.util.ApplicationLocale
     * utilizada para saber el idioma seleccionado como default en la 
     * aplicación y el idioma atual seleccionado por el usuario.
     */
    protected ApplicationLocale applicationLocale;
    /**
     * variable global del tipo java.util.List&lt;com.brasajava.model.Persona&gt;
     * para representar los datos.<br>
     * esta variable tiene los metodos get y set.
     */
    protected List<Persona> datos;
    /**
     * variable global del tipo java.lang.String[]
     * para representar los titulos.
     */
    protected String[] titulos;
    /**
     * variable global del tipo javax.swing.JTable
     * para uso interno esta variable no tiene el metodo get.
     */
    protected JTable tabla;

    /**
     * Constructor completo, ya que los titulo se supene que las clases contratas
     * los implementaran internamente.
     * @param messageSource del tipo org.springframework.context.MessageSource.
     * @param applicationLocale del tipo com.brasajava.util.ApplicationLocale.
     * @param datos del tipo java.util.List&lt;com.brasajava.model.Persona&gt;
     */
    public MiTableModel(MessageSource messageSource, ApplicationLocale applicationLocale, List<Persona> datos) {
        this.messageSource = messageSource;
        this.applicationLocale = applicationLocale;
        this.datos = datos;
    }

    /**
     * Constructor con configuración basica, ya que es necesario utilizar el
     * metodo setDatos para configurar los datos.
     * @param messageSource del tipo org.springframework.context.MessageSource.
     * @param applicationLocale del tipo com.brasajava.util.ApplicationLocale.
     */
    public MiTableModel(MessageSource messageSource, ApplicationLocale applicationLocale) {
        this.messageSource = messageSource;
        this.applicationLocale = applicationLocale;
        datos = new ArrayList<>();
    }

    /**
     * Configura el valor de la variable global messageSource.
     * @param messageSource del tipo org.springframework.context.MessageSource.
     */
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Configura el valor de la varible global applicationLocale.
     * @param applicationLocale del tipo com.brasajava.util.ApplicationLocale.
     */
    public void setApplicationLocale(ApplicationLocale applicationLocale) {
        this.applicationLocale = applicationLocale;
    }

    /**
     * Configura el valor de la variable global datos.
     * @param datos del tipo java.util.List&lt;com.brasajava.model.Persona&gt;
     */
    public void setDatos(List<Persona> datos) {
        this.datos = datos;
    }

    /**
     * Retorna el valor de la variable global datos.
     * @return del tipo java.util.List&lt;com.brasajava.model.Persona&gt;
     */
    public List<Persona> getDatos() {
        return datos;
    }

    /**
     * Configura el valor de la variable global titulos.
     * @param titulos del tipo java.lang.String[].
     */
    public void setTitulos(String[] titulos) {
        this.titulos = titulos;
    }

    /**
     * Configura el valor de la variable global tabla.
     * @param tabla del tipo javax.swing.JTable.
     */
    public void setTabla(JTable tabla) {
        this.tabla = tabla;
    }

    /**
     * Este metodo invoca el metodo clear() de la variable datos.
     */
    public void clearData() {
        datos.clear();
    }

    /**
     * Este metodo retorna un int informando el numero de lineas de los datos.
     * @return del tipo int.
     */
    @Override
    public int getRowCount() {
        return datos.size();
    }

    /**
     * Este metodo retorna un int informando el numero de columnas de los datos.
     * @return del tipo int.
     */
    @Override
    public int getColumnCount() {
        return titulos.length;
    }

    /**
     * Este metodo retorna una String con el nombre de la columna indicada por
     * el parametro.
     * @param col del tipo int.
     * @return del tipo java.lang.String.
     */
    @Override
    public String getColumnName(int col) {
        return titulos[col];
    }

    /**
     * Este es un metodo que las clases concretas son obligadas a implementar.
     * @param rowIndex del tipo int.
     * @param columnIndex del tipo int.
     * @return del tipo java.lang.Object.
     * <p>Este metodo es responsable de retornar el valor de la posición 
     * especificada como una supuesta bidimendional array</p>
     */
    @Override
    public abstract Object getValueAt(int rowIndex, int columnIndex);

    /**
     * Este metodo deve configurar el tamaño de las columnas.
     */
    public abstract void colunmSizeConfig();

}
