package com.brasajava.view.persona;

import com.brasajava.util.interfaces.MiTableModel;
import com.brasajava.model.Persona;
import com.brasajava.util.ApplicationLocale;
import java.util.List;
import org.springframework.context.MessageSource;

/**
 * Esta clase representa el TableModel con los campos NIF y NOMBRE de la clase 
 * Persona.
 *
 * @author Ricardo Maximino
 */
public class MiTableModelNifNombre extends MiTableModel{
    public MiTableModelNifNombre(MessageSource messageSource,ApplicationLocale applicationLocale){
        super(messageSource, applicationLocale);
        titulos = new String[2];
        configTitulos();
    }
    /**
     * El único constructor para instanciar esta clase.
     *
     * @param messageSource del tipo org.springframework.context.MessageSource.
     * @param applicationLocale del tipo com.brasajava.util.ApplicationLocale.
     * @param datos del tipo java.util.List&lt;Persona&gt;.
     * <p>
     * titulos = new String[13]</p>
     */
    public MiTableModelNifNombre(MessageSource messageSource,ApplicationLocale applicationLocale,List<Persona> datos){
        super(messageSource, applicationLocale, datos);
        titulos = new String[2];
        configTitulos();
    }

    /**
     * Retorna un objeto con el valor de las coodenadas pasadas como parametros.
     *
     * @param rowIndex del tipo int.
     * @param columnIndex del tipo int.
     * @return del tipo java.lang.Object.
     *
     * Esta clase no usa un array bidimensional si no que una lista, entoces
     * utiliza rowIndex como index de la lista y el columnIndex en un switch
     * para retornar el valor objeto deseado.
     */
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

    /**
     * Auto configura las columnas de la tabla relacionada a este tableModel.
     */
    @Override
    public void colunmSizeConfig() {
       if (tabla != null) {
            tabla.getColumnModel().getColumn(0).setPreferredWidth(10);
       }
    }

    /**
     * Actualiza toda la interfaz gráfica con el idioma seleccionado en la
     * instacia única de la clase
     * com.brasajava.util.ApplicationLocale.getLocale().
     */
    @Override
    public void refreshLanguage() {
        configTitulos();
    }
    
    private void configTitulos(){
        titulos[0] = messageSource.getMessage("ID", null, applicationLocale.getLocale());
        titulos[1] = messageSource.getMessage("NAME", null, applicationLocale.getLocale());
    }
    
}
