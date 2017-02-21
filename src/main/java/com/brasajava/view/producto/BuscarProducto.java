/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brasajava.view.producto;

import com.brasajava.model.Producto;
import com.brasajava.service.ServicioProducto;
import com.brasajava.view.principal.MainFrame;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Ricardo
 */
public class BuscarProducto extends javax.swing.JDialog {

    private List<Producto> listaDeProductos;
    private ApplicationContext context;
    private String str;
    private boolean isChar;
    private GrupoView grupoView;

    public BuscarProducto(JFrame parent, ApplicationContext context) {
        super(parent, true);
        initComponents();
        this.context = context;
        listaDeProductos = new ArrayList();
        for (Producto p : context.getBean(ServicioProducto.class).findAll()) {
            listaDeProductos.add(p);
        }
        ((ProductoTableModel) tabla.getModel()).getListaDeProducto().addAll(listaDeProductos);
    }
    public void refresh(){
        listaDeProductos.clear();
        for (Producto p : context.getBean(ServicioProducto.class).findAll()) {
            listaDeProductos.add(p);
        }
        ((ProductoTableModel) tabla.getModel()).getListaDeProducto().addAll(listaDeProductos);
    }

    public BuscarProducto(JFrame parent, ApplicationContext context, List<Producto> list) {
        super(parent, true);
        initComponents();
        this.context = context;
        this.listaDeProductos = list;
        ((ProductoTableModel) tabla.getModel()).getListaDeProducto().addAll(listaDeProductos);
    }

    public List<Producto> getListaDeProductos() {
        return listaDeProductos;
    }

    public void setListaDeProductos(List<Producto> listaDeProductos) {
        this.listaDeProductos = listaDeProductos;
    }

    public GrupoView getGrupoView() {
        return grupoView;
    }

    public void setGrupoView(GrupoView grupoView) {
        this.grupoView = grupoView;
    }

    private void porNombreAction(KeyEvent evt) {
        //
        if (isChar) {
            str = txtPorNombre.getText() + evt.getKeyChar();
        } else {
            str = txtPorNombre.getText();
        }
        ProductoTableModel model = (ProductoTableModel) tabla.getModel();
        model.getListaDeProducto().clear();
        if (str.isEmpty()) {
            model.getListaDeProducto().addAll(listaDeProductos);
        } else {
            for (Producto p : listaDeProductos) {
                if (p.getNombre().toUpperCase().contains(str.toUpperCase().subSequence(0, str.length()))) {
                    model.getListaDeProducto().add(p);
                }
            }
        }
        model.fireTableDataChanged();

    }

    private ProductoTableModel getModel() {
        return new ProductoTableModel();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        lblPorNombre = new javax.swing.JLabel();
        txtPorNombre = new javax.swing.JTextField();
        scrollTabla = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblPorNombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblPorNombre.setText("BUSCA PRODUCTO POR NOMBRE");

        txtPorNombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPorNombre.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtPorNombreInputMethodTextChanged(evt);
            }
        });
        txtPorNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPorNombreActionPerformed(evt);
            }
        });
        txtPorNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPorNombreKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPorNombreKeyTyped(evt);
            }
        });

        tabla.setModel(getModel());
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        scrollTabla.setViewportView(tabla);

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblPorNombre)
                .addGap(40, 40, 40)
                .addComponent(txtPorNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPorNombre)
                    .addComponent(txtPorNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(scrollTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPorNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPorNombreKeyTyped
        porNombreAction(evt);
    }//GEN-LAST:event_txtPorNombreKeyTyped

    private void txtPorNombreInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtPorNombreInputMethodTextChanged

    }//GEN-LAST:event_txtPorNombreInputMethodTextChanged

    private void txtPorNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPorNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPorNombreActionPerformed

    private void txtPorNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPorNombreKeyPressed
        if (evt.getKeyCode() == 8 || evt.getKeyCode() == 10) {
            isChar = false;
        } else {
            isChar = true;
        }
    }//GEN-LAST:event_txtPorNombreKeyPressed

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked
        Producto p;
        if (evt.getClickCount() == 2) {
            ProductoTableModel model = (ProductoTableModel) tabla.getModel();
            p = model.getListaDeProducto().get(tabla.getSelectedRow());
            if (this.isModal()) {
                grupoView.add(p);
            } else {
                ProductoView pv = context.getBean(ProductoView.class);
                pv.setProducto(p);
                context.getBean(MainFrame.class).getDesktopPane().add(pv);
                pv.setVisible(true);
            }
        }
    }//GEN-LAST:event_tablaMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblPorNombre;
    private javax.swing.JPanel panel;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField txtPorNombre;
    // End of variables declaration//GEN-END:variables
}
