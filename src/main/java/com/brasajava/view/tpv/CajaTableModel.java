package com.brasajava.view.tpv;

import com.brasajava.model.Cuenta;
import com.brasajava.model.Venta;
import com.brasajava.util.interfaces.Internationalizable;
import javax.swing.table.AbstractTableModel;

public class CajaTableModel extends AbstractTableModel implements Internationalizable {

    private Cuenta cuenta;
    private String[] title;

    public CajaTableModel() {
        cuenta = new Cuenta("0");
        title = new String[4];
        title[0] = "UNID.";
        title[1] = "DESC.";
        title[2] = "PREC.";
        title[3] = "TOTAL";
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
        return false;
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
                obj = venta.getProducto().getName();
                break;
            case 2:
                obj = venta.getProducto().getPrecioMasIva();
                break;
            case 3:
                obj = venta.getTotal();
        }
        return obj;
    }

    @Override
    public void refreshLanguage() {

    }

}
