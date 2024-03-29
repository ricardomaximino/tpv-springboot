package com.brasajava.view.producto;

import com.brasajava.model.Grupo;
import com.brasajava.service.ServicioGrupo;
import com.brasajava.util.ApplicationLocale;
import com.brasajava.util.interfaces.Internationalizable;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

/**
 * Esta clase representa la Busqueda de grupo con JDialog.
 *
 * @author Ricardo Maximino
 */
public class BuscarGrupo extends javax.swing.JDialog implements Internationalizable {

    private List<Grupo> listaDeGrupos;
    private final ApplicationContext context;
    private final MessageSource messageSource;
    private final ApplicationLocale applicationLocale;
    private String str;
    private boolean isChar;
    private ProductoView productoView;

    /**
     * Constructor para crear una instancia desta clase.
     *
     * @param parent del tipo JFrame.
     * @param context del tipo org.springframework.context.ApplicationConetxt.
     * <p>
     * Utilizando el context se pedirá una instancia de las clase:</p>
     * <ul>
     * <li>org.springframework.context.MessageSource</li>
     * <li>com.brasajava.util.ApplicationLocale</li>
     * <li>com.brasajava.view.producto.ShowProductoGrupoCommand</li>
     * </ul>
     */
    public BuscarGrupo(JFrame parent, ApplicationContext context) {
        super(parent, true);
        this.context = context;
        this.messageSource = context.getBean(MessageSource.class);
        this.applicationLocale = context.getBean(ApplicationLocale.class);
        listaDeGrupos = new ArrayList<>();
        for (Grupo g : context.getBean(ServicioGrupo.class).findAll()) {
            listaDeGrupos.add(g);
        }
        initComponents();
        ((GrupoTableModel) tabla.getModel()).getListaDeGrupo().addAll(listaDeGrupos);
        ((GrupoTableModel) tabla.getModel()).fireTableDataChanged();
    }

    /**
     * Constructor para crear una instancia desta clase pasando aparte del
     * context una lista de productos.
     *
     * @param parent del tipo JFrame.
     * @param context del tipo org.springframework.context.ApplicationContext.
     * @param list del tipo java.util.List&lt;Producto&gt;.
     * <p>
     * Utilizando el context se pedirá una instancia de las clase:</p>
     * <ul>
     * <li>org.springframework.context.MessageSource</li>
     * <li>com.brasajava.util.ApplicationLocale</li>
     * <li>com.brasajava.view.producto.ShowProductoGrupoCommand</li>
     * </ul>
     */
    public BuscarGrupo(JFrame parent, ApplicationContext context, List<Grupo> list) {
        super(parent, true);
        this.context = context;
        this.messageSource = context.getBean(MessageSource.class);
        this.applicationLocale = context.getBean(ApplicationLocale.class);
        this.listaDeGrupos = list;
        initComponents();
        ((GrupoTableModel) tabla.getModel()).getListaDeGrupo().addAll(listaDeGrupos);
        ((GrupoTableModel) tabla.getModel()).fireTableDataChanged();
    }

    /**
     * Actualiza la listaDeProductos con la base de datos.
     */
    public void refresh() {
        GrupoTableModel model = (GrupoTableModel) tabla.getModel();
        listaDeGrupos.clear();
        for (Grupo g : context.getBean(ServicioGrupo.class).findAll()) {
            listaDeGrupos.add(g);
        }
        model.getListaDeGrupo().clear();
        model.getListaDeGrupo().addAll(listaDeGrupos);
        model.fireTableDataChanged();
    }

    /**
     * Retorna el valor de la variable listaDeGrupos.
     *
     * @return del tipo java.util.List&lt;Grupo&gt;.
     */
    public List<Grupo> getListaDeProductos() {
        return listaDeGrupos;
    }

    /**
     * Configura el valor de la variable listaDeGrupos.
     *
     * @param listaDeGrupos del tipo java.util.List&lt;Grupo&gt;.
     */
    public void setListaDeProductos(List<Grupo> listaDeGrupos) {
        this.listaDeGrupos = listaDeGrupos;
    }

    /**
     * Retorna el valor de la variable productoView.
     *
     * @return del tipo com.brasajava.view.producto.ProductoView.
     */
    public ProductoView getProductoView() {
        return productoView;
    }

    /**
     * Configura la variable productoView.
     *
     * @param productoView del tipo com.brasajava.view.producto.ProductoView.
     */
    public void setProductoView(ProductoView productoView) {
        this.productoView = productoView;
    }
    
    /**
     * Actualiza toda la interfaz gráfica con el idioma seleccionado en la
     * instacia única de la clase
     * com.brasajava.util.ApplicationLocale.getLocale().
     */
    @Override
    public void refreshLanguage() {
        setWithInternationalization();
        ((GrupoTableModel) tabla.getModel()).refreshLanguage();
    }

    private void porNombreAction(KeyEvent evt) {
        //
        if (isChar) {
            str = txtPorNombre.getText() + evt.getKeyChar();
        } else {
            str = txtPorNombre.getText();
        }
        GrupoTableModel model = (GrupoTableModel) tabla.getModel();
        model.getListaDeGrupo().clear();
        if (str.isEmpty()) {
            model.getListaDeGrupo().addAll(listaDeGrupos);
        } else {
            for (Grupo g : listaDeGrupos) {
                if (g.getNombre().toUpperCase().contains(str.toUpperCase().subSequence(0, str.length()))) {
                    model.getListaDeGrupo().add(g);
                }
            }
        }
        model.fireTableDataChanged();

    }

    private GrupoTableModel getModel() {
        return context.getBean(GrupoTableModel.class);
    }

    private void setWithInternationalization() {
        lblPorNombre.setText(messageSource.getMessage("label_SearchGroupByName", null, applicationLocale.getLocale()));
    }

    /*
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {

        panel = new javax.swing.JPanel();
        lblPorNombre = new javax.swing.JLabel();
        txtPorNombre = new javax.swing.JTextField();
        scrollTabla = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblPorNombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblPorNombre.setText("BUSCA GRUPO POR NOMBRE");

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
        Grupo g;
        if (evt.getClickCount() == 2) {
            GrupoTableModel model = (GrupoTableModel) tabla.getModel();
            g = model.getListaDeGrupo().get(tabla.getSelectedRow());
            productoView.add(g);
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
