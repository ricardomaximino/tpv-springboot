/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brasajava.view.producto;

import com.brasajava.model.Grupo;
import com.brasajava.util.interfaces.Internationalizable;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Ricardo
 */
public class ProductoTableModel extends AbstractTableModel implements Internationalizable{
    private Grupo grupo;
    private String [] titulos;
   //falta internationalization
    public ProductoTableModel(){
        grupo = new Grupo();
        titulos = new String[3];
        titulos[0]= "COD.";
        titulos[1]= "NOMBRE";
        titulos[2]= "PRECIO + I.V.A.";
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
        this.fireTableDataChanged();
    }

    public String[] getTitulos() {
        return titulos;
    }

    public void setTitulos(String[] titulos) {
        this.titulos = titulos;
    }
    
    

    @Override
    public int getRowCount() {
        return grupo.getProductos().size();
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
                obj = grupo.getProductos().get(rowIndex).getId();
                break;
            case 1:
                obj = grupo.getProductos().get(rowIndex).getNombre();
                break;
            case 2:
                obj = grupo.getProductos().get(rowIndex).getPrecioMasIva().toString();
                break;
        }
        return obj;
    }

    @Override
    public void refreshLanguage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
