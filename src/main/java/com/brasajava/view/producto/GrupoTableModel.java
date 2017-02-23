package com.brasajava.view.producto;

import com.brasajava.model.Grupo;
import com.brasajava.util.ApplicationLocale;
import com.brasajava.util.interfaces.Internationalizable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.springframework.context.MessageSource;

/**
 *
 * @author Ricardo
 */
public class GrupoTableModel extends AbstractTableModel implements Internationalizable{
    private List<Grupo> listaDeGrupo;
    private String [] titulos;
    private final MessageSource messageSource;
    private final ApplicationLocale applicationLocale;
  
    public GrupoTableModel(MessageSource messageSource, ApplicationLocale applicationLocale){
        this.messageSource = messageSource;
        this.applicationLocale = applicationLocale;
        listaDeGrupo = new ArrayList<>();
        titulos = new String[3];
        setWithInternationalization();
    }

    public List<Grupo> getListaDeGrupo() {
        return listaDeGrupo;
    }

    public void setListaDeGrupo(List<Grupo> listaDeGrupo) {
        this.listaDeGrupo = listaDeGrupo;
    }
    

    public String[] getTitulos() {
        return titulos;
    }

    public void setTitulos(String[] titulos) {
        this.titulos = titulos;
    }
    
    

    @Override
    public int getRowCount() {
        return listaDeGrupo.size();
    }

    @Override
    public int getColumnCount() {
        return titulos.length;
    }
    
    @Override
    public String getColumnName(int columnIndex){
        return titulos[columnIndex];
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return false;
    }

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

    private void setWithInternationalization(){
        titulos[0]= messageSource.getMessage("label_Code", null, applicationLocale.getLocale());
        titulos[1]= messageSource.getMessage("label_Name", null, applicationLocale.getLocale());
        titulos[2]= messageSource.getMessage("tableColumn_NumberOfProducts", null, applicationLocale.getLocale());
    }
    @Override
    public void refreshLanguage() {
        setWithInternationalization();
        fireTableStructureChanged();
    }
    
}
