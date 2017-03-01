package com.brasajava.view.busqueda;

import com.brasajava.util.Item;
import com.brasajava.util.interfaces.Command;
import com.brasajava.util.ApplicationLocale;
import com.brasajava.util.interfaces.GroupAndCommandActionName;
import com.brasajava.util.interfaces.Internationalizable;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

/**
 * Este extende JDialog y es la resposable de hacer las búsquedas seleccionadas
 * por el usuario.
 *
 * @author Ricardo Maximino
 */
public class FrameBusqueda extends javax.swing.JDialog implements Internationalizable, GroupAndCommandActionName {

    private JLabel lblBuscarPor;
    private JTextField txtBuscarPor;
    private JButton btnBuscarPor;
    private JComboBox<Item> cmbBuscarPor;
    private JComboBox<Item> cmbMes;

    private MessageSource messageSource;
    private ApplicationLocale applicationLocale;
    private final ApplicationContext context;

    private String group;
    private String commandActionName;
    private String accion;
    private String text;
    private int mes;

    /**
     * Estes campo esta pensado para patronizar el valor de la variable global
     * group.
     */
    public static final String GRUPO_CLIENTE = "CLIENTE";
    /**
     * Estes campo esta pensado para patronizar el valor de la variable global
     * group.
     */
    public static final String GRUPO_USUARIO = "USUARIO";

    public static final String ACCION_BUSCAR_POR_NIF = "NIF";
    public static final String ACCION_BUSCAR_POR_NOMBRE = "NOMBRE";
    public static final String ACCION_BUSCAR_POR_PRIMER_APELLIDO = "PRIMER_APELLIDO";
    public static final String ACCION_BUSCAR_POR_SEGUNDO_APELLIDO = "SEGUNDO_APELLIDO";
    public static final String ACCION_BUSCAR_POR_ALTA = "ALTA";
    public static final String ACCION_BUSCAR_POR_BAJA = "BAJA";
    public static final String ACCION_BUSCAR_POR_CUMPLEANEROS_DEL_MES = "CUMPLEANEROS_DEL_MES";

    /**
     * Este metodo inicializa las tres variables globales más importantes para
     * el funcionamiento de esta classe.
     *
     * @param messageSource del tipo org.springframework.context.MessageSource.
     * @param applicationLocale del tipo com.brasajava.util.ApplicationLocale.
     * @param context org.springframework.context.ApplicationContext.<br>
     * En este constructor tambien es donde se inicializan los components
     * graficos.
     */
    public FrameBusqueda(MessageSource messageSource, ApplicationLocale applicationLocale, ApplicationContext context) {
        super();
        super.setModal(true);
        this.messageSource = messageSource;
        this.applicationLocale = applicationLocale;
        this.context = context;
        init();
        lblBuscarPor.setText(messageSource.getMessage("label_SearchFor", null, applicationLocale.getLocale()));
        btnBuscarPor.setText(messageSource.getMessage("button_Search", null, applicationLocale.getLocale()));
        txtBuscarPor.setText("");
        setCmbModel(getModelItems());
        cmbMes.setModel(getMesModel());
        accion = ((Item) cmbBuscarPor.getSelectedItem()).getCommandName();
        mes = (int) ((Item) cmbMes.getSelectedItem()).getValue();
    }

    /**
     * Retorna el valor de la variable global messageSource.
     *
     * @return del tipo org.springframework.context.MessageSource.
     */
    public MessageSource getMessageSource() {
        return messageSource;
    }

    /**
     * Configura el valor de la variable global messageSource.
     *
     * @param messageSource del tipo org.springframework.context.MessageSource.
     */
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Retorna el valor de la variable global applicationLocale.
     *
     * @return del tipo com.brasajava.util.ApplicationLocale.
     */
    public ApplicationLocale getApplicationLocale() {
        return applicationLocale;
    }

    /**
     * Configura el valor de la variable global applicationLocale.
     *
     * @param applicationLocale del tipo com.brasajava.util.ApplicationLocale.
     */
    public void setApplicationLocale(ApplicationLocale applicationLocale) {
        this.applicationLocale = applicationLocale;
    }

    /**
     * Retorna el valor de la variable global accion.
     *
     * @return del tipo java.lang.String.
     */
    public String getAccion() {
        return accion;
    }

    /**
     * Configura el valor de la variable global accion.
     *
     * @param accion del tipo java.lang.String.
     */
    public void setAccion(String accion) {
        this.accion = accion;
    }

    /**
     * Retorna el valor de la variable global text.
     *
     * @return del tipo java.lang.String.
     */
    public String getText() {
        return text;
    }

    /**
     * Retorna el valor de la variable global mes.
     *
     * @return del tipo int.
     */
    public int getMes() {
        return mes;
    }

    /**
     * Configura el valor de la variable global group.
     *
     * @param group del tipo java.lang.String.
     */
    @Override
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * Retorna el valor de la variable global group.
     *
     * @return del tipo java.lang.String.
     */
    @Override
    public String getGroup() {
        return group;
    }

    /**
     * Configura el valor de la variable global commandActionName.
     *
     * @param commandActionName del tipo java.lang.String.
     */
    @Override
    public void setCommandActionName(String commandActionName) {
        this.commandActionName = commandActionName;
    }

    /**
     * Retorna el valor de la variable global commandActionName.
     *
     * @return del tipo java.lang.String.
     */
    @Override
    public String getCommandActionName() {
        return commandActionName;
    }

    /**
     * Este metodo es la implementacion del la interfaz
     * com.brasajava.view.menu.Internationalizable, este metodo es responsable
     * de atualizar la lengua en todos los componentes visuales de esta classe.
     */
    @Override
    public void refreshLanguage() {
        if (this.getName() != null && this.getName().equals("")) {
            this.setTitle(messageSource.getMessage(this.getName(), null, applicationLocale.getDefaultLocale()));
        }
        lblBuscarPor.setText(messageSource.getMessage("label_SearchFor", null, applicationLocale.getLocale()));
        btnBuscarPor.setText(messageSource.getMessage("button_Search", null, applicationLocale.getLocale()));
        for (int i = 0; i < cmbBuscarPor.getModel().getSize(); i++) {
            Item it = cmbBuscarPor.getModel().getElementAt(i);
            it.setLabel(messageSource.getMessage(it.getName(), null, applicationLocale.getLocale()));
        }
    }

    private void setCmbModel(Item[] items) {
        DefaultComboBoxModel<Item> model = new DefaultComboBoxModel<>(items);
        if (model.getSize() > 0) {
            model.setSelectedItem(model.getElementAt(0));
        }
        cmbBuscarPor.setModel(model);
    }

    private Item[] getModelItems() {
        List<Item> list = new ArrayList<>();
        list.add(createItem(ACCION_BUSCAR_POR_NIF, "ID"));
        list.add(createItem(ACCION_BUSCAR_POR_NOMBRE, "NAME"));
        list.add(createItem(ACCION_BUSCAR_POR_PRIMER_APELLIDO, "FIRST_LASTNAME"));
        list.add(createItem(ACCION_BUSCAR_POR_SEGUNDO_APELLIDO, "SECOND_LASTNAME"));
        list.add(createItem(ACCION_BUSCAR_POR_ALTA, "JOINED"));
        list.add(createItem(ACCION_BUSCAR_POR_BAJA, "CUTED_OFF"));
        list.add(createItem(ACCION_BUSCAR_POR_CUMPLEANEROS_DEL_MES, "BIRTHDAY_THIS_MONTH"));
        return list.toArray(new Item[list.size()]);
    }

    private Item createItem(String commandName, String messageSourceKeyName) {
        Item item = new Item(commandName, messageSourceKeyName);
        item.setLabel(messageSource.getMessage(item.getName(), null, applicationLocale.getLocale()));
        return item;
    }

    //provisorio
    private void button(ActionEvent e) {
        Command command = context.getBean("buscaPersona", Command.class);
        text = txtBuscarPor.getText();

        if (command != null) {
            this.setVisible(false);
            command.execute(new Object[]{this});
        } else {
            JOptionPane.showMessageDialog(this, messageSource.getMessage("message_DISABLED_SERVICE", null, applicationLocale.getLocale()));
        }
        txtBuscarPor.setText("");
    }

    private void comboBox(ActionEvent e) {
        Item item = (Item) cmbBuscarPor.getSelectedItem();
        accion = item.getCommandName();
        if (item.getCommandName().equals(ACCION_BUSCAR_POR_ALTA) || item.getCommandName().equals(ACCION_BUSCAR_POR_BAJA)) {
            txtBuscarPor.setEnabled(false);
            txtBuscarPor.setText("");
        } else if (item.getCommandName().equals(ACCION_BUSCAR_POR_CUMPLEANEROS_DEL_MES)) {
            txtBuscarPor.setVisible(false);
            txtBuscarPor.setText("");
            cmbMes.setVisible(true);
        } else {
            txtBuscarPor.setVisible(true);
            txtBuscarPor.setEnabled(true);
            cmbMes.setVisible(false);
        }
    }

    private void comboBoxMes(ActionEvent e) {
        Item item = (Item) cmbMes.getSelectedItem();
        mes = (int) item.getValue();
    }

    private void init() {
        lblBuscarPor = new JLabel();
        txtBuscarPor = new JTextField();
        btnBuscarPor = new JButton();
        cmbBuscarPor = new JComboBox<>();
        cmbMes = new JComboBox<>();

        cmbMes.setVisible(false);

        this.setPreferredSize(new Dimension(650, 200));
        this.setLocation(new Point(600, 250));
        this.setResizable(false);
        this.getContentPane().setLayout(null);

        lblBuscarPor.setBounds(20, 30, 200, 50);
        cmbBuscarPor.setBounds(230, 30, 400, 50);
        txtBuscarPor.setBounds(20, 90, 400, 50);
        cmbMes.setBounds(20, 90, 400, 50);
        btnBuscarPor.setBounds(430, 90, 200, 50);

        Font font = new Font("Arial", Font.BOLD, 23);

        cmbBuscarPor.setFont(font);
        cmbMes.setFont(font);
        lblBuscarPor.setFont(font);
        txtBuscarPor.setFont(font);
        btnBuscarPor.setFont(font);

        this.getContentPane().add(lblBuscarPor);
        this.getContentPane().add(cmbBuscarPor);
        this.getContentPane().add(txtBuscarPor);
        this.getContentPane().add(cmbMes);
        this.getContentPane().add(btnBuscarPor);

        cmbBuscarPor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ev) {
                if (ev.getKeyCode() == 10) {
                    txtBuscarPor.grabFocus();
                }
            }
        });

        txtBuscarPor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ev) {
                if (ev.getKeyCode() == 10) {
                    button(null);
                }
            }
        });
        btnBuscarPor.addActionListener(this::button);
        cmbBuscarPor.addActionListener(this::comboBox);
        cmbMes.addActionListener(this::comboBoxMes);

        pack();
    }

    private DefaultComboBoxModel<Item> getMesModel() {
        DefaultComboBoxModel<Item> model = new DefaultComboBoxModel<>();
        //Enero
        Item enero = new Item(null, "month_JANUARY");
        enero.setLabel(messageSource.getMessage(enero.getName(), null, applicationLocale.getLocale()));
        enero.setValue(1);
        model.addElement(enero);
        //Febrero
        Item febrero = new Item(null, "month_FEBRUARY");
        febrero.setLabel(messageSource.getMessage(febrero.getName(), null, applicationLocale.getLocale()));
        febrero.setValue(2);
        model.addElement(febrero);
        //Marzo
        Item marzo = new Item(null, "month_MARCH");
        marzo.setLabel(messageSource.getMessage(marzo.getName(), null, applicationLocale.getLocale()));
        marzo.setValue(3);
        model.addElement(marzo);
        //Abril
        Item abril = new Item(null, "month_APRIL");
        abril.setLabel(messageSource.getMessage(abril.getName(), null, applicationLocale.getLocale()));
        abril.setValue(4);
        model.addElement(abril);
        //Mayo
        Item mayo = new Item(null, "month_MAY");
        mayo.setLabel(messageSource.getMessage(mayo.getName(), null, applicationLocale.getLocale()));
        mayo.setValue(5);
        model.addElement(mayo);
        //Junio
        Item junio = new Item(null, "month_JUNE");
        junio.setLabel(messageSource.getMessage(junio.getName(), null, applicationLocale.getLocale()));
        junio.setValue(6);
        model.addElement(junio);
        //Julio
        Item julio = new Item(null, "month_JULY");
        julio.setLabel(messageSource.getMessage(julio.getName(), null, applicationLocale.getLocale()));
        julio.setValue(7);
        model.addElement(julio);
        //Agosto
        Item agosto = new Item(null, "month_AUGUST");
        agosto.setLabel(messageSource.getMessage(agosto.getName(), null, applicationLocale.getLocale()));
        agosto.setValue(8);
        model.addElement(agosto);
        //Septiembre
        Item septiembre = new Item(null, "month_SEPTEMBER");
        septiembre.setLabel(messageSource.getMessage(septiembre.getName(), null, applicationLocale.getLocale()));
        septiembre.setValue(9);
        model.addElement(septiembre);
        //Octubre
        Item octubre = new Item(null, "month_OCTOBER");
        octubre.setLabel(messageSource.getMessage(octubre.getName(), null, applicationLocale.getLocale()));
        octubre.setValue(10);
        model.addElement(octubre);
        //Noviembre
        Item noviembre = new Item(null, "month_NOVEMBER");
        noviembre.setLabel(messageSource.getMessage(noviembre.getName(), null, applicationLocale.getLocale()));
        noviembre.setValue(11);
        model.addElement(noviembre);
        //Diciembre
        Item diciembre = new Item(null, "month_DECEMBER");
        diciembre.setLabel(messageSource.getMessage(diciembre.getName(), null, applicationLocale.getLocale()));
        diciembre.setValue(12);
        model.addElement(diciembre);
        return model;
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
