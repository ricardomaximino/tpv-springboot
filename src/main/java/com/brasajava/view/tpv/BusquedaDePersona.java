package com.brasajava.view.tpv;

import com.brasajava.model.Persona;
import com.brasajava.util.ApplicationLocale;
import com.brasajava.util.Session;
import com.brasajava.util.interfaces.Internationalizable;
import com.brasajava.util.interfaces.MiTableModel;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

/**
 * Esta clase representa la búsqueda de persona.
 * @author Ricardo Maximino
 */
public class BusquedaDePersona extends javax.swing.JDialog implements Internationalizable {

    private final ApplicationContext context;
    private final MessageSource messageSource;
    private final ApplicationLocale applicationLocale;
    private boolean cliente;
    private boolean isChar;
    private String str;
    private List<Persona> lista;

    private TPV tpv;

    /**
     * El único constructor para crear una instancia desta clase.
     *
     * @param tpv del tipo com.brasajava.view.tpv.TPV.
     * @param context del tipo org.springframework.context.ApplicationContext.
     * 
     * <p>Utilizando el context se pedirá una instancia de las clase:</p>
     * <ul>
     * <li>org.springframework.context.MessageSource</li>
     * <li>com.brasajava.util.ApplicationLocale</li>
     * </ul>
     * lista = new ArrayLista&lt;&gt;();
     */
    public BusquedaDePersona(TPV tpv, ApplicationContext context) {
        super(tpv, true);
        this.tpv = tpv;
        this.context = context;
        this.messageSource = context.getBean(MessageSource.class);
        this.applicationLocale = context.getBean(ApplicationLocale.class);
        lista = new ArrayList<>();
        initComponents();
        setWithInternationalization();
    }

    /**
     * Retorna el valor de la variable lista.
     * @return del tipo java.util.List&lt;com.brasajava.model.Persona&gt;.
     */
    public List<Persona> getLista() {
        return lista;
    }

    /**
     * Configura el valor de la variable lista.
     * @param lista del tipo java.util.List&lt;com.brasajava.model.Persona&gt;.
     */
    public void setLista(List<Persona> lista) {
        this.lista = lista;
        setModelData();
    }


    /**
     * Configura el valor de la variable cliente.
     * @param cliente del tipo com.brasajava.model.Cliente.
     */
    public void setCliente(boolean cliente) {
        this.cliente = cliente;
    }

    /**
     * Actualiza toda la interfaz gráfica con el idioma seleccionado en la
     * instacia única de la clase
     * com.brasajava.util.ApplicationLocale.getLocale().
     */
    @Override
    public void refreshLanguage() {
        setWithInternationalization();
        ((MiTableModel) tabla.getModel()).refreshLanguage();
        ((MiTableModel) tabla.getModel()).fireTableStructureChanged();
    }
    
    private void setModelData(){
        MiTableModel model = (MiTableModel)tabla.getModel();
        model.getDatos().clear();
        model.getDatos().addAll(lista);
        model.fireTableDataChanged();
    }

    private void setWithInternationalization() {
        if (cliente) {
            this.setTitle(messageSource.getMessage("menu_Client", null, applicationLocale.getLocale()));
        } else {
            this.setTitle(messageSource.getMessage("menu_User", null, applicationLocale.getLocale()));
        }
        lblNIF.setText(messageSource.getMessage("ID", null, applicationLocale.getLocale()));
        lblNombre.setText(messageSource.getMessage("NAME", null, applicationLocale.getLocale()));
        btnBuscar.setText(messageSource.getMessage("button_Search", null, applicationLocale.getLocale()));
    }

    private MiTableModel getModel() {
        return context.getBean("tableModelNifNombre", MiTableModel.class);
    }
    
    private void porNombreAction(KeyEvent evt) {
        //
        if (isChar) {
            str = txtNombre.getText() + evt.getKeyChar();
        } else {
            str = txtNombre.getText();
        }
        MiTableModel model = (MiTableModel) tabla.getModel();
        model.getDatos().clear();
        if (str.isEmpty()) {
            model.getDatos().addAll(lista);
        } else {
            for (Persona p : lista) {
                if (p.getNombre().toUpperCase().contains(str.toUpperCase())) {
                    model.getDatos().add(p);
                }
            }
        }
        model.fireTableDataChanged();

    }
    
    private void porNIF(){
        MiTableModel model = (MiTableModel) tabla.getModel();
        model.getDatos().clear();
        if (txtNIF.getText().isEmpty()) {
            model.getDatos().addAll(lista);
        } else {
            for (Persona p : lista) {
                if (p.getNif().toUpperCase().contains(txtNIF.getText().toUpperCase())) {
                    model.getDatos().add(p);
                }
            }
        }
        model.fireTableDataChanged();
    }
    private void clear(){
        txtNIF.setText("");
        txtNombre.setText("");
        str = "";
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
        panelControles = new javax.swing.JPanel();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblNIF = new javax.swing.JLabel();
        txtNIF = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JToggleButton();
        scrollTabla = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(this.getClass().getResource("/images/icon.png")).getImage());

        panelControles.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblNombre.setText("NOMBRE");

        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        lblNIF.setText("N.I.F");

        txtNIF.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtNIF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNIFKeyPressed(evt);
            }
        });

        btnBuscar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelControlesLayout = new javax.swing.GroupLayout(panelControles);
        panelControles.setLayout(panelControlesLayout);
        panelControlesLayout.setHorizontalGroup(
            panelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelControlesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(panelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelControlesLayout.createSequentialGroup()
                        .addComponent(txtNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblNIF))
                .addGap(14, 14, 14))
        );
        panelControlesLayout.setVerticalGroup(
            panelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelControlesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(lblNIF))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNIF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabla.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tabla.setModel(getModel());
        tabla.setRowHeight(40);
        tabla.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(10);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(140);
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
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelControles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollTabla))
                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelControles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
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

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        porNombreAction(evt);
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyPressed
       if (evt.getKeyCode() == 8 || evt.getKeyCode() == 10) {
            isChar = false;
        } else {
            isChar = true;
        }
    }//GEN-LAST:event_txtNombreKeyPressed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        porNIF();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtNIFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNIFKeyPressed
        if(evt.getKeyCode() == 10){
            porNIF();
        }
    }//GEN-LAST:event_txtNIFKeyPressed

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked
        Persona p;
        if (evt.getClickCount() == 2) {
            MiTableModel model = (MiTableModel) tabla.getModel();
            p = model.getDatos().get(tabla.getSelectedRow());
            if (cliente) {
                Session session = context.getBean(Session.class);
                session.setCliente(p);
                clear();
                dispose();
            } else {
                Session session = context.getBean(Session.class);
                session.setUsuario(p);
                clear();
                dispose();
            }
            tpv.refreshSession();
        }
    }//GEN-LAST:event_tablaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnBuscar;
    private javax.swing.JLabel lblNIF;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel panelControles;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField txtNIF;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables

}
