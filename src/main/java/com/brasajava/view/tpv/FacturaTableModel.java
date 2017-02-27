/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brasajava.view.tpv;

import com.brasajava.model.Factura;
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
public class FacturaTableModel extends AbstractTableModel implements Internationalizable{
    private List<Factura> listaDeFactura;
    private String [] titulos;
    private final MessageSource messageSource;
    private final ApplicationLocale applicationLocale;

    public FacturaTableModel(MessageSource messageSource,ApplicationLocale applicationLocale){
        this.messageSource = messageSource;
        this.applicationLocale = applicationLocale;
        listaDeFactura = new ArrayList<>();
        titulos = new String[4];
        setWithInternationalization();
    }

    public List<Factura> getListaDeFactura() {
        return listaDeFactura;
    }

    public void setListaDeFactura(List<Factura> listaDeFactura) {
        this.listaDeFactura = listaDeFactura;
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
        return listaDeFactura.size();
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

    private void setWithInternationalization(){
        titulos[0]= messageSource.getMessage("label_Code", null, applicationLocale.getLocale());
        titulos[1]= messageSource.getMessage("DATE", null, applicationLocale.getLocale());
        titulos[2]= messageSource.getMessage("tableColumn_NumberOfAccount", null, applicationLocale.getLocale());
        titulos[3]= messageSource.getMessage("TOTAL", null, applicationLocale.getLocale());
    }
    @Override
    public void refreshLanguage() {
        setWithInternationalization();
        fireTableStructureChanged();
    }
    
}
