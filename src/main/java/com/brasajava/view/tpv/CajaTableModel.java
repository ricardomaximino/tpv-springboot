package com.brasajava.view.tpv;

import com.brasajava.model.Cuenta;
import com.brasajava.model.Venta;
import com.brasajava.util.ApplicationLocale;
import com.brasajava.util.interfaces.Internationalizable;
import javax.swing.table.AbstractTableModel;
import org.springframework.context.MessageSource;

/**
 * Esta Clase representa el TableModel para la tabla de la clase TPV.
 * @author Ricardo Maximino
 */
public class CajaTableModel extends AbstractTableModel implements Internationalizable {

    private Cuenta cuenta;
    private String[] title;
    private final MessageSource messageSource;
    private final ApplicationLocale applicationLocale;

    /**
     * El único constructor para instanciar esta clase.
     * @param messageSource del tipo org.springframework.context.MessageSource.
     * @param applicationLocale del tipo com.brasajava.util.ApplicationLocale.
     */
    public CajaTableModel(MessageSource messageSource, ApplicationLocale applicationLocale) {
        this.messageSource = messageSource;
        this.applicationLocale = applicationLocale;
        cuenta = new Cuenta("0");
        title = new String[4];
        setWithInternationalization();
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
        fireTableDataChanged();
    }

    /**
     * Retorna el valor de la variable title.
     * @return del tipo java.lang.String[].
     */
    public String[] getTitle() {
        return title;
    }

    /**
     * configura el valor de la variable title.
     * @param title del tipo java.lang.String[].
     */
    public void setTitle(String[] title) {
        this.title = title;
        fireTableStructureChanged();
    }

    /**
     * Retorna el número de lineas usando el metodo size() de la cuenta.getVentas().
     * @return del tipo int.
     */
    @Override
    public int getRowCount() {
        return cuenta.getVentas().size();
    }

    /**
     * Retorna el número de columnas usando la variable de la array title.
     * @return del tipo int.
     */
    @Override
    public int getColumnCount() {
        return title.length;
    }

    /**
     * Retorna el nombre de la columna retornando el valor de la array title
     * usando el parametro pasado.
     * @param col del tipo int.
     * @return del tipo java.lang.String.
     */
    @Override
    public String getColumnName(int col) {
        return title[col];
    }

    /**
     * Override el metodo para retornar siempre false para que ninguna de las
     * celulas sean editables.
     * @param row del tipo int.
     * @param col del tipo int.
     * @return  del tipo boolean.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    /**
     * Retorna un objeto con el valor de las coodenadas pasadas como parametros.
     * @param row del tipo int.
     * @param col del tipo int.
     * @return del tipo java.lang.Object.
     * 
     * Esta clase no usa un array bidimensional si no que una lista,
     * entoces utiliza rowIndex como index de la lista y el columnIndex en
     * un switch para retornar el valor objeto deseado.
     */
    @Override
    public Object getValueAt(int row, int col) {
        Object obj = null;
        Venta venta = cuenta.getVentas().get(row);
        switch (col) {
            case 0:
                obj = venta.getCantidad();
                break;
            case 1:
                obj = venta.getProducto().getNombre();
                break;
            case 2:
                obj = venta.getProducto().getPrecioMasIva();
                break;
            case 3:
                obj = venta.getTotal();
        }
        return obj;
    }
    
    

    /**
     * Actualiza toda la interfaz gráfica con el idioma seleccionado en la
     * instacia única de la clase
     * com.brasajava.util.ApplicationLocale.getLocale().
     */
    @Override
    public void refreshLanguage() {
        setWithInternationalization();
        fireTableStructureChanged();
    }
private void setWithInternationalization(){
        title[0] = messageSource.getMessage("UNIT", null,applicationLocale.getLocale()); //"UNID.";
        title[1] = messageSource.getMessage("DESCRIPTION", null,applicationLocale.getLocale()); //"DESC.";
        title[2] = messageSource.getMessage("PRICE", null,applicationLocale.getLocale()); //"PREC.";
        title[3] = messageSource.getMessage("SUBTOTAL", null,applicationLocale.getLocale()); //"TOTAL";
    }
}
