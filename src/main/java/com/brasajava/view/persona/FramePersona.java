package com.brasajava.view.persona;

import com.brasajava.model.Contacto;
import com.brasajava.model.Persona;
import com.brasajava.service.ServicioCliente;
import com.brasajava.service.ServicioPersona;
import com.brasajava.service.ServicioUsuario;
import com.brasajava.util.ApplicationLocale;
import com.brasajava.util.Item;
import com.brasajava.util.PrototypeContext;
import com.brasajava.util.interfaces.Internationalizable;
import com.brasajava.view.persona.command.SavePersonaCommand;
import java.awt.Component;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

/**
 * Esta clase es el extende javax.swing.JIntenalFrame, y es donde se exibirá
 * atualizará y añadirá personas a la base de datos.
 *
 * @author Ricardo Maximino
 */
public class FramePersona extends javax.swing.JInternalFrame implements Internationalizable {

    private ApplicationContext context;
    private MessageSource messageSource;
    private ApplicationLocale applicationLocale;
    private Persona persona;
    private String state;
    private String group;
    private final List<String> errorList;

    public static final String GRUPO_CLIENTE = "CLIENTE";
    public static final String GRUPO_USUARIO = "USUARIO";

    public static final String STATE_ANADIR = "ANADIR";
    public static final String STATE_ATUALIZAR = "ATUALIZAR";

    /**
     * Estes campo esta pensado para patronizar el valor de la descripción del
     * contacto ya que la clase com.brasajava.model.Persona tiene una lista de
     * contactos pero en este formulario solamente será posible añadir e
     * rescatar un contacto que será el telefono.
     */
    public static final String TELEFONO = "TELEFONO";

    /**
     * Este es el único constructor para crear una nova instancia desta clase,
     * este constructor lleva tres argumentos imprecindibles para el perfecto
     * funcionamento.
     *
     * @param messageSource del tipo org.springframework.context.MessageSource.
     * @param applicationLocale del tipo com.brasajava.util.ApplicationLocale.
     * @param context del tipo org.springframework.context.ApplicationContext.
     */
    public FramePersona(MessageSource messageSource, ApplicationLocale applicationLocale, ApplicationContext context) {
        this.context = context;
        this.messageSource = messageSource;
        this.applicationLocale = applicationLocale;
        initComponents();
        errorList = new ArrayList<>();
    }

    /**
     * Configura el valor de la variable global prototypeContext.
     *
     * @param context del tipo org.springframework.context.ApplicationContext.
     */
    public void setContext(ApplicationContext context) {
        this.context = context;
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
     * Retorna el valor de la variable global persona.
     *
     * @return del tipo com.brasajava.model.Persona.
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * Configura el valor de la variable global persona y los componentes
     * visuales correspondientes como por ejemplo
     * txtNombre.setText(persona.getNombre());.
     *
     * @param persona del tipo com.brasajava.model.Persona
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
        //Datos Personales
        txtNombre.setText(persona.getNombre());
        txtPrimerApellido.setText(persona.getPrimerApellido());
        txtSegundoApellido.setText(persona.getSegundoApellido());
        txtNIF.setText(persona.getNif());
        setFecha(persona.getFechaNacimiento());
        ckbActivo.setSelected(persona.isActivo());
        //Contacto
        txtTelefono.setText(getTelefono());
        //Acceso
        txtEmail.setText(persona.getNombreDeUsuario());
        txtContrasena.setText(persona.getContraseña());
        txtRepiteContrasena.setText(persona.getContraseña());

        if (persona.getId() > 0) {
            cmbControlDeAcceso.getModel().setSelectedItem(persona.getControleDeAcceso());
        }

        //Direccion
        txtPais.setText(persona.getDirecion().getPais());
        txtProvincia.setText(persona.getDirecion().getProvincia());
        txtLocalidade.setText(persona.getDirecion().getLocalidade());
        txtDireccion.setText(persona.getDirecion().getDireccion());
        txtNumero.setText(persona.getDirecion().getNumero());
        txtCP.setText(persona.getDirecion().getCp());
        txtNota.setText(persona.getDirecion().getNota());
    }

    /**
     * Retorna el valor de la variable global state.
     *
     * @return del tipo java.lang.String.
     */
    public String getState() {
        return state;
    }

    /**
     * Configura el valor de la variable global state.
     *
     * @param state del tipo java.lang.String.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Retorna el valor de la variable global group.
     *
     * @return del tipo java.lang.String.
     */
    public String getGroup() {
        return group;
    }

    /**
     * Configura el valor de la variable global group.
     *
     * @param group del tipo java.lang.String.
     */
    public void setGroup(String group) {
        this.group = group;
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

    /**
     * Este metodo implementa la interfaz
     * com.brasajava.util.interfaces.Internationalizable.
     */
    @Override
    public void refreshLanguage() {

        this.setTitle(messageSource.getMessage(this.getName(), null, applicationLocale.getLocale()));

        for (int i = 0; i < cmbMes.getModel().getSize(); i++) {
            Item item = cmbMes.getModel().getElementAt(i);
            item.setLabel(messageSource.getMessage(item.getName(), null, applicationLocale.getLocale()));
        }

        for (Component componentP : this.getContentPane().getComponents()) {
            for (Component component : ((JPanel) componentP).getComponents()) {
                if (component instanceof JLabel) {
                    JLabel label = (JLabel) component;
                    label.setText(messageSource.getMessage(label.getName(), null, applicationLocale.getLocale()));
                } else if (component instanceof JPanel) {
                    JPanel panel = (JPanel) component;
                    panel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), messageSource.getMessage(panel.getName(), null, applicationLocale.getLocale())));
                    panel.repaint();
                } else if (component instanceof JCheckBox) {
                    JCheckBox checkBox = (JCheckBox) component;
                    checkBox.setText(messageSource.getMessage(checkBox.getName(), null, applicationLocale.getLocale()));
                } else if (component instanceof JButton) {
                    JButton button = (JButton) component;
                    button.setText(messageSource.getMessage(button.getName(), null, applicationLocale.getLocale()));
                }
            }
        }
    }

    /**
     * Este metodo es para transferir los valores de los componentes visuales
     * para la instancia de la clase com.brasajava.model.Persona armacenada el
     * la variable global persona.
     *
     * @throws DateTimeException java.time.DateTimeException se la fecha elejida
     * no exista.
     */
    public void merge() {
        //implementar la validación aqui en el merge

        //Datos Personales
        persona.setNombre(txtNombre.getText());
        persona.setPrimerApellido(txtPrimerApellido.getText());
        persona.setSegundoApellido(txtSegundoApellido.getText());
        persona.setNif(txtNIF.getText());
        persona.setFechaNacimiento(converteFecha());

        persona.setActivo(ckbActivo.isSelected());
        //Contacto
        setTelefono(txtTelefono.getText());
        //Acceso
        persona.setNombreDeUsuario(txtEmail.getText());
        persona.setContraseña(new String(txtContrasena.getPassword()));
        persona.setControleDeAcceso((String) cmbControlDeAcceso.getSelectedItem());

        //Direccion
        persona.getDirecion().setPais(txtPais.getText());
        persona.getDirecion().setProvincia(txtProvincia.getText());
        persona.getDirecion().setLocalidade(txtLocalidade.getText());
        persona.getDirecion().setDireccion(txtDireccion.getText());
        persona.getDirecion().setNumero(txtNumero.getText());
        persona.getDirecion().setCp(txtCP.getText());
        persona.getDirecion().setNota(txtNota.getText());
    }

    /**
     * Inhabilita la edición del apartado de contraseña y controle de acceso.
     */
    public void setFrameToUpdateMode() {
        txtContrasena.setEnabled(false);
        txtRepiteContrasena.setEnabled(false);
        cmbControlDeAcceso.setEnabled(false);
        txtNIF.setEditable(false);
    }

    private boolean validateData() {
        boolean result = true;
        converteFecha();
        checkPassword();
        if (txtNIF.isEditable()) {
            checkNIF();
        }
        if (!errorList.isEmpty()) {
            result = false;
            JOptionPane.showMessageDialog(this, errorListToString());
        }
        return result;
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

    private DefaultComboBoxModel<Integer> getDiaModel() {
        DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();
        for (int i = 1; i < 32; i++) {
            model.addElement(i);
        }
        return model;
    }

    private DefaultComboBoxModel<Integer> getAnoModel() {
        DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();
        for (int i = LocalDate.now().getYear(); i > (LocalDate.now().getYear() - 65); i--) {
            model.addElement(i);
        }
        return model;
    }

    private String errorListToString() {
        StringBuilder sb = new StringBuilder();
        for (String s : errorList) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }

    private String msgErrorDeFecha() {
        StringBuilder sb = new StringBuilder();
        sb.append(cmbDia.getSelectedItem());
        sb.append(" ");
        sb.append(messageSource.getMessage("message_of", null, applicationLocale.getLocale()));
        sb.append(" ");
        sb.append(cmbMes.getSelectedItem());
        sb.append(" ");
        sb.append(messageSource.getMessage("message_of", null, applicationLocale.getLocale()));
        sb.append(" ");
        sb.append(cmbAno.getSelectedItem());
        sb.append(", ");
        sb.append(messageSource.getMessage("message_is_not_a_valid_date", null, applicationLocale.getLocale()));
        sb.append(".");
        return sb.toString();
    }

    private LocalDate converteFecha() {
        LocalDate localDate = null;
        try {
            localDate = LocalDate.of((int) cmbAno.getSelectedItem(), (int) ((Item) cmbMes.getSelectedItem()).getValue(), (int) cmbDia.getSelectedItem());
        } catch (Exception ex) {
            errorList.add(msgErrorDeFecha());
        }
        return localDate;

    }

    private void checkPassword() {
        if (!new String(txtContrasena.getPassword()).equals(new String(txtRepiteContrasena.getPassword()))) {
            errorList.add(messageSource.getMessage("message_The_fields_password_repitePassword_must_be_equals", null, applicationLocale.getLocale()));
        }
    }

    private void checkNIF() {
        if (txtNIF.getText().isEmpty()) {
            errorList.add(messageSource.getMessage("message_ID_CAN_NOT_BE_EMPTY", null, applicationLocale.getLocale()));
        } else {
            ServicioPersona<?> serv = null;
            if (group.equals(GRUPO_CLIENTE)) {
                serv = context.getBean(ServicioCliente.class);
            } else if (group.equals(GRUPO_USUARIO)) {
                serv = context.getBean(ServicioUsuario.class);
            }
            Persona p = (Persona) serv.findByNif(txtNIF.getText());
            if (p != null) {
                errorList.add(messageSource.getMessage("message_THIS_ID_ALREADY_EXIST", null, applicationLocale.getLocale()));
            }
        }
    }

    private void setFecha(LocalDate fecha) {
        cmbDia.setSelectedIndex(fecha.getDayOfMonth() - 1);
        cmbMes.setSelectedIndex(fecha.getMonthValue() - 1);
        cmbAno.setSelectedItem(fecha.getYear());
    }

    private String getTelefono() {
        if (persona.getContactos().size() > 0) {
            for (Contacto c : persona.getContactos()) {
                if (c.getDescripcion().equals(TELEFONO)) {
                    return c.getContacto();
                }
            }
        }
        return "";
    }

    private void setTelefono(String telefono) {
        Contacto contacto = null;
        if (persona.getContactos().size() > 0) {
            for (Contacto c : persona.getContactos()) {
                if (c.getDescripcion().equals(TELEFONO)) {
                    contacto = c;
                }
            }
        }
        if (contacto != null) {
            contacto.setContacto(telefono);
        } else {
            contacto = new Contacto(TELEFONO, telefono);
            persona.getContactos().add(contacto);
        }
    }

    /*
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelDatosPersonale = new javax.swing.JPanel();
        txtNombre = new javax.swing.JTextField();
        txtPrimerApellido = new javax.swing.JTextField();
        txtSegundoApellido = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        lblPrimerApellido = new javax.swing.JLabel();
        lblSegundoApellido = new javax.swing.JLabel();
        txtNIF = new javax.swing.JTextField();
        cmbDia = new javax.swing.JComboBox<>();
        cmbMes = new javax.swing.JComboBox<>();
        cmbAno = new javax.swing.JComboBox<>();
        ckbActivo = new javax.swing.JCheckBox();
        lblNIF = new javax.swing.JLabel();
        lblFechaDeNacimiento = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        lblTelefono = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        panelDatosDeAcceso = new javax.swing.JPanel();
        lblContrasena = new javax.swing.JLabel();
        lblRepiteContrasena = new javax.swing.JLabel();
        lblControlDeAcceso = new javax.swing.JLabel();
        cmbControlDeAcceso = new javax.swing.JComboBox<>();
        txtContrasena = new javax.swing.JPasswordField();
        txtRepiteContrasena = new javax.swing.JPasswordField();
        panelDireccion = new javax.swing.JPanel();
        txtPais = new javax.swing.JTextField();
        txtProvincia = new javax.swing.JTextField();
        txtLocalidade = new javax.swing.JTextField();
        lblPais = new javax.swing.JLabel();
        lblProvincia = new javax.swing.JLabel();
        lblLocalidade = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        txtNumero = new javax.swing.JTextField();
        txtCP = new javax.swing.JTextField();
        txtNota = new javax.swing.JTextField();
        lblDireccion = new javax.swing.JLabel();
        lblNumero = new javax.swing.JLabel();
        lblCP = new javax.swing.JLabel();
        lblNota = new javax.swing.JLabel();
        panelDeBotones = new javax.swing.JPanel();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnCambiarContrasena = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setName(""); // NOI18N

        panelDatosPersonale.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), messageSource.getMessage("border_PersonalData", null, applicationLocale.getLocale())));
        panelDatosPersonale.setName("border_PersonalData"); // NOI18N

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreKeyPressed(evt);
            }
        });

        txtPrimerApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrimerApellidoActionPerformed(evt);
            }
        });
        txtPrimerApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPrimerApellidoKeyPressed(evt);
            }
        });

        txtSegundoApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSegundoApellidoKeyPressed(evt);
            }
        });

        lblNombre.setText(messageSource.getMessage("label_Name",null,applicationLocale.getLocale()));
        lblNombre.setName("label_Name"); // NOI18N

        lblPrimerApellido.setText(messageSource.getMessage("label_FirstLastname",null,applicationLocale.getLocale()));
        lblPrimerApellido.setName("label_FirstLastname"); // NOI18N

        lblSegundoApellido.setText(messageSource.getMessage("label_SecondLastname",null,applicationLocale.getLocale()));
        lblSegundoApellido.setName("label_SecondLastname"); // NOI18N

        txtNIF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNIFKeyPressed(evt);
            }
        });

        cmbDia.setModel(getDiaModel());
        cmbDia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDiaKeyPressed(evt);
            }
        });

        cmbMes.setModel(getMesModel());
        cmbMes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMesKeyPressed(evt);
            }
        });

        cmbAno.setModel(getAnoModel());
        cmbAno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbAnoKeyPressed(evt);
            }
        });

        ckbActivo.setText(messageSource.getMessage("check_Active",null,applicationLocale.getLocale())
        );
        ckbActivo.setName("check_Active"); // NOI18N
        ckbActivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ckbActivoKeyPressed(evt);
            }
        });

        lblNIF.setText(messageSource.getMessage("label_Id",null,applicationLocale.getLocale()));
        lblNIF.setName("label_Id"); // NOI18N

        lblFechaDeNacimiento.setText(messageSource.getMessage("label_Birthday",null,applicationLocale.getLocale()));
        lblFechaDeNacimiento.setName("label_Birthday"); // NOI18N

        txtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoActionPerformed(evt);
            }
        });
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyPressed(evt);
            }
        });

        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEmailKeyPressed(evt);
            }
        });

        lblTelefono.setText(messageSource.getMessage("label_Phone", null, applicationLocale.getLocale()));
        lblTelefono.setName("label_Phone"); // NOI18N

        lblEmail.setText(messageSource.getMessage("label_Email", null, applicationLocale.getLocale()));
        lblEmail.setName("label_Email"); // NOI18N

        javax.swing.GroupLayout panelDatosPersonaleLayout = new javax.swing.GroupLayout(panelDatosPersonale);
        panelDatosPersonale.setLayout(panelDatosPersonaleLayout);
        panelDatosPersonaleLayout.setHorizontalGroup(
            panelDatosPersonaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosPersonaleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosPersonaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosPersonaleLayout.createSequentialGroup()
                        .addGroup(panelDatosPersonaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNombre))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelDatosPersonaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPrimerApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPrimerApellido))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelDatosPersonaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSegundoApellido)
                            .addComponent(txtSegundoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelDatosPersonaleLayout.createSequentialGroup()
                        .addGroup(panelDatosPersonaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNIF))
                        .addGap(14, 14, 14)
                        .addGroup(panelDatosPersonaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDatosPersonaleLayout.createSequentialGroup()
                                .addComponent(cmbDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbMes, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbAno, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(ckbActivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblFechaDeNacimiento)))
                    .addGroup(panelDatosPersonaleLayout.createSequentialGroup()
                        .addGroup(panelDatosPersonaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTelefono))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelDatosPersonaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEmail)
                            .addComponent(txtEmail))))
                .addGap(20, 20, 20))
        );
        panelDatosPersonaleLayout.setVerticalGroup(
            panelDatosPersonaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosPersonaleLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panelDatosPersonaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(lblPrimerApellido)
                    .addComponent(lblSegundoApellido))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosPersonaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrimerApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSegundoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(panelDatosPersonaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNIF)
                    .addComponent(lblFechaDeNacimiento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosPersonaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ckbActivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNIF, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(cmbDia)
                    .addComponent(cmbMes)
                    .addComponent(cmbAno))
                .addGap(30, 30, 30)
                .addGroup(panelDatosPersonaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTelefono)
                    .addComponent(lblEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosPersonaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addContainerGap())
        );

        panelDatosDeAcceso.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), messageSource.getMessage("border_AccessData",null, applicationLocale.getLocale())));
        panelDatosDeAcceso.setName("border_AccessData"); // NOI18N

        lblContrasena.setText(messageSource.getMessage("label_Password", null, applicationLocale.getLocale()));
        lblContrasena.setName("label_Password"); // NOI18N

        lblRepiteContrasena.setText(messageSource.getMessage("label_RepeatPassword", null, applicationLocale.getLocale()));
        lblRepiteContrasena.setName("label_RepeatPassword"); // NOI18N

        lblControlDeAcceso.setText(messageSource.getMessage("label_PowerOfAccess", null, applicationLocale.getLocale()));
        lblControlDeAcceso.setName("label_PowerOfAccess"); // NOI18N

        cmbControlDeAcceso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbControlDeAcceso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ADMIN", "USER" }));
        cmbControlDeAcceso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbControlDeAccesoKeyPressed(evt);
            }
        });

        txtContrasena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtContrasenaKeyPressed(evt);
            }
        });

        txtRepiteContrasena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRepiteContrasenaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelDatosDeAccesoLayout = new javax.swing.GroupLayout(panelDatosDeAcceso);
        panelDatosDeAcceso.setLayout(panelDatosDeAccesoLayout);
        panelDatosDeAccesoLayout.setHorizontalGroup(
            panelDatosDeAccesoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosDeAccesoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosDeAccesoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblContrasena)
                    .addComponent(txtContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDatosDeAccesoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtRepiteContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRepiteContrasena))
                .addGap(18, 18, 18)
                .addGroup(panelDatosDeAccesoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbControlDeAcceso, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblControlDeAcceso))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelDatosDeAccesoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtContrasena, txtRepiteContrasena});

        panelDatosDeAccesoLayout.setVerticalGroup(
            panelDatosDeAccesoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosDeAccesoLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(panelDatosDeAccesoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContrasena)
                    .addComponent(lblRepiteContrasena)
                    .addComponent(lblControlDeAcceso))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosDeAccesoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRepiteContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbControlDeAcceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panelDatosDeAccesoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbControlDeAcceso, txtContrasena, txtRepiteContrasena});

        panelDireccion.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), messageSource.getMessage("border_Direction", null, applicationLocale.getLocale())));
        panelDireccion.setName("border_Direction"); // NOI18N

        txtPais.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPaisKeyPressed(evt);
            }
        });

        txtProvincia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtProvinciaKeyPressed(evt);
            }
        });

        txtLocalidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLocalidadeKeyPressed(evt);
            }
        });

        lblPais.setText(messageSource.getMessage("label_Country", null, applicationLocale.getLocale()));
        lblPais.setName("label_Country"); // NOI18N

        lblProvincia.setText(messageSource.getMessage("label_Province", null, applicationLocale.getLocale()));
        lblProvincia.setName("label_Province"); // NOI18N

        lblLocalidade.setText(messageSource.getMessage("label_Town", null, applicationLocale.getLocale()));
        lblLocalidade.setName("label_Town"); // NOI18N

        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDireccionKeyPressed(evt);
            }
        });

        txtNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumeroKeyPressed(evt);
            }
        });

        txtCP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCPKeyPressed(evt);
            }
        });

        txtNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNotaKeyPressed(evt);
            }
        });

        lblDireccion.setText(messageSource.getMessage("label_Direction", null, applicationLocale.getLocale()));
        lblDireccion.setName("label_Direction"); // NOI18N

        lblNumero.setText(messageSource.getMessage("label_Number", null, applicationLocale.getLocale()));
        lblNumero.setName("label_Number"); // NOI18N

        lblCP.setText(messageSource.getMessage("label_PostCode", null, applicationLocale.getLocale()));
        lblCP.setName("label_PostCode"); // NOI18N

        lblNota.setText(messageSource.getMessage("label_Note", null, applicationLocale.getLocale()));
        lblNota.setName("label_Note"); // NOI18N

        javax.swing.GroupLayout panelDireccionLayout = new javax.swing.GroupLayout(panelDireccion);
        panelDireccion.setLayout(panelDireccionLayout);
        panelDireccionLayout.setHorizontalGroup(
            panelDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDireccionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDireccionLayout.createSequentialGroup()
                        .addGroup(panelDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPais, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPais))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelDireccionLayout.createSequentialGroup()
                                .addComponent(lblProvincia)
                                .addGap(234, 234, 234))
                            .addGroup(panelDireccionLayout.createSequentialGroup()
                                .addComponent(txtProvincia)
                                .addGap(10, 10, 10))))
                    .addGroup(panelDireccionLayout.createSequentialGroup()
                        .addGroup(panelDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDireccion))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNumero)
                            .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCP)
                            .addComponent(txtCP, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(panelDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLocalidade)
                    .addComponent(txtLocalidade, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNota, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNota))
                .addGap(28, 28, 28))
        );
        panelDireccionLayout.setVerticalGroup(
            panelDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDireccionLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(panelDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPais)
                    .addComponent(lblProvincia)
                    .addComponent(lblLocalidade))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPais, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(txtProvincia, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(txtLocalidade, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(panelDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDireccion)
                    .addComponent(lblNumero)
                    .addComponent(lblCP)
                    .addComponent(lblNota))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(txtNumero, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(txtCP, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(txtNota, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addContainerGap())
        );

        panelDeBotones.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), messageSource.getMessage("border_ButtonSection",null,applicationLocale.getLocale())));

        btnAceptar.setText(messageSource.getMessage("button_Accept",null,applicationLocale.getLocale()));
        btnAceptar.setName("button_Accept"); // NOI18N
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        btnAceptar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAceptarKeyPressed(evt);
            }
        });

        btnCancelar.setText(messageSource.getMessage("button_Cancel",null,applicationLocale.getLocale()));
        btnCancelar.setName("button_Cancel"); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        btnCancelar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCancelarKeyPressed(evt);
            }
        });

        btnCambiarContrasena.setText(messageSource.getMessage("button_Change_Password",null,applicationLocale.getLocale()));
        btnCambiarContrasena.setName("button_Change_Password"); // NOI18N
        btnCambiarContrasena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarContrasenaActionPerformed(evt);
            }
        });
        btnCambiarContrasena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCambiarContrasenaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelDeBotonesLayout = new javax.swing.GroupLayout(panelDeBotones);
        panelDeBotones.setLayout(panelDeBotonesLayout);
        panelDeBotonesLayout.setHorizontalGroup(
            panelDeBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDeBotonesLayout.createSequentialGroup()
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addComponent(btnCambiarContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelDeBotonesLayout.setVerticalGroup(
            panelDeBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDeBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnCambiarContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelDatosPersonale, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDatosDeAcceso, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDireccion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDeBotones, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(panelDatosPersonale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDatosDeAcceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelDeBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPrimerApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrimerApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrimerApellidoActionPerformed

    private void txtTelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyPressed
        if (evt.getKeyCode() == 10) {
            txtEmail.grabFocus();
        }
    }//GEN-LAST:event_txtTelefonoKeyPressed

    private void txtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        errorList.clear();
        if (validateData()) {
            merge();
            context.getBean(SavePersonaCommand.class).execute(new Object[]{this});
        }

    }//GEN-LAST:event_btnAceptarActionPerformed
    //provisorio
    private void btnCambiarContrasenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarContrasenaActionPerformed
        try {
            String password = persona.getContraseña();//new String(txtContrasena.getPassword());
            String inputPassword = JOptionPane.showInputDialog(this,
                    messageSource.getMessage("message_to_change_password_first_enter_the_password", null, applicationLocale.getLocale()),
                    messageSource.getMessage("update_Password", null, applicationLocale.getLocale()),
                    JOptionPane.INFORMATION_MESSAGE);
            if (inputPassword.equals(password)) {
                txtContrasena.setEnabled(true);
                txtRepiteContrasena.setEnabled(true);
                cmbControlDeAcceso.setEnabled(true);
                txtContrasena.grabFocus();
            } else {
                JOptionPane.showMessageDialog(this,
                        messageSource.getMessage("message_wrong_password", null, applicationLocale.getLocale()));
            }
        } catch (NullPointerException ex) {
        }
    }//GEN-LAST:event_btnCambiarContrasenaActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyPressed
        if (evt.getKeyCode() == 10) {

            txtPrimerApellido.grabFocus();
        }
    }//GEN-LAST:event_txtNombreKeyPressed

    private void txtPrimerApellidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrimerApellidoKeyPressed
        if (evt.getKeyCode() == 10) {
            txtSegundoApellido.grabFocus();
        }
    }//GEN-LAST:event_txtPrimerApellidoKeyPressed

    private void txtSegundoApellidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSegundoApellidoKeyPressed
        if (evt.getKeyCode() == 10) {
            if (txtNIF.isEditable()) {
                txtNIF.grabFocus();
            } else {
                cmbDia.grabFocus();
            }
        }
    }//GEN-LAST:event_txtSegundoApellidoKeyPressed

    private void txtNIFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNIFKeyPressed
        if (evt.getKeyCode() == 10) {
            cmbDia.grabFocus();
        }
    }//GEN-LAST:event_txtNIFKeyPressed

    private void cmbDiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDiaKeyPressed
        if (evt.getKeyCode() == 10) {
            cmbMes.grabFocus();
        }
    }//GEN-LAST:event_cmbDiaKeyPressed

    private void cmbMesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMesKeyPressed
        if (evt.getKeyCode() == 10) {
            cmbAno.grabFocus();
        }
    }//GEN-LAST:event_cmbMesKeyPressed

    private void cmbAnoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbAnoKeyPressed
        if (evt.getKeyCode() == 10) {
            ckbActivo.grabFocus();
        }
    }//GEN-LAST:event_cmbAnoKeyPressed

    private void ckbActivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ckbActivoKeyPressed
        if (evt.getKeyCode() == 10) {
            txtTelefono.grabFocus();
        }
    }//GEN-LAST:event_ckbActivoKeyPressed

    private void txtEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyPressed
        if (evt.getKeyCode() == 10) {
            if (txtContrasena.isEnabled()) {
                txtContrasena.grabFocus();
            } else {
                txtPais.grabFocus();
            }
        }
    }//GEN-LAST:event_txtEmailKeyPressed

    private void txtContrasenaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContrasenaKeyPressed
        if (evt.getKeyCode() == 10) {
            txtRepiteContrasena.grabFocus();
        }
    }//GEN-LAST:event_txtContrasenaKeyPressed

    private void txtRepiteContrasenaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRepiteContrasenaKeyPressed
        if (evt.getKeyCode() == 10) {
            cmbControlDeAcceso.grabFocus();
        }
    }//GEN-LAST:event_txtRepiteContrasenaKeyPressed

    private void cmbControlDeAccesoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbControlDeAccesoKeyPressed
        if (evt.getKeyCode() == 10) {
            txtPais.grabFocus();
        }
    }//GEN-LAST:event_cmbControlDeAccesoKeyPressed

    private void txtPaisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaisKeyPressed
        if (evt.getKeyCode() == 10) {
            txtProvincia.grabFocus();
        }
    }//GEN-LAST:event_txtPaisKeyPressed

    private void txtProvinciaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProvinciaKeyPressed
        if (evt.getKeyCode() == 10) {
            txtLocalidade.grabFocus();
        }
    }//GEN-LAST:event_txtProvinciaKeyPressed

    private void txtLocalidadeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLocalidadeKeyPressed
        if (evt.getKeyCode() == 10) {
            txtDireccion.grabFocus();
        }
    }//GEN-LAST:event_txtLocalidadeKeyPressed

    private void txtDireccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyPressed
        if (evt.getKeyCode() == 10) {
            txtNumero.grabFocus();
        }
    }//GEN-LAST:event_txtDireccionKeyPressed

    private void txtNumeroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroKeyPressed
        if (evt.getKeyCode() == 10) {
            txtCP.grabFocus();
        }
    }//GEN-LAST:event_txtNumeroKeyPressed

    private void txtCPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCPKeyPressed
        if (evt.getKeyCode() == 10) {
            txtNota.grabFocus();
        }
    }//GEN-LAST:event_txtCPKeyPressed

    private void txtNotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNotaKeyPressed
        if (evt.getKeyCode() == 10) {
            btnAceptar.grabFocus();
        }
    }//GEN-LAST:event_txtNotaKeyPressed

    private void btnAceptarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAceptarKeyPressed
        if (evt.getKeyCode() == 10) {
            btnCancelar.grabFocus();
        }
    }//GEN-LAST:event_btnAceptarKeyPressed

    private void btnCancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelarKeyPressed
        if (evt.getKeyCode() == 10) {
            btnCambiarContrasena.grabFocus();
        }
    }//GEN-LAST:event_btnCancelarKeyPressed

    private void btnCambiarContrasenaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCambiarContrasenaKeyPressed
        if (evt.getKeyCode() == 10) {
            txtNombre.grabFocus();
        }
    }//GEN-LAST:event_btnCambiarContrasenaKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCambiarContrasena;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JCheckBox ckbActivo;
    private javax.swing.JComboBox<Integer> cmbAno;
    private javax.swing.JComboBox<String> cmbControlDeAcceso;
    private javax.swing.JComboBox<Integer> cmbDia;
    private javax.swing.JComboBox<Item> cmbMes;
    private javax.swing.JLabel lblCP;
    private javax.swing.JLabel lblContrasena;
    private javax.swing.JLabel lblControlDeAcceso;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFechaDeNacimiento;
    private javax.swing.JLabel lblLocalidade;
    private javax.swing.JLabel lblNIF;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNota;
    private javax.swing.JLabel lblNumero;
    private javax.swing.JLabel lblPais;
    private javax.swing.JLabel lblPrimerApellido;
    private javax.swing.JLabel lblProvincia;
    private javax.swing.JLabel lblRepiteContrasena;
    private javax.swing.JLabel lblSegundoApellido;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JPanel panelDatosDeAcceso;
    private javax.swing.JPanel panelDatosPersonale;
    private javax.swing.JPanel panelDeBotones;
    private javax.swing.JPanel panelDireccion;
    private javax.swing.JTextField txtCP;
    private javax.swing.JPasswordField txtContrasena;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtLocalidade;
    private javax.swing.JTextField txtNIF;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNota;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtPais;
    private javax.swing.JTextField txtPrimerApellido;
    private javax.swing.JTextField txtProvincia;
    private javax.swing.JPasswordField txtRepiteContrasena;
    private javax.swing.JTextField txtSegundoApellido;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
