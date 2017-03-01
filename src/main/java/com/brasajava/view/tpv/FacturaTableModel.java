package com.brasajava.view.tpv;

import com.brasajava.model.Factura;
import com.brasajava.util.ApplicationLocale;
import com.brasajava.util.interfaces.Internationalizable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.springframework.context.MessageSource;

/**
 * Esta clase representa un TableModel para com.brasajava.model.Factura.
 * @author Ricardo Maximino
 */
public class FacturaTableModel extends AbstractTableModel implements Internationalizable{
    private List<Factura> listaDeFactura;
    private String [] titulos;
    private final MessageSource messageSource;
    private final ApplicationLocale applicationLocale;

    /**
     * El único constructor para instanciar esta clase.
     * @param messageSource del tipo org.springframework.context.MessageSource.
     * @param applicationLocale del tipo com.brasajava.util.ApplicationLocale.
     * <p>listaDeFactura = new ArrayList&lt;&gt;();</p>
     * <p>titulos = new String[4]</p>
     */
    public FacturaTableModel(MessageSource messageSource,ApplicationLocale applicationLocale){
        this.messageSource = messageSource;
        this.applicationLocale = applicationLocale;
        listaDeFactura = new ArrayList<>();
        titulos = new String[4];
        setWithInternationalization();
    }

    /**
     * Retorna el valor de la variable listaDeFactura.
     * @return del tipo java.util.List&lt;com.brasajava.model.Factura&gt;
     */
    public List<Factura> getListaDeFactura() {
        return listaDeFactura;
    }

    /**
     * Configura el valor de la variable listaDeFactura y llama el metodo
     * fireTableDataChaged().
     * @param listaDeFactura  del tipo java.util.List&lt;com.brasajava.model.Factura&gt;
     */
    public void setListaDeFactura(List<Factura> listaDeFactura) {
        this.listaDeFactura = listaDeFactura;
        this.fireTableDataChanged();
    }
    

    /**
     * Retorna el valor de la variable titulos.
     * @return del tipo java.lang.String[]
     */
    public String[] getTitulos() {
        return titulos;
    }

    /**
     * Configura el valor de la variable titulos y llama el metodo 
     * fireTableStructureChanged()
     * @param titulos del tipo java.lang.String[].
     */
    public void setTitulos(String[] titulos) {
        this.titulos = titulos;
        this.fireTableStructureChanged();
    }
    
    

    /**
     * Retorna el número de lineas usando el metodo size() de la listaDeFactura.
     * @return del tipo int.
     */
    @Override
    public int getRowCount() {
        return listaDeFactura.size();
    }

    /**
     * Retorna el número de columnas usando la variable de la array titulos.
     * @return del tipo int.
     */
    @Override
    public int getColumnCount() {
        return titulos.length;
    }
    /**
     * Retorna el nombre de la columna retornando el valor de la array titulo
     * usando el parametro pasado.
     * @param columnIndex del tipo int.
     * @return del tipo java.lang.String.
     */
    @Override
    public String getColumnName(int columnIndex){
        return titulos[columnIndex];
    }
    
    /**
     * Override el metodo para retornar siempre false para que ninguna de las
     * celulas sean editables.
     * @param rowIndex del tipo int.
     * @param columnIndex del tipo int.
     * @return  del tipo boolean.
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return false;
    }

    /**
     * Retorna un objeto con el valor de las coodenadas pasadas como parametros.
     * @param rowIndex del tipo int.
     * @param columnIndex del tipo int.
     * @return del tipo java.lang.Object.
     * 
     * Esta clase no usa un array bidimensional si no que una lista,
     * entoces utiliza rowIndex como index de la lista y el columnIndex en
     * un switch para retornar el valor objeto deseado.
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object obj = null;
        switch(columnIndex){
            case 0:
                obj = listaDeFactura.get(rowIndex).getId();
                break;
            case 1:
                obj = listaDeFactura.get(rowIndex).getFecha().toString();
                break;
            case 2:
                obj = listaDeFactura.get(rowIndex).getCuentas().size();
                break;
            case 3:
                obj = listaDeFactura.get(rowIndex).getTotal().toString();
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
        titulos[0]= messageSource.getMessage("label_Code", null, applicationLocale.getLocale());
        titulos[1]= messageSource.getMessage("DATE", null, applicationLocale.getLocale());
        titulos[2]= messageSource.getMessage("tableColumn_NumberOfAccount", null, applicationLocale.getLocale());
        titulos[3]= messageSource.getMessage("TOTAL", null, applicationLocale.getLocale());
    }    
}
