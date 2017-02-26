package com.brasajava.view.tpv;

import com.brasajava.model.Cuenta;
import com.brasajava.model.Venta;
import com.brasajava.util.ApplicationLocale;
import com.brasajava.util.interfaces.Internationalizable;
import javax.swing.table.AbstractTableModel;
import org.springframework.context.MessageSource;

public class CajaTableModel extends AbstractTableModel implements Internationalizable {

    private Cuenta cuenta;
    private String[] title;
    private final MessageSource messageSource;
    private final ApplicationLocale applicationLocale;

    public CajaTableModel(MessageSource messageSource, ApplicationLocale applicationLocale) {
        this.messageSource = messageSource;
        this.applicationLocale = applicationLocale;
        cuenta = new Cuenta("0");
        title = new String[4];
        setWithInternationalization();
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    @Override
    public int getRowCount() {
        return cuenta.getVentas().size();
    }

    @Override
    public int getColumnCount() {
        return title.length;
    }

    @Override
    public String getColumnName(int col) {
        return title[col];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    @Override
    public Object getValueAt(int row, int col) {
        Object obj = null;
        Venta venta = cuenta.getVentas().get(row);
        switch (col) {
            case 0:
                obj = venta.getCantidad();
                break;
            case 1:
                obj = venta.getProducto().getNombre();
                break;
            case 2:
                obj = venta.getProducto().getPrecioMasIva();
                break;
            case 3:
                obj = venta.getTotal();
        }
        return obj;
    }
    
    private void setWithInternationalization(){
        title[0] = messageSource.getMessage("UNIT", null,applicationLocale.getLocale()); //"UNID.";
        title[1] = messageSource.getMessage("DESCRIPTION", null,applicationLocale.getLocale()); //"DESC.";
        title[2] = messageSource.getMessage("PRICE", null,applicationLocale.getLocale()); //"PREC.";
        title[3] = messageSource.getMessage("TOTAL", null,applicationLocale.getLocale()); //"TOTAL";
    }

    @Override
    public void refreshLanguage() {
        setWithInternationalization();
        fireTableStructureChanged();
    }

}
