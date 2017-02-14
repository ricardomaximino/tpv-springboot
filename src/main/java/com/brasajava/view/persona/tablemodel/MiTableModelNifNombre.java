package com.brasajava.view.persona.tablemodel;

import com.brasajava.model.Persona;
import com.brasajava.util.ApplicationLocale;
import java.util.List;
import org.springframework.context.MessageSource;

/**
 *
 * @author Ricardo Maximino
 */
public class MiTableModelNifNombre extends MiTableModel{
    public MiTableModelNifNombre(MessageSource messageSource,ApplicationLocale applicationLocale){
        super(messageSource, applicationLocale);
        titulos = new String[2];
        configTitulos();
    }
    public MiTableModelNifNombre(MessageSource messageSource,ApplicationLocale applicationLocale,List<Persona> datos){
        super(messageSource, applicationLocale, datos);
        titulos = new String[2];
        configTitulos();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
         Object object = null;
        switch (columnIndex) {
            case 0:
                object = datos.get(rowIndex).getNif();
                break;
            case 1:
                object = datos.get(rowIndex).getNombre();
                break;
        }
        return object;
    }

    @Override
    public void colunmSizeConfig() {
       if (tabla != null) {
            tabla.getColumnModel().getColumn(0).setPreferredWidth(10);
       }
    }

    @Override
    public void refreshLanguage() {
        configTitulos();
    }
    
    private void configTitulos(){
        titulos[0] = messageSource.getMessage("ID", null, applicationLocale.getLocale());
        titulos[1] = messageSource.getMessage("NAME", null, applicationLocale.getLocale());
    }
    
}
