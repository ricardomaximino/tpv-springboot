/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brasajava.view.producto;

import com.brasajava.model.Producto;
import com.brasajava.util.ApplicationLocale;
import com.brasajava.util.interfaces.Internationalizable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

/**
 *
 * @author Ricardo
 */
public class ProductoTableModel extends AbstractTableModel implements Internationalizable{
    private List<Producto> listaDeProducto;
    private String [] titulos;
    private final MessageSource messageSource;
    private final ApplicationLocale applicationLocale;

    public ProductoTableModel(MessageSource messageSource,ApplicationLocale applicationLocale){
        this.messageSource = messageSource;
        this.applicationLocale = applicationLocale;
        listaDeProducto = new ArrayList<>();
        titulos = new String[3];
        setWithInternationalization();
    }

    public List<Producto> getListaDeProducto() {
        return listaDeProducto;
    }

    public void setListaDeProducto(List<Producto> listaDeProducto) {
        this.listaDeProducto = listaDeProducto;
        this.fireTableDataChanged();
    }
    

    public String[] getTitulos() {
        return titulos;
    }

    public void setTitulos(String[] titulos) {
        this.titulos = titulos;
        this.fireTableStructureChanged();
    }
    
    

    @Override
    public int getRowCount() {
        return listaDeProducto.size();
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
                obj = listaDeProducto.get(rowIndex).getId();
                break;
            case 1:
                obj = listaDeProducto.get(rowIndex).getNombre();
                break;
            case 2:
                obj = listaDeProducto.get(rowIndex).getPrecioMasIva().toString();
                break;
        }
        return obj;
    }

    private void setWithInternationalization(){
        titulos[0]= messageSource.getMessage("label_Code", null, applicationLocale.getLocale());
        titulos[1]= messageSource.getMessage("label_Name", null, applicationLocale.getLocale());
        titulos[2]= messageSource.getMessage("label_PriceWithTax", null, applicationLocale.getLocale());
    }
    @Override
    public void refreshLanguage() {
        setWithInternationalization();
        fireTableStructureChanged();
    }
    
}
