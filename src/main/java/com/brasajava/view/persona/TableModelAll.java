package com.brasajava.view.persona;

import com.brasajava.util.interfaces.MiTableModel;
import com.brasajava.model.Persona;
import com.brasajava.util.ApplicationLocale;
import java.util.List;
import org.springframework.context.MessageSource;

/**
 * Esta clase representa el TableModel com totos los campos de la clase Persona.
 *
 * @author Ricardo Maximino
 */
public class TableModelAll extends MiTableModel {

    /**
     * Constructor para instanciar esta clase.
     *
     * @param messageSource del tipo org.springframework.context.MessageSource.
     * @param applicationLocale de tipo com.brasajava.util.ApplicationLocale.
     * <p>
     * titulos = new String[13]</p>
     */
    public TableModelAll(MessageSource messageSource, ApplicationLocale applicationLocale) {
        super(messageSource, applicationLocale);
        this.titulos = new String[13];
        configTitulos();
    }

    /**
     * Constructor para instanciar esta clase.
     *
     * @param messageSource del tipo org.springframework.context.MessageSource.
     * @param applicationLocale del tipo com.brasajava.util.ApplicationLocale.
     * @param datos del tipo java.util.List&lt;Persona&gt;.
     * <p>
     * titulos = new String[13]</p>
     */
    public TableModelAll(MessageSource messageSource, ApplicationLocale applicationLocale, List<Persona> datos) {
        super(messageSource, applicationLocale, datos);
        this.titulos = new String[13];
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
            case 2:
                object = datos.get(rowIndex).getPrimerApellido();
                break;
            case 3:
                object = datos.get(rowIndex).getSegundoApellido();
                break;
            case 4:
                object = datos.get(rowIndex).getFechaNacimiento();
                break;
            case 5:
                object = datos.get(rowIndex).isActivo();
                break;
            case 6:
                object = datos.get(rowIndex).getDirecion() != null ? "OK" : "NOT OK";
                break;
            case 7:
                object = datos.get(rowIndex).getContactos().size();
                break;
            case 8:
                object = datos.get(rowIndex).getNombreDeUsuario();
                break;
            case 9:
                object = datos.get(rowIndex).getContraseña();
                break;
            case 10:
                object = datos.get(rowIndex).getControleDeAcceso();
                break;
            case 11:
                object = datos.get(rowIndex).getFechaPrimeraAlta();
                break;
            case 12:
                object = datos.get(rowIndex).getFechaUltimaBaja();
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
            tabla.getColumnModel().getColumn(1).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(10);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(10);
            tabla.getColumnModel().getColumn(6).setPreferredWidth(30);
            tabla.getColumnModel().getColumn(7).setPreferredWidth(30);
            tabla.getColumnModel().getColumn(8).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(9).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(10).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(11).setPreferredWidth(10);
            tabla.getColumnModel().getColumn(12).setPreferredWidth(10);

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

    private void configTitulos() {
        titulos[0] = messageSource.getMessage("ID", null, applicationLocale.getLocale());
        titulos[1] = messageSource.getMessage("NAME", null, applicationLocale.getLocale());
        titulos[2] = messageSource.getMessage("FIRST_LASTNAME", null, applicationLocale.getLocale());
        titulos[3] = messageSource.getMessage("SECOND_LASTNAME", null, applicationLocale.getLocale());
        titulos[4] = messageSource.getMessage("BIRTHDAY", null, applicationLocale.getLocale());
        titulos[5] = messageSource.getMessage("ACTIVE", null, applicationLocale.getLocale());
        titulos[6] = messageSource.getMessage("ADDRESS", null, applicationLocale.getLocale());
        titulos[7] = messageSource.getMessage("CONTACTS", null, applicationLocale.getLocale());
        titulos[8] = messageSource.getMessage("USERNAME", null, applicationLocale.getLocale());
        titulos[9] = messageSource.getMessage("PASSWORD", null, applicationLocale.getLocale());
        titulos[10] = messageSource.getMessage("ACCESS_CONTROL", null, applicationLocale.getLocale());
        titulos[11] = messageSource.getMessage("JOIN_DATE", null, applicationLocale.getLocale());
        titulos[12] = messageSource.getMessage("CUTED_OFF_DATE", null, applicationLocale.getLocale());
    }
}
