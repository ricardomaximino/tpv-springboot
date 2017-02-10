package com.brasajava.view.tpv;

import com.brasajava.model.Cuenta;
import com.brasajava.model.Grupo;
import com.brasajava.model.Producto;
import com.brasajava.model.Venta;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class TpvModel {

    //Grupo
    private int grupoHeight;
    private int grupoWidth;
    private int grupoXPosition;
    private int grupoYPosition;
    private int grupoCount;
    private List<JComponent> grupoButtonList;
    private Map<String, Grupo> grupoMap;
    private List<Grupo> grupoList;
    private JPanel grupoPanel;

    //Producto
    private int productoHeight;
    private int productoWidth;
    private int productoXPosition;
    private int productoYPosition;
    private int productoCount;
    private List<JComponent> productoButtonList;
    private Map<String, Producto> productoMap;
    private List<Producto> productoList;
    private JPanel productoPanel;

    //Cuenta
    private int cuentaHeight;
    private int cuentaWidth;
    private int cuentaXPosition;
    private int cuentaYPosition;
    private int cuentaCount;
    private List<JComponent> cuentaButtonList;
    private Map<String, Cuenta> cuentaMap;
    private List<Cuenta> cuentaList;
    private JPanel cuentaPanel;

    private final CajaTableModel model;
    private int cantidad;
    private JLabel labelTotal;
    private JTextField txtCuenta;
    private JLabel lblCantidad;
    private Map<Producto, Venta> ventasMap;
    private Map<Cuenta, CuentaMap> cuentasMap;
    private final JTable tabla;
    private JScrollPane scrollTabla;

    public TpvModel(JTable tabla) {
        this.tabla = tabla;
        this.model = (CajaTableModel) tabla.getModel();
        //ButtonList
        grupoButtonList = new ArrayList();
        productoButtonList = new ArrayList();
        cuentaButtonList = new ArrayList();
        //Map
        grupoMap = new HashMap<>();
        productoMap = new HashMap<>();
        cuentaMap = new HashMap<>();
        //
        grupoHeight = productoHeight = cuentaHeight = 200;
        grupoWidth = productoWidth = cuentaWidth = 200;
        cantidad = 1;
        cuentaCount = 1;

        cuentasMap = new HashMap<>();
    }


    private void guardaCuenta() {
        Cuenta cuenta = model.getCuenta();
        cuentaMap.put(cuenta.getNombre(), cuenta);
        JButton b = new JButton(cuenta.getNombre());
        b.setActionCommand(cuenta.getNombre());
        b.setSize(cuentaWidth, cuentaHeight);
        b.addActionListener(this::cuentaButtonAction);
        if (cuenta.isTicket()) {
            b.setBackground(Color.green);
        }
        cuentaButtonList.add(b);
        reorganizaCuenta();
    }

    
    private void abrirCuenta(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        Cuenta c = cuentaMap.get(b.getActionCommand());
        cuentaMap.remove(c.getNombre());
        cuentaButtonList.remove(b);
        abrirCuenta(c);
    }
    

    private void grupoButtonAction(ActionEvent e) {
        Grupo g = grupoMap.get(((JButton) e.getSource()).getActionCommand());
        productoButtonList.clear();
        for (Producto p : g.getProductos()) {
            JButton button = new JButton(p.getNombre());
            button.addActionListener(this::productoButtonAction);
            button.setActionCommand(p.getId() + "");
            button.setSize(productoWidth,productoHeight);
            if (p.getImage() != null && !p.getImage().isEmpty()) {
                    Image image = new ImageIcon(getClass().getResource("/images/" + p.getImage())).getImage().getScaledInstance(190, 170, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(image) );
                    button.setVerticalTextPosition(AbstractButton.BOTTOM);
                    button.setHorizontalTextPosition(AbstractButton.CENTER);
            }
            button.setToolTipText(p.getNombre() + " " + p.getPrecioMasIva());
            productoButtonList.add(button);
            reoganizaProducto();
            productoMap.put(p.getId() + "", p);
        }
    }

    //cada linea, en la tabla, para cada producto
    private void productoButtonAction(ActionEvent e) {
        converteCantidad();

        Producto p = productoMap.get(((JButton) e.getSource()).getActionCommand());
        Cuenta c = model.getCuenta();
        if (!cuentasMap.containsKey(c)) {
            cuentasMap.put(c, new CuentaMap());
        }
        ventasMap = cuentasMap.get(c).getMap();
        Map<Venta, Integer> pointMap = cuentasMap.get(c).getPointMap();
        List<Venta> ventas = c.getVentas();

        Venta v = ventasMap.get(p);
        if (v != null) {
            v.setVenta(v.getCantidad() + cantidad, p);
        } else {
            v = new Venta();
            v.setCuenta(c);
            v.setVenta(cantidad, p);
            ventasMap.put(p, v);
            ventas.add(v);
        }

        model.fireTableDataChanged();
        tabla.setRowSelectionInterval(ventas.indexOf(v), ventas.indexOf(v));
        tabla.repaint();
        if (!pointMap.containsKey(v)) {
            int max = scrollTabla.getVerticalScrollBar().getMaximum();
            pointMap.put(v, max);
        }
        c.setTotal(sumaTotal(ventas));
        resetCantidad();
        scrollTabla.getVerticalScrollBar().setValue(pointMap.get(v));
    }
    

    private void cuentaButtonAction(ActionEvent e) {
        abrirCuenta(e);
    }

    private void reorganizaGrupo() {
        grupoYPosition = 0;
        grupoHeight = 0;
        grupoPanel.removeAll();

        for (Component c : grupoButtonList) {
            c.setLocation(grupoXPosition, grupoYPosition);
            grupoPanel.add(c);
            grupoYPosition += c.getHeight();

            if (grupoPanel.getHeight() < grupoYPosition) {
                int w = grupoPanel.getWidth();

                grupoPanel.setMaximumSize(new java.awt.Dimension(200, 32767));
                grupoPanel.setPreferredSize(new java.awt.Dimension(200, grupoYPosition));

                GroupLayout panelLayout = (GroupLayout) grupoPanel.getLayout();
                grupoPanel.setLayout(panelLayout);
                panelLayout.setHorizontalGroup(
                        panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, w, Short.MAX_VALUE)
                );
                panelLayout.setVerticalGroup(
                        panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, grupoYPosition, Short.MAX_VALUE)
                );
            }
        }
        grupoPanel.repaint();
    }

    private void reoganizaProducto() {
        productoXPosition = 0;
        productoYPosition = 0;
        productoPanel.removeAll();

        for (Component c : productoButtonList) {
            c.setLocation(productoXPosition, productoYPosition);
            productoPanel.add(c);

            productoXPosition += c.getWidth();

            if (productoPanel.getWidth() < productoXPosition + c.getWidth()) {
                productoYPosition += c.getHeight();
                productoXPosition = 0;
            }

            if (productoPanel.getHeight() < productoYPosition + c.getHeight()) {
                int w = productoPanel.getWidth();

                GroupLayout panelProductoLayout = (GroupLayout) productoPanel.getLayout();
                productoPanel.setLayout(panelProductoLayout);
                panelProductoLayout.setHorizontalGroup(
                        panelProductoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, w, Short.MAX_VALUE)
                );
                panelProductoLayout.setVerticalGroup(
                        panelProductoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, productoYPosition + c.getHeight(), Short.MAX_VALUE)
                );
            }
        }
        productoPanel.repaint();
    }

    private void reorganizaCuenta() {
        cuentaYPosition = 0;
        cuentaXPosition = 0;
        cuentaPanel.removeAll();

        for (Component c : cuentaButtonList) {
            c.setLocation(cuentaXPosition, cuentaYPosition);
            cuentaPanel.add(c);
            cuentaXPosition += c.getWidth();

            if (cuentaPanel.getHeight() < cuentaXPosition) {
                int h = cuentaPanel.getHeight();

                cuentaPanel.setMaximumSize(new Dimension(32767, 172));
                cuentaPanel.setPreferredSize(new Dimension(cuentaXPosition, 172));

                GroupLayout panelLayout = (GroupLayout) cuentaPanel.getLayout();
                cuentaPanel.setLayout(panelLayout);
                panelLayout.setHorizontalGroup(
                        panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, cuentaXPosition, Short.MAX_VALUE)
                );
                panelLayout.setVerticalGroup(
                        panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, h, Short.MAX_VALUE)
                );
            }
        }
        cuentaPanel.repaint();
    }

    private void resetCantidad() {
        cantidad = 1;
        lblCantidad.setText(cantidad + "");
    }

    private void converteCantidad() {
        cantidad = Integer.parseInt(lblCantidad.getText());
        lblCantidad.setName("false");
    }
    
    
    
    
    public void nuevaCuenta() {
        model.setCuenta(new Cuenta(cuentaCount + ""));
        labelTotal.setText("0,00");
        cuentaCount++;
        model.fireTableDataChanged();
        txtCuenta.setText(model.getCuenta().getNombre());
    }
    
    
    public void marcarTicket() {
        Cuenta c = model.getCuenta();
        c.setTicket(true);
    }

    //Arreglar internationalization
    public void borrarCuenta() {
        Cuenta c = model.getCuenta();
        int jop = JOptionPane.showConfirmDialog(productoPanel, "Desea Borrar esta Cuenta " + c.getNombre() + "?", "Confirmación", JOptionPane.YES_OPTION);
        if (jop == JOptionPane.YES_OPTION) {
            nuevaCuenta();
            c = null;
        }
    }
    
    public void crearCuenta() {
        guardaCuenta();
        nuevaCuenta();
    }
    
    public void abrirCuenta(Cuenta c) {
        if (model.getCuenta().getVentas().size() > 0) {
            guardaCuenta();
        }
        model.setCuenta(c);
        model.fireTableDataChanged();
        reorganizaCuenta();
        BigDecimal total = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        for (Venta v : c.getVentas()) {
            total = total.add(v.getTotal().setScale(2, BigDecimal.ROUND_HALF_DOWN));
        }
        labelTotal.setText(total.toString());
        txtCuenta.setText(c.getNombre());
    }

    public void cambiarNombre(String nombre) {
        model.getCuenta().setNombre(nombre);
    }

    public Cuenta pagar() {
        Cuenta c = model.getCuenta();
        BigDecimal total = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        for (Venta v : c.getVentas()) {
            total = total.add(v.getTotal().setScale(2, BigDecimal.ROUND_HALF_DOWN));
        }
        c.setTotal(total);
        return c;
    }
///crear grupos
    public void crearGrupos() {
        grupoButtonList.clear();
        for (Grupo g : grupoList) {
            JButton button = new JButton(g.getNombre());
            button.setSize(grupoWidth, grupoHeight);
            button.addActionListener(this::grupoButtonAction);
            grupoButtonList.add(button);
            button.setActionCommand(g.getId() + "");
            grupoMap.put(g.getId() + "", g);
        }
        reorganizaGrupo();
    }
    
    public BigDecimal sumaTotal(List<Venta> ventas) {
        BigDecimal total = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        for (Venta ve : ventas) {
            total = total.add(ve.getTotal().setScale(2, BigDecimal.ROUND_HALF_DOWN));
        }
        labelTotal.setText(total.toString());
        return total;
    }
    
    
    
    //Gets and Sets
    public int getGrupoHeight() {
        return grupoHeight;
    }

    public void setGrupoHeight(int grupoHeight) {
        this.grupoHeight = grupoHeight;
    }

    public int getGrupoWidth() {
        return grupoWidth;
    }

    public void setGrupoWidth(int grupoWidth) {
        this.grupoWidth = grupoWidth;
    }

    public int getGrupoXPosition() {
        return grupoXPosition;
    }

    public void setGrupoXPosition(int grupoXPosition) {
        this.grupoXPosition = grupoXPosition;
    }

    public int getGrupoYPosition() {
        return grupoYPosition;
    }

    public void setGrupoYPosition(int grupoYPosition) {
        this.grupoYPosition = grupoYPosition;
    }

    public int getGrupoCount() {
        return grupoCount;
    }

    public void setGrupoCount(int grupoCount) {
        this.grupoCount = grupoCount;
    }

    public List<JComponent> getGrupoButtonList() {
        return grupoButtonList;
    }

    public void setGrupoButtonList(List<JComponent> grupoButtonList) {
        this.grupoButtonList = grupoButtonList;
    }

    public Map<String, Grupo> getGrupoMap() {
        return grupoMap;
    }

    public void setGrupoMap(Map<String, Grupo> grupoMap) {
        this.grupoMap = grupoMap;
    }

    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
    }

    public JPanel getGrupoPanel() {
        return grupoPanel;
    }

    public void setGrupoPanel(JPanel grupoPanel) {
        this.grupoPanel = grupoPanel;
    }

    public int getProductoHeight() {
        return productoHeight;
    }

    public void setProductoHeight(int productoHeight) {
        this.productoHeight = productoHeight;
    }

    public int getProductoWidth() {
        return productoWidth;
    }

    public void setProductoWidth(int productoWidth) {
        this.productoWidth = productoWidth;
    }

    public int getProductoXPosition() {
        return productoXPosition;
    }

    public void setProductoXPosition(int productoXPosition) {
        this.productoXPosition = productoXPosition;
    }

    public int getProductoYPosition() {
        return productoYPosition;
    }

    public void setProductoYPosition(int productoYPosition) {
        this.productoYPosition = productoYPosition;
    }

    public int getProductoCount() {
        return productoCount;
    }

    public void setProductoCount(int productoCount) {
        this.productoCount = productoCount;
    }

    public List<JComponent> getProductoButtonList() {
        return productoButtonList;
    }

    public void setProductoButtonList(List<JComponent> productoButtonList) {
        this.productoButtonList = productoButtonList;
    }

    public Map<String, Producto> getProductoMap() {
        return productoMap;
    }

    public void setProductoMap(Map<String, Producto> productoMap) {
        this.productoMap = productoMap;
    }

    public List<Producto> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<Producto> productoList) {
        this.productoList = productoList;
    }

    public JPanel getProductoPanel() {
        return productoPanel;
    }

    public void setProductoPanel(JPanel productoPanel) {
        this.productoPanel = productoPanel;
    }

    public int getCuentaHeight() {
        return cuentaHeight;
    }

    public void setCuentaHeight(int cuentaHeight) {
        this.cuentaHeight = cuentaHeight;
    }

    public int getCuentaWidth() {
        return cuentaWidth;
    }

    public void setCuentaWidth(int cuentaWidth) {
        this.cuentaWidth = cuentaWidth;
    }

    public int getCuentaXPosition() {
        return cuentaXPosition;
    }

    public void setCuentaXPosition(int cuentaXPosition) {
        this.cuentaXPosition = cuentaXPosition;
    }

    public int getCuentaYPosition() {
        return cuentaYPosition;
    }

    public void setCuentaYPosition(int cuentaYPosition) {
        this.cuentaYPosition = cuentaYPosition;
    }

    public int getCuentaCount() {
        return cuentaCount;
    }

    public void setCuentaCount(int cuentaCount) {
        this.cuentaCount = cuentaCount;
    }

    public List<JComponent> getCuentaButtonList() {
        return cuentaButtonList;
    }

    public void setCuentaButtonList(List<JComponent> cuentaButtonList) {
        this.cuentaButtonList = cuentaButtonList;
    }

    public Map<String, Cuenta> getCuentaMap() {
        return cuentaMap;
    }

    public void setCuentaMap(Map<String, Cuenta> cuentaMap) {
        this.cuentaMap = cuentaMap;
    }

    public List<Cuenta> getCuentaList() {
        return cuentaList;
    }

    public void setCuentaList(List<Cuenta> cuentaList) {
        this.cuentaList = cuentaList;
    }

    public JPanel getCuentaPanel() {
        return cuentaPanel;
    }

    public void setCuentaPanel(JPanel cuentaPanel) {
        this.cuentaPanel = cuentaPanel;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        lblCantidad.setText(cantidad + "");
    }

    public JLabel getLabelTotal() {
        return labelTotal;
    }

    public void setLabelTotal(JLabel labelTotal) {
        this.labelTotal = labelTotal;
    }

    public JTextField getTxtCuenta() {
        return txtCuenta;
    }

    public void setTxtCuenta(JTextField txtCuenta) {
        this.txtCuenta = txtCuenta;
    }

    public JLabel getLblCantidad() {
        return lblCantidad;
    }

    public void setLblCantidad(JLabel lblCantidad) {
        this.lblCantidad = lblCantidad;
    }

    public JScrollPane getScrollTabla() {
        return scrollTabla;
    }

    public void setScrollTabla(JScrollPane scrollTabla) {
        this.scrollTabla = scrollTabla;
    }

    public Map<Producto, Venta> getVentasMap() {
        return ventasMap;
    }

    public void setVentasMap(Map<Producto, Venta> ventasMap) {
        this.ventasMap = ventasMap;
    }

    public Map<Cuenta, CuentaMap> getCuentasMap() {
        return cuentasMap;
    }

    public void setCuentasMap(Map<Cuenta, CuentaMap> cuentasMap) {
        this.cuentasMap = cuentasMap;
    }
}