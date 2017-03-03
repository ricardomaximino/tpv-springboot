package com.brasajava.view.persona;

import com.brasajava.util.interfaces.MiTableModel;
import com.brasajava.model.Cliente;
import com.brasajava.model.Persona;
import com.brasajava.model.Usuario;
import com.brasajava.service.ServicioCliente;
import com.brasajava.service.ServicioUsuario;
import com.brasajava.util.ApplicationLocale;
import com.brasajava.util.PrototypeContext;
import com.brasajava.util.interfaces.Internationalizable;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

/**
 * Esta clase es una tabla para persona.
 *
 * @author Ricardo Maximino
 * <p>
 * Este clase extende JInternalFrame y dentro de este JInternalFrame hay
 * solamente un componente visual(JTable).</p>
 */
public class ListaPersona extends javax.swing.JInternalFrame implements Internationalizable {

    private final ApplicationContext context;
    private final MessageSource messageSource;
    private final ApplicationLocale applicationLocale;
    private final ShowPersonaCommand showPersonaCommand;

    /**
     * Este constructor ademas que iniciar lo componentes configura el model de
     * la JTable.
     *
     * @param context del tipo org.springframework.context.ApplicationContext.
     */
    public ListaPersona(ApplicationContext context) {
        this.context = context;
        this.messageSource = context.getBean(MessageSource.class);
        this.applicationLocale = context.getBean(ApplicationLocale.class);
        this.showPersonaCommand = context.getBean(ShowPersonaCommand.class);
        initComponents();
    }

    /**
     * Este metodo retorna el valor de la variable global tabla.
     *
     * @return del tipo javax.swing.JTable.
     */
    public JTable getTabla() {
        return tabla;
    }

    /**
     * Este metodo configura la variable global tabla.
     *
     * @param tabla del tipo javax.swing.JTable.
     */
    public void setTabla(JTable tabla) {
        this.tabla = tabla;
    }

    /**
     * Este metodo implementa la interfaz
     * com.brasajava.util.interfaces.Internationalizable.
     */
    @Override
    public void refreshLanguage() {
        this.setTitle(messageSource.getMessage(this.getName(), null, applicationLocale.getLocale()));

        MiTableModel model = context.getBean("tableModelTodos", MiTableModel.class);
        TableModel oldModel = tabla.getModel();
        if (oldModel instanceof MiTableModel) {
            model.setDatos(((MiTableModel) oldModel).getDatos());
        }
        tabla.setModel(model);
    }

    /**
     * Este metodo Override el metodo dispose() para eliminar todas las
     * referencias de la instancia in question armacenadas en una instancia de
     * la clase com.brasajava.util.PrototypeContext.
     */
    @Override
    public void dispose() {
        context.getBean(PrototypeContext.class).remove(this);
        super.dispose();
    }

    /*
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(800, 600));
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setAutoscrolls(true);

        tabla.setAutoCreateRowSorter(true);
        tabla.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabla.setToolTipText("Hga doble clic para editar");
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked

    }//GEN-LAST:event_tablaMouseClicked

    private void tablaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMousePressed
        if (evt.getClickCount() == 2) {
            int row = tabla.getSelectedRow();
            String nif = tabla.getValueAt(row, 0).toString();
            Object obj = ((MiTableModel) tabla.getModel()).getDatos().get(0);
            if (obj instanceof Persona) {
                Persona persona = (Persona) obj;
                //Cliente
                if (persona.getClass().equals(Cliente.class)) {
                    Cliente cliente = context.getBean(ServicioCliente.class).findByNif(nif);
                    if(cliente != null){
                        showPersonaCommand.show(cliente,cliente.getClass(),"framePersona_UpdateClient");
                    }else{
                        JOptionPane.showMessageDialog(this,messageSource.getMessage("message_CLIENT_TABLE_IS_NOT_UPDATE",null,applicationLocale.getLocale()));
                    }
                } 
                //Usuario
                else if (persona.getClass().equals(Usuario.class)) {
                    Usuario usuario = context.getBean(ServicioUsuario.class).findByNif(nif);
                    if(usuario != null){
                        showPersonaCommand.show(usuario,usuario.getClass(),"framePersona_UpdateUser");
                    }else{
                        JOptionPane.showMessageDialog(this,messageSource.getMessage("message_USER_TABLE_IS_NOT_UPDATE",null,applicationLocale.getLocale()));
                    }
                }
            }
        }
    }//GEN-LAST:event_tablaMousePressed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_formFocusGained

    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden

    }//GEN-LAST:event_formComponentHidden

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing

    }//GEN-LAST:event_formInternalFrameClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables

}
