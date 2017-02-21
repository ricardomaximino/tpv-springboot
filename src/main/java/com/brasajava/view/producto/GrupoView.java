/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brasajava.view.producto;

import com.brasajava.model.Grupo;
import com.brasajava.model.Producto;
import com.brasajava.service.ServicioGrupo;
import com.brasajava.view.principal.MainFrame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Ricardo
 */
public class GrupoView extends javax.swing.JInternalFrame {

    private Grupo grupo;
    private final ApplicationContext context;

    private String grupoImage;

    private static final String GUARDAR = "GUARDAR";
    private static final String CANCELAR = "CANCELAR";

    /**
     * Creates new form ProductoGrupo
     *
     * @param context
     */
    public GrupoView(ApplicationContext context) {
        this.context = context;
        initComponents();
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
        setGrupoFields();
    }

    public void add(Producto pro) {
        ProductoTableModel model = (ProductoTableModel) tablaParaPruductoGrupo.getModel();
        if (!model.getListaDeProducto().contains(pro)) {
            model.getListaDeProducto().add(pro);
            model.fireTableDataChanged();
        } else {
            JOptionPane.showMessageDialog(this, "já tem");
        }
    }

    private long parseLong(String txt) {
        if (txt.isEmpty()) {
            return 0;
        } else {
            return Long.parseLong(txt);
        }

    }

    private void setGrupoFields() {
        lblCodigoGrupoValue.setText(grupo.getId() + "");
        txtNombreGrupo.setText(grupo.getNombre());
        txtDescripcionGrupo.setText(grupo.getDescripcion());
        ProductoTableModel model = (ProductoTableModel) tablaParaPruductoGrupo.getModel();
        for (Producto p : grupo.getProductos()) {
            model.getListaDeProducto().add(p);
        }
        ckbActivo.setSelected(grupo.isActivo());
        grupoImage = grupo.getImage();

        lblMolduraGrupo.setIcon(null);
        if (grupo.getImage() != null && !grupo.getImage().isEmpty()) {
            try {
                Image image = new ImageIcon(getClass().getResource("/images/" + grupo.getImage())).getImage().getScaledInstance(190, 170, Image.SCALE_SMOOTH);
                lblMolduraGrupo.setIcon(new ImageIcon(image));
            } catch (NullPointerException ex) {
            }
        }
    }

    private void colectarDatosDelGrupo() {
        grupo.setDescripcion(txtDescripcionGrupo.getText());
        grupo.setId(parseLong(lblCodigoGrupoValue.getText()));
        grupo.setImage(grupoImage);
        grupo.setNombre(txtNombreGrupo.getText());
        grupo.setActivo(ckbActivo.isSelected());
        grupo.getProductos().clear();
        for (Producto p : ((ProductoTableModel) tablaParaPruductoGrupo.getModel()).getListaDeProducto()) {
            grupo.getProductos().add(p);

        }
        if (!grupo.getNombre().isEmpty()) {
            this.grupo = context.getBean(ServicioGrupo.class).save(grupo);
            setGrupoFields();
        }
    }

    private ProductoTableModel getTableModel() {
        return new ProductoTableModel();
    }

    private void btnGrupoAction(ActionEvent evt) {
        switch (evt.getActionCommand()) {
            case GUARDAR:
                //checkear validez de los datos
                //pasar del visual para el objeto
                colectarDatosDelGrupo();
                //guardar objeto y actualizar (por el còdigo por si es un nuevo)
                break;
            case CANCELAR:
                setGrupoFields();
                break;
        }
    }

    private void popUpMenu(MouseEvent evt) {
        if (evt.getButton() == 3) {
            if (tablaParaPruductoGrupo.getSelectedRow() == -1) {
                menuItemQuitar.setEnabled(false);
            }else{
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
        panelGrupo = new javax.swing.JPanel();
        lblCodigoGrupo = new javax.swing.JLabel();
        lblNombreGrupo = new javax.swing.JLabel();
        lblDescripcionGrupo = new javax.swing.JLabel();
        lblCodigoGrupoValue = new javax.swing.JLabel();
        txtNombreGrupo = new javax.swing.JTextField();
        txtDescripcionGrupo = new javax.swing.JTextField();
        lblImageGrupo = new javax.swing.JLabel();
        lblMolduraGrupo = new javax.swing.JLabel();
        scrollTabla = new javax.swing.JScrollPane();
        tablaParaPruductoGrupo = new javax.swing.JTable();
        btnActualizarYCancelarGrupo = new javax.swing.JButton();
        btnNuevoYGuardarGrupo = new javax.swing.JButton();
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
        setPreferredSize(new java.awt.Dimension(517, 532));

        lblCodigoGrupo.setText("COD.");

        lblNombreGrupo.setText("NOMBRE");

        lblDescripcionGrupo.setText("DESCRIPCION");

        lblCodigoGrupoValue.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigoGrupoValue.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblImageGrupo.setText("IMAGEN");

        lblMolduraGrupo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMolduraGrupo.setText("IMAGEN");
        lblMolduraGrupo.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lblMolduraGrupo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblMolduraGrupo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMolduraGrupoMouseClicked(evt);
            }
        });

        scrollTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scrollTablaMouseClicked(evt);
            }
        });

        tablaParaPruductoGrupo.setModel(getTableModel());
        tablaParaPruductoGrupo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaParaPruductoGrupoMouseClicked(evt);
            }
        });
        scrollTabla.setViewportView(tablaParaPruductoGrupo);

        btnActualizarYCancelarGrupo.setText("CANCELAR");
        btnActualizarYCancelarGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarYCancelarGrupoActionPerformed(evt);
            }
        });

        btnNuevoYGuardarGrupo.setText("GUARDAR");
        btnNuevoYGuardarGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoYGuardarGrupoActionPerformed(evt);
            }
        });

        ckbActivo.setText("GRUPO ACTIVO");

        javax.swing.GroupLayout panelGrupoLayout = new javax.swing.GroupLayout(panelGrupo);
        panelGrupo.setLayout(panelGrupoLayout);
        panelGrupoLayout.setHorizontalGroup(
            panelGrupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGrupoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGrupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGrupoLayout.createSequentialGroup()
                        .addComponent(scrollTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGrupoLayout.createSequentialGroup()
                        .addGroup(panelGrupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCodigoGrupoValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNombreGrupo, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGrupoLayout.createSequentialGroup()
                                .addComponent(btnActualizarYCancelarGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnNuevoYGuardarGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGrupoLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(panelGrupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDescripcionGrupo)
                                    .addComponent(txtDescripcionGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelGrupoLayout.createSequentialGroup()
                                        .addComponent(ckbActivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(146, 146, 146))))
                            .addGroup(panelGrupoLayout.createSequentialGroup()
                                .addGroup(panelGrupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCodigoGrupo)
                                    .addComponent(lblNombreGrupo))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelGrupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGrupoLayout.createSequentialGroup()
                                .addComponent(lblMolduraGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGrupoLayout.createSequentialGroup()
                                .addComponent(lblImageGrupo)
                                .addGap(96, 96, 96))))))
        );
        panelGrupoLayout.setVerticalGroup(
            panelGrupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGrupoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGrupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblImageGrupo)
                    .addComponent(btnActualizarYCancelarGrupo)
                    .addComponent(btnNuevoYGuardarGrupo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelGrupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelGrupoLayout.createSequentialGroup()
                        .addComponent(lblCodigoGrupo)
                        .addGap(18, 18, 18)
                        .addComponent(lblCodigoGrupoValue, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNombreGrupo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNombreGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDescripcionGrupo)
                        .addGap(18, 18, 18)
                        .addComponent(txtDescripcionGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelGrupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblMolduraGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelGrupoLayout.createSequentialGroup()
                            .addGap(180, 180, 180)
                            .addComponent(ckbActivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addComponent(scrollTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 479, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panelGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 473, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 12, Short.MAX_VALUE)
                    .addComponent(panelGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 13, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarYCancelarGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarYCancelarGrupoActionPerformed
        btnGrupoAction(evt);
    }//GEN-LAST:event_btnActualizarYCancelarGrupoActionPerformed

    private void btnNuevoYGuardarGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoYGuardarGrupoActionPerformed
        btnGrupoAction(evt);
    }//GEN-LAST:event_btnNuevoYGuardarGrupoActionPerformed

    private void lblMolduraGrupoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMolduraGrupoMouseClicked
        JFileChooser fc = new JFileChooser();
        int respuesta = fc.showOpenDialog(this);
        if (respuesta == JFileChooser.APPROVE_OPTION) {
            grupoImage = fc.getSelectedFile().getName();
        }
    }//GEN-LAST:event_lblMolduraGrupoMouseClicked

    private void tablaParaPruductoGrupoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaParaPruductoGrupoMouseClicked
        popUpMenu(evt);
            Producto p;
        if (evt.getClickCount() == 2) {
            ProductoTableModel model = (ProductoTableModel) tablaParaPruductoGrupo.getModel();
            p = model.getListaDeProducto().get(tablaParaPruductoGrupo.getSelectedRow());
                ProductoView pv = context.getBean(ProductoView.class);
                pv.setProducto(p);
                context.getBean(MainFrame.class).getDesktopPane().add(pv);
                pv.setVisible(true);
        }
    }//GEN-LAST:event_tablaParaPruductoGrupoMouseClicked

    private void scrollTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollTablaMouseClicked
        popUpMenu(evt);
    }//GEN-LAST:event_scrollTablaMouseClicked

    private void menuItemAñadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAñadirActionPerformed
        BuscarProducto bp = context.getBean("buscarProductoParaAñadir", BuscarProducto.class);
        bp.refresh();
        bp.setGrupoView(this);
        bp.setVisible(true);
    }//GEN-LAST:event_menuItemAñadirActionPerformed

    private void menuItemQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemQuitarActionPerformed
        ProductoTableModel model = (ProductoTableModel) tablaParaPruductoGrupo.getModel();
        int row = tablaParaPruductoGrupo.getSelectedRow();
        model.getListaDeProducto().remove(row);
        model.fireTableRowsDeleted(row, row);
    }//GEN-LAST:event_menuItemQuitarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarYCancelarGrupo;
    private javax.swing.JButton btnNuevoYGuardarGrupo;
    private javax.swing.JCheckBox ckbActivo;
    private javax.swing.JLabel lblCodigoGrupo;
    private javax.swing.JLabel lblCodigoGrupoValue;
    private javax.swing.JLabel lblDescripcionGrupo;
    private javax.swing.JLabel lblImageGrupo;
    private javax.swing.JLabel lblMolduraGrupo;
    private javax.swing.JLabel lblNombreGrupo;
    private javax.swing.JMenuItem menuItemAñadir;
    private javax.swing.JMenuItem menuItemQuitar;
    private javax.swing.JPanel panelGrupo;
    private javax.swing.JPopupMenu popUpMenu;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JTable tablaParaPruductoGrupo;
    private javax.swing.JTextField txtDescripcionGrupo;
    private javax.swing.JTextField txtNombreGrupo;
    // End of variables declaration//GEN-END:variables
}