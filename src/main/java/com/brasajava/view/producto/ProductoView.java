/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brasajava.view.producto;

import com.brasajava.model.Grupo;
import com.brasajava.model.Producto;
import com.brasajava.service.ServicioGrupo;
import com.brasajava.service.ServicioProducto;
import com.brasajava.view.principal.MainFrame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Ricardo
 */
public class ProductoView extends javax.swing.JInternalFrame {

    private Producto producto;
    private DefaultListModel<Grupo> listModel;
    private final ApplicationContext context;

    private String productoImage;

    private static final String GUARDAR = "GUARDAR";
    private static final String CANCELAR = "CANCELAR";

    /**
     * Creates new form ProductoGrupo
     *
     * @param context
     */
    public ProductoView(ApplicationContext context) {
        this.context = context;
        initComponents();
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        setProductoFields();
    }

    public void add(Grupo grup) {
        if (!listModel.contains(grup)) {
            listModel.addElement(grup);
        }
    }

    private void setProductoFields() {
        lblCodigoProductoValue.setText(producto.getId() + "");
        txtNombreProducto.setText(producto.getNombre());
        txtDescripcionProducto.setText(producto.getDescripcion());
        txtCustoDelProducto.setText(producto.getCusto() != null ? producto.getCusto().toString() : "");
        txtMargen.setText(producto.getMargen() + "");
        txtPrecioSinIva.setText(producto.getPrecioSinIva() != null ? producto.getPrecioSinIva().toString() : "");
        txtIva.setText(producto.getIva() + "");
        txtPrecioMasIva.setText(producto.getPrecioMasIva() != null ? producto.getPrecioMasIva().toString() : "");
        txtAlmacen.setText(producto.getAlmacen() + "");
        productoImage = producto.getImage();
        ckbActivo.setSelected(producto.isActivo());
        listModel.clear();
        for (Grupo g : producto.getGrupos()) {
            listModel.addElement(g);
        }
        lblMolduraProducto.setIcon(null);
        if (producto.getImage() != null && !producto.getImage().isEmpty()) {
            try {
                Image image = new ImageIcon(getClass().getResource("/images/" + producto.getImage())).getImage().getScaledInstance(190, 170, Image.SCALE_SMOOTH);
                lblMolduraProducto.setIcon(new ImageIcon(image));
            } catch (NullPointerException ex) {
            }
        }
    }

    private void colectarDatosDelProducto() {
        producto.setActivo(ckbActivo.isSelected());
        producto.setAlmacen(parseInt(txtAlmacen.getText()));
        producto.setCusto(parseBigDecimal(txtCustoDelProducto.getText()));
        producto.setDescripcion(txtDescripcionProducto.getText());
        producto.setId(parseLong(lblCodigoProductoValue.getText()));
        producto.setImage(productoImage);
        producto.setIva(parseDouble(txtIva.getText()));
        producto.setMargen(parseDouble(txtMargen.getText()));
        producto.setNombre(txtNombreProducto.getText());
        producto.setPrecioMasIva(parseBigDecimal(txtPrecioMasIva.getText()));
        producto.setPrecioSinIva(parseBigDecimal(txtPrecioSinIva.getText()));
        producto.getGrupos().clear();

        if (!producto.getNombre().isEmpty()) {
            this.producto = context.getBean(ServicioProducto.class).save(producto);
            for (int i = 0; i < listModel.size(); i++) {
                Grupo grupo = listModel.getElementAt(i);
                producto.getGrupos().add(grupo);
                if (!grupo.getProductos().contains(producto)) {
                    grupo.getProductos().add(producto);
                    context.getBean(ServicioGrupo.class).save(grupo);
                }
            }
            setProductoFields();
        }
    }

    private int parseInt(String txt) {
        if (txt.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(txt);
        }

    }

    private long parseLong(String txt) {
        if (txt.isEmpty()) {
            return 0;
        } else {
            return Long.parseLong(txt);
        }

    }

    private BigDecimal parseBigDecimal(String txt) {
        System.out.println(txt);
        if (txt.isEmpty()) {
            return new BigDecimal("0.00");
        } else {
            return new BigDecimal(txt);
        }
    }

    private double parseDouble(String txt) {
        if (txt.isEmpty()) {
            return 0;
        } else {
            return Double.parseDouble(txt);
        }
    }

    private DefaultListModel<Grupo> getListModel() {
        DefaultListModel<Grupo> model = new DefaultListModel<>();
        listModel = model;
        return model;
    }

    private void btnProductoAction(ActionEvent evt) {
        switch (evt.getActionCommand()) {
            case GUARDAR:
                colectarDatosDelProducto();
                break;
            case CANCELAR:
                setProductoFields();
                break;
        }
    }

    private void popUpMenu(MouseEvent evt) {
        if (evt.getButton() == 3) {
            if (listProducto.getSelectedIndex() == -1) {
                menuItemQuitar.setEnabled(false);
            } else {
                menuItemQuitar.setEnabled(true);
            }
            popUpMenu.show(this, evt.getX() + 45, evt.getY() + 315);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popUpMenu = new javax.swing.JPopupMenu();
        menuItemAñadir = new javax.swing.JMenuItem();
        menuItemQuitar = new javax.swing.JMenuItem();
        panelProducto = new javax.swing.JPanel();
        lblCodigoProducto = new javax.swing.JLabel();
        lblNombreProducto = new javax.swing.JLabel();
        lblDescripcionDelProducto = new javax.swing.JLabel();
        lblCustoDelProducto = new javax.swing.JLabel();
        lblPrecioSinIva = new javax.swing.JLabel();
        lblPrecioConIva = new javax.swing.JLabel();
        lblIva = new javax.swing.JLabel();
        lblAlmacen = new javax.swing.JLabel();
        lblGruposProducto = new javax.swing.JLabel();
        lblImagenProducto = new javax.swing.JLabel();
        lblMolduraProducto = new javax.swing.JLabel();
        lblCodigoProductoValue = new javax.swing.JLabel();
        txtNombreProducto = new javax.swing.JTextField();
        txtDescripcionProducto = new javax.swing.JTextField();
        txtCustoDelProducto = new javax.swing.JTextField();
        txtPrecioSinIva = new javax.swing.JTextField();
        txtPrecioMasIva = new javax.swing.JTextField();
        txtIva = new javax.swing.JTextField();
        txtAlmacen = new javax.swing.JTextField();
        lblMargenDelProducto = new javax.swing.JLabel();
        txtMargen = new javax.swing.JTextField();
        btnActualizarYCancelarProducto = new javax.swing.JButton();
        btnNuevoYGuardarProducto = new javax.swing.JButton();
        scrollList = new javax.swing.JScrollPane();
        listProducto = new javax.swing.JList<>();
        ckbActivo = new javax.swing.JCheckBox();

        menuItemAñadir.setText("AÑADIR");
        menuItemAñadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAñadirActionPerformed(evt);
            }
        });
        popUpMenu.add(menuItemAñadir);

        menuItemQuitar.setText("QUITAR");
        menuItemQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemQuitarActionPerformed(evt);
            }
        });
        popUpMenu.add(menuItemQuitar);

        setClosable(true);
        setIconifiable(true);

        lblCodigoProducto.setText("COD.");

        lblNombreProducto.setText("NOMBRE");

        lblDescripcionDelProducto.setText("DESCRIPCION");

        lblCustoDelProducto.setText("CUSTO DEL PROD.");

        lblPrecioSinIva.setText("PRECIO SIN I.V.A.");

        lblPrecioConIva.setText("PRECIO CON I.V.A.");

        lblIva.setText("I.V.A. (%)");

        lblAlmacen.setText("ALMACEN");

        lblGruposProducto.setText("GRUPOS");

        lblImagenProducto.setText("IMAGEN");

        lblMolduraProducto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        java.awt.Image image = new javax.swing.ImageIcon(getClass().getResource("/images/hitPina.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
        lblMolduraProducto.setText("IMAGEN");
        lblMolduraProducto.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lblMolduraProducto.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblMolduraProducto.setPreferredSize(new java.awt.Dimension(200, 200));
        lblMolduraProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMolduraProductoMouseClicked(evt);
            }
        });

        lblCodigoProductoValue.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigoProductoValue.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtCustoDelProducto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txtPrecioSinIva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txtPrecioMasIva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txtIva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txtAlmacen.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        lblMargenDelProducto.setText("MARGEN (%)");

        txtMargen.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        btnActualizarYCancelarProducto.setText("CANCELAR");
        btnActualizarYCancelarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarYCancelarProductoActionPerformed(evt);
            }
        });

        btnNuevoYGuardarProducto.setText("GUARDAR");
        btnNuevoYGuardarProducto.setToolTipText("");
        btnNuevoYGuardarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoYGuardarProductoActionPerformed(evt);
            }
        });

        listProducto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        listProducto.setModel(getListModel());
        listProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listProductoMouseClicked(evt);
            }
        });
        scrollList.setViewportView(listProducto);

        ckbActivo.setText("PRODUCTO ACTIVO");

        javax.swing.GroupLayout panelProductoLayout = new javax.swing.GroupLayout(panelProducto);
        panelProducto.setLayout(panelProductoLayout);
        panelProductoLayout.setHorizontalGroup(
            panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProductoLayout.createSequentialGroup()
                        .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCodigoProducto)
                            .addComponent(lblNombreProducto)
                            .addComponent(lblDescripcionDelProducto)
                            .addComponent(lblCustoDelProducto)
                            .addComponent(lblPrecioSinIva)
                            .addComponent(lblPrecioConIva))
                        .addGap(25, 25, 25)
                        .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelProductoLayout.createSequentialGroup()
                                .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtPrecioMasIva, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                                    .addComponent(txtPrecioSinIva, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCustoDelProducto, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(42, 42, 42)
                                .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAlmacen)
                                    .addComponent(lblIva)
                                    .addComponent(lblMargenDelProducto))
                                .addGap(48, 48, 48)
                                .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtIva)
                                    .addComponent(txtMargen)
                                    .addComponent(txtAlmacen)))
                            .addComponent(txtDescripcionProducto)
                            .addComponent(txtNombreProducto)))
                    .addGroup(panelProductoLayout.createSequentialGroup()
                        .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelProductoLayout.createSequentialGroup()
                                .addComponent(lblCodigoProductoValue, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(btnActualizarYCancelarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(58, 58, 58)
                                .addComponent(btnNuevoYGuardarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelProductoLayout.createSequentialGroup()
                                .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelProductoLayout.createSequentialGroup()
                                        .addGap(55, 55, 55)
                                        .addComponent(lblGruposProducto))
                                    .addComponent(scrollList, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(39, 39, 39)
                                .addComponent(lblMolduraProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelProductoLayout.createSequentialGroup()
                        .addComponent(ckbActivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblImagenProducto)
                        .addGap(101, 101, 101)))
                .addContainerGap())
        );

        panelProductoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnActualizarYCancelarProducto, btnNuevoYGuardarProducto});

        panelProductoLayout.setVerticalGroup(
            panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelProductoLayout.createSequentialGroup()
                        .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblCodigoProducto)
                                .addComponent(lblCodigoProductoValue, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnActualizarYCancelarProducto)
                                .addComponent(btnNuevoYGuardarProducto)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNombreProducto)
                            .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDescripcionDelProducto)
                            .addComponent(txtDescripcionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCustoDelProducto)
                            .addComponent(txtCustoDelProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMargen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMargenDelProducto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPrecioSinIva)
                            .addComponent(txtPrecioSinIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblIva))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPrecioConIva)
                            .addComponent(txtPrecioMasIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAlmacen)
                            .addComponent(txtAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ckbActivo))
                    .addComponent(lblImagenProducto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelProductoLayout.createSequentialGroup()
                        .addComponent(lblGruposProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scrollList))
                    .addComponent(lblMolduraProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
                .addGap(9, 9, 9))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(panelProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(panelProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoYGuardarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoYGuardarProductoActionPerformed
        btnProductoAction(evt);
    }//GEN-LAST:event_btnNuevoYGuardarProductoActionPerformed

    private void btnActualizarYCancelarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarYCancelarProductoActionPerformed
        btnProductoAction(evt);
    }//GEN-LAST:event_btnActualizarYCancelarProductoActionPerformed

    private void lblMolduraProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMolduraProductoMouseClicked
        JFileChooser fc = new JFileChooser();
        int respuesta = fc.showOpenDialog(this);
        if (respuesta == JFileChooser.APPROVE_OPTION) {
            productoImage = fc.getSelectedFile().getName();
        }
    }//GEN-LAST:event_lblMolduraProductoMouseClicked

    private void listProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listProductoMouseClicked
        popUpMenu(evt);
        if(evt.getClickCount() == 2){
            int index = listProducto.getSelectedIndex();
            if(index > -1){
                Grupo grupo = listModel.getElementAt(index);
                GrupoView gv = context.getBean(GrupoView.class);
                gv.setGrupo(grupo);
                context.getBean(MainFrame.class).getDesktopPane().add(gv);
                gv.setVisible(true);
            }
        }
    }//GEN-LAST:event_listProductoMouseClicked

    private void menuItemAñadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAñadirActionPerformed
        BuscarGrupo bg = context.getBean("buscarGrupoParaAñadir", BuscarGrupo.class);
        bg.refresh();
        bg.setProductoView(this);
        bg.setVisible(true);
    }//GEN-LAST:event_menuItemAñadirActionPerformed

    private void menuItemQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemQuitarActionPerformed
        int index = listProducto.getSelectedIndex();
        listModel.removeElementAt(index);
    }//GEN-LAST:event_menuItemQuitarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarYCancelarProducto;
    private javax.swing.JButton btnNuevoYGuardarProducto;
    private javax.swing.JCheckBox ckbActivo;
    private javax.swing.JLabel lblAlmacen;
    private javax.swing.JLabel lblCodigoProducto;
    private javax.swing.JLabel lblCodigoProductoValue;
    private javax.swing.JLabel lblCustoDelProducto;
    private javax.swing.JLabel lblDescripcionDelProducto;
    private javax.swing.JLabel lblGruposProducto;
    private javax.swing.JLabel lblImagenProducto;
    private javax.swing.JLabel lblIva;
    private javax.swing.JLabel lblMargenDelProducto;
    private javax.swing.JLabel lblMolduraProducto;
    private javax.swing.JLabel lblNombreProducto;
    private javax.swing.JLabel lblPrecioConIva;
    private javax.swing.JLabel lblPrecioSinIva;
    private javax.swing.JList<Grupo> listProducto;
    private javax.swing.JMenuItem menuItemAñadir;
    private javax.swing.JMenuItem menuItemQuitar;
    private javax.swing.JPanel panelProducto;
    private javax.swing.JPopupMenu popUpMenu;
    private javax.swing.JScrollPane scrollList;
    private javax.swing.JTextField txtAlmacen;
    private javax.swing.JTextField txtCustoDelProducto;
    private javax.swing.JTextField txtDescripcionProducto;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtMargen;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtPrecioMasIva;
    private javax.swing.JTextField txtPrecioSinIva;
    // End of variables declaration//GEN-END:variables
}
