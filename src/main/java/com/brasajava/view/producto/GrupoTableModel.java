package com.brasajava.view.producto;

import com.brasajava.model.Grupo;
import com.brasajava.util.ApplicationLocale;
import com.brasajava.util.interfaces.Internationalizable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.springframework.context.MessageSource;

/**
 * Esta clase representa el TableModel del Grupo.
 * @author Ricardo Maximino
 */
public class GrupoTableModel extends AbstractTableModel implements Internationalizable{
    private List<Grupo> listaDeGrupo;
    private String [] titulos;
    private final MessageSource messageSource;
    private final ApplicationLocale applicationLocale;
  
    /**
     * Único constructor para crear una instancia desta clase.
     * @param messageSource del tipo org.springframework.context.MessageSource.
     * @param applicationLocale del tipo com.brasajava.util.ApplicationLocale.
     */
    public GrupoTableModel(MessageSource messageSource, ApplicationLocale applicationLocale){
        this.messageSource = messageSource;
        this.applicationLocale = applicationLocale;
        listaDeGrupo = new ArrayList<>();
        titulos = new String[3];
        setWithInternationalization();
    }

    /**
     * Retorna el valor de la variable listaDeGrupo.
     * @return del tipo java.util.List&lt;Grupo&gt;.
     */
    public List<Grupo> getListaDeGrupo() {
        return listaDeGrupo;
    }

    /**
     * Configura el valor de la variable listaDeGrupo.
     * @param listaDeGrupo del tipo java.util.List&lt;Grupo&gt;.
     */
    public void setListaDeGrupo(List<Grupo> listaDeGrupo) {
        this.listaDeGrupo = listaDeGrupo;
    }
    

    /**
     * Retorna el valor de la varible titulos.
     * @return del tipo java.lang.String[].
     */
    public String[] getTitulos() {
        return titulos;
    }

    /**
     * Configura el valor de la variable titulos.
     * @param titulos del tipo java.lang.String[].
     */
    public void setTitulos(String[] titulos) {
        this.titulos = titulos;
    }   

    /**
     * Retorna el número de lineas usando el metodo size() de la listaDeProducto.
     * @return del tipo int.
     */
    @Override
    public int getRowCount() {
        return listaDeGrupo.size();
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
     * Retorna el nombre de la columna retornando el valor de la array titulos
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
                obj = listaDeGrupo.get(rowIndex).getId();
                break;
            case 1:
                obj = listaDeGrupo.get(rowIndex).getNombre();
                break;
            case 2:
                obj = listaDeGrupo.get(rowIndex).getProductos().size();
                break;
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
        titulos[1]= messageSource.getMessage("label_Name", null, applicationLocale.getLocale());
        titulos[2]= messageSource.getMessage("tableColumn_NumberOfProducts", null, applicationLocale.getLocale());
    }
}
