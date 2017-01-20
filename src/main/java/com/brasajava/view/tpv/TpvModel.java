package com.brasajava.view.tpv;

import com.brasajava.model.Cuenta;
import com.brasajava.model.Grupo;
import com.brasajava.model.Producto;
import com.brasajava.model.Venta;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

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

    private CajaTableModel model;
    private int cantidad;
    private JLabel labelTotal;

    public TpvModel() {
        //ButtonList
        grupoButtonList = new ArrayList();
        productoButtonList = new ArrayList();
        cuentaButtonList = new ArrayList();
        //Map
        grupoMap = new HashMap<>();
        productoMap = new HashMap<>();
        cuentaMap = new HashMap<>();
        //
        grupoHeight = grupoWidth = productoHeight = productoWidth = cuentaHeight = cuentaWidth = 200;
        cantidad = 1;
        cuentaCount = 1;

    }

    private void guardaCuenta() {
        Cuenta cuenta = model.getCuenta();
        cuentaMap.put(cuenta.getName(), cuenta);
        JButton b = new JButton(cuenta.getName());
        b.setActionCommand(cuenta.getName());
        b.setSize(cuentaWidth, cuentaHeight);
        b.addActionListener(this::cuentaButtonAction);
        cuentaButtonList.add(b);
        reorganizaCuenta();

    }

    private void nuevaCuenta() {
        model.setCuenta(new Cuenta(cuentaCount + ""));
        labelTotal.setText("0,00");
        cuentaCount++;
        model.fireTableDataChanged();
    }

    public void crearCuenta() {
        guardaCuenta();
        nuevaCuenta();
    }

    private void abrirCuenta(ActionEvent e) {
        if (model.getCuenta().getVentas().size() > 0) {
            guardaCuenta();
        }
        JButton b = (JButton) e.getSource();
        Cuenta c = cuentaMap.get(b.getActionCommand());
        cuentaMap.remove(c.getName());
        cuentaButtonList.remove(b);
        model.setCuenta(c);
        model.fireTableDataChanged();
        reorganizaCuenta();
        BigDecimal total = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        for (Venta v : c.getVentas()) {
            total = total.add(v.getTotal().setScale(2, BigDecimal.ROUND_HALF_DOWN));
        }
        labelTotal.setText(total.toString());
    }

    public void crear() {
        grupoButtonList.clear();
        for (Grupo g : grupoList) {
            JButton button = new JButton(g.getNombre());
            button.setSize(200, 200);
            button.addActionListener(this::grupoButtonAction);
            grupoButtonList.add(button);
            button.setActionCommand(g.getId() + "");
            grupoMap.put(g.getId() + "", g);
        }
        reorganizaGrupo();
    }

    private void grupoButtonAction(ActionEvent e) {
        Grupo g = grupoMap.get(((JButton) e.getSource()).getActionCommand());
        productoButtonList.clear();
        for (Producto p : g.getProductos()) {
            JButton button = new JButton(p.getName());
            button.addActionListener(this::productoButtonAction);
            button.setActionCommand(p.getId() + "");
            button.setSize(200, 200);
            productoButtonList.add(button);
            reoganizaProducto();
            productoMap.put(p.getId() + "", p);
        }
    }

    private void productoButtonAction(ActionEvent e) {
        Producto p = productoMap.get(((JButton) e.getSource()).getActionCommand());
        Cuenta c = model.getCuenta();
        List<Venta> ventas = c.getVentas();
        Venta venta = new Venta();
        venta.setCuenta(c);
        venta.setVenta(cantidad, p);
        ventas.add(venta);

        BigDecimal total = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        for (Venta v : ventas) {
            total = total.add(v.getTotal().setScale(2, BigDecimal.ROUND_HALF_DOWN));
        }
        labelTotal.setText(total.toString());
        model.fireTableDataChanged();
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
        productoHeight = 0;
        productoWidth = 0;
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

    public CajaTableModel getModel() {
        return model;
    }

    public void setModel(CajaTableModel model) {
        this.model = model;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public JLabel getLabelTotal() {
        return labelTotal;
    }

    public void setLabelTotal(JLabel labelTotal) {
        this.labelTotal = labelTotal;
    }

}
