/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brasajava.view.producto;

import com.brasajava.model.Grupo;
import com.brasajava.util.interfaces.Internationalizable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Ricardo
 */
public class GrupoTableModel extends AbstractTableModel implements Internationalizable{
    private List<Grupo> listaDeGrupo;
    private String [] titulos;
   //falta internationalization
    public GrupoTableModel(){
        listaDeGrupo = new ArrayList<>();
        titulos = new String[3];
        titulos[0]= "COD.";
        titulos[1]= "NOMBRE";
        titulos[2]= "PRECIO + I.V.A.";
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

    @Override
    public void refreshLanguage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
