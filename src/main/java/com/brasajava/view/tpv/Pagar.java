package com.brasajava.view.tpv;

import com.brasajava.model.Cuenta;
import com.brasajava.model.Factura;
import com.brasajava.service.ServicioFactura;
import com.brasajava.util.ApplicationLocale;
import com.brasajava.util.Session;
import com.brasajava.util.interfaces.Internationalizable;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

/**
 * Esta clase representa un frame que posibilita pagar una cuenta abierta en el
 * tpv.
 *
 * @author Ricardo Maximino
 */
public class Pagar extends javax.swing.JDialog implements Internationalizable {

    private boolean noPagaRapito;
    private final Cuenta cuenta;
    private final Factura factura;
    private BigDecimal entregado;
    private BigDecimal cambio;
    private final TPV tpv;
    private final ApplicationContext context;
    private final MessageSource messageSource;
    private final ApplicationLocale applicationLocale;
    private final Session session;
    private boolean fullprint;

    /**
     * El único constructor para instanciar esta clase.
     *
     * @param tpv del tipo com.brasajava.view.tpv.TPV.
     * @param context del tipo org.springframework.context.ApplicationContext.
     *
     * Utilizando context se pedirá una instancia de las clases:
     * <ul>
     * <li>org.springframework.context.MessageSource</li>
     * <li>com.brasajava.util.ApplicationLocale</li>
     * <li>com.brasajava.util.Session</li>
     * </ul>
     */
    public Pagar(TPV tpv, ApplicationContext context) {
        super(tpv, true);
        this.tpv = tpv;
        this.context = context;
        this.messageSource = context.getBean(MessageSource.class);
        this.applicationLocale = context.getBean(ApplicationLocale.class);
        this.session = context.getBean(Session.class);
        //para facturas con una sola cuenta.
        if (session.getFactura() != null) {
            this.factura = session.getFactura();
            this.cuenta = factura.getCuentas().get(0);
        } else {
            this.factura = context.getBean(Factura.class);
            this.cuenta = session.getCuenta();
        }
        noPagaRapito = false;
        initComponents();
        if (this.cuenta != null) {
            prepara();
        }
        activarCartera();
        setWithInternationalization();
    }

    /**
     * Actualiza toda la interfaz gráfica con el idioma seleccionado en la
     * instacia única de la clase
     * com.brasajava.util.ApplicationLocale.getLocale().
     */
    @Override
    public void refreshLanguage() {
        setWithInternationalization();
    }

    private void prepara() {
        lblTotalValue.setText(cuenta.getTotal().toString());
        lblEntregadoValue.setText(cuenta.getTotal().toString());
        lblCambioValue.setText(cuenta.getTotal().toString());
    }

    private void activarCartera() {
        for (Component c : panelCartera.getComponents()) {
            JButton b = (JButton) c;
            if (b.getActionCommand().equals("dinero")) {
                b.addActionListener(this::carteraAction);
            }
        }
    }

    private void carteraAction(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        if (noPagaRapito) {
            entregado = entregado.add(new BigDecimal(b.getName()));
            hacerCuentas();
        } else {
            entregado = new BigDecimal(b.getName());
            noPagaRapito = true;
            hacerCuentas();
        }
    }

    private void ticket() {
        JTextArea ta = new JTextArea();
        ta.setFont(new Font("monospaced", 1, 12));
        StringBuilder sb = tpv.prePrint();
        int start = 0;
        int end = 0;
        String str = "";
        sb.append("=====================================");

        //Total        
        sb.append("\n");
        sb.append("                                     ");
        start = sb.length() - 20;
        str = messageSource.getMessage("TOTAL", null, applicationLocale.getLocale());
        end = start + str.length();
        sb.replace(start, end, str);

        switch (lblTotalValue.getText().length()) {
            case 4:
                start = sb.length() - 4;
                str = lblTotalValue.getText().replace('.', ',');
                end = start + str.length();
                sb.replace(start, end, str);
                break;
            case 5:
                start = sb.length() - 5;
                str = lblTotalValue.getText().replace('.', ',');
                end = start + str.length();
                sb.replace(start, end, str);
                break;
            case 6:
                start = sb.length() - 6;
                str = lblTotalValue.getText().replace('.', ',');
                end = start + str.length();
                sb.replace(start, end, str);
                break;
            case 7:
                start = sb.length() - 7;
                str = lblTotalValue.getText().replace('.', ',');
                end = start + str.length();
                sb.replace(start, end, str);
                break;
        }

        //entregado
        sb.append("\n");
        sb.append("                                     ");
        start = sb.length() - 20;
        str = messageSource.getMessage("lbl_Entregado", null, applicationLocale.getLocale());
        end = start + str.length();
        sb.replace(start, end, str);

        switch (lblEntregadoValue.getText().length()) {
            case 4:
                start = sb.length() - 4;
                str = lblEntregadoValue.getText().replace('.', ',');
                end = start + str.length();
                sb.replace(start, end, str);
                break;
            case 5:
                start = sb.length() - 5;
                str = lblEntregadoValue.getText().replace('.', ',');
                end = start + str.length();
                sb.replace(start, end, str);
                break;
            case 6:
                start = sb.length() - 6;
                str = lblEntregadoValue.getText().replace('.', ',');
                end = start + str.length();
                sb.replace(start, end, str);
                break;
            case 7:
                start = sb.length() - 7;
                str = lblEntregadoValue.getText().replace('.', ',');
                end = start + str.length();
                sb.replace(start, end, str);
                break;
        }
        sb.append("\n");
        sb.append("                 ====================");

        //cambio
        sb.append("\n");
        sb.append("                                     ");
        start = sb.length() - 20;
        str = messageSource.getMessage("lbl_Cambio", null, applicationLocale.getLocale());
        end = start + str.length();
        sb.replace(start, end, str);

        switch (lblCambioValue.getText().length()) {
            case 4:
                start = sb.length() - 4;
                str = lblCambioValue.getText().replace('.', ',');
                end = start + str.length();
                sb.replace(start, end, str);
                break;
            case 5:
                start = sb.length() - 5;
                str = lblCambioValue.getText().replace('.', ',');
                end = start + str.length();
                sb.replace(start, end, str);
                break;
            case 6:
                start = sb.length() - 6;
                str = lblCambioValue.getText().replace('.', ',');
                end = start + str.length();
                sb.replace(start, end, str);
                break;
            case 7:
                start = sb.length() - 7;
                str = lblCambioValue.getText().replace('.', ',');
                end = start + str.length();
                sb.replace(start, end, str);
                break;
        }
        sb.append("\n");
        sb.append("\n");
        sb.append("                                     ");
        start = sb.length() - 37;
        str = messageSource.getMessage("message_AttendedYou", null, applicationLocale.getLocale()) + ": " + session.getUsuario().getNombre();
        end = start + str.length();
        sb.replace(start, end, str);

        MessageFormat header = new MessageFormat("TPV - BRASAJAVA SWING");
        MessageFormat footer = new MessageFormat("GRACIAS POR CONFIAR EN BRASAJAVA");
        ta.append(sb.toString());
        try {
            if (!fullprint) {
                ta.print(header, footer, false, null, null, false);
            } else {
                ta.print(header, footer);
            }
        } catch (PrinterException ex) {

        }
    }

    private void setWithInternationalization() {
        lblCambio.setText(messageSource.getMessage("lbl_Cambio", null, applicationLocale.getLocale()));
        lblEntregado.setText(messageSource.getMessage("lbl_Entregado", null, applicationLocale.getLocale()));
        lblTotal.setText(messageSource.getMessage("TOTAL", null, applicationLocale.getLocale()));

        btnAceptar.setText(messageSource.getMessage("button_Accept", null, applicationLocale.getLocale()));
        btnCancelar.setText(messageSource.getMessage("button_Cancel", null, applicationLocale.getLocale()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();
        lblCambio = new javax.swing.JLabel();
        lblEntregado = new javax.swing.JLabel();
        lblCambioValue = new javax.swing.JLabel();
        lblTotalValue = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();
        lblEntregadoValue = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        panelCartera = new javax.swing.JPanel();
        btn50Euros = new javax.swing.JButton();
        btn20Euros = new javax.swing.JButton();
        btn10Euros = new javax.swing.JButton();
        btn5Euros = new javax.swing.JButton();
        btn2Euros = new javax.swing.JButton();
        btn10Centimos = new javax.swing.JButton();
        btn1Euro = new javax.swing.JButton();
        btn5Centimos = new javax.swing.JButton();
        btn50Centimos = new javax.swing.JButton();
        btn2Centimos = new javax.swing.JButton();
        btn20Centimos = new javax.swing.JButton();
        btn1Centimo = new javax.swing.JButton();
        btnCE = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(this.getClass().getResource("/images/icon.png")).getImage());

        lblTotal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTotal.setText("TOTAL");
        lblTotal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTotalMouseClicked(evt);
            }
        });

        lblCambio.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblCambio.setText("CAMBIO");

        lblEntregado.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblEntregado.setText("ENTREGADO");

        lblCambioValue.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblCambioValue.setText("0,00");

        lblTotalValue.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTotalValue.setText("15,00");

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        lblEntregadoValue.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblEntregadoValue.setText("15,00");

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEntregado, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTotalValue, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCambioValue, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEntregadoValue, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEntregado, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEntregadoValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCambioValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(69, 69, 69)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAceptar, btnCancelar});

        panelCartera.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btn50Euros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cincuentaEuros.png"))); // NOI18N
        btn50Euros.setActionCommand("dinero");
        btn50Euros.setName("50.00"); // NOI18N
        btn50Euros.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn50EurosKeyPressed(evt);
            }
        });

        btn20Euros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/vinteEuros.png"))); // NOI18N
        btn20Euros.setActionCommand("dinero");
        btn20Euros.setName("20.00"); // NOI18N

        btn10Euros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/diezEuros.png"))); // NOI18N
        btn10Euros.setActionCommand("dinero");
        btn10Euros.setName("10.00"); // NOI18N

        btn5Euros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cincoEuros.png"))); // NOI18N
        btn5Euros.setActionCommand("dinero");
        btn5Euros.setName("5.00"); // NOI18N

        btn2Euros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dosEuros.png"))); // NOI18N
        btn2Euros.setActionCommand("dinero");
        btn2Euros.setBorder(null);
        btn2Euros.setName("2.00"); // NOI18N
        btn2Euros.setPreferredSize(new java.awt.Dimension(70, 70));

        btn10Centimos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/diezCentimo.png"))); // NOI18N
        btn10Centimos.setActionCommand("dinero");
        btn10Centimos.setName("0.10"); // NOI18N
        btn10Centimos.setPreferredSize(new java.awt.Dimension(70, 70));

        btn1Euro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/unEuro.png"))); // NOI18N
        btn1Euro.setActionCommand("dinero");
        btn1Euro.setBorderPainted(false);
        btn1Euro.setName("1.00"); // NOI18N
        btn1Euro.setPreferredSize(new java.awt.Dimension(70, 70));

        btn5Centimos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cincoCentimo.png"))); // NOI18N
        btn5Centimos.setActionCommand("dinero");
        btn5Centimos.setName("0.05"); // NOI18N
        btn5Centimos.setPreferredSize(new java.awt.Dimension(70, 70));

        btn50Centimos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cincuentaCentimos.png"))); // NOI18N
        btn50Centimos.setActionCommand("dinero");
        btn50Centimos.setName("0.50"); // NOI18N
        btn50Centimos.setPreferredSize(new java.awt.Dimension(70, 70));

        btn2Centimos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dosCentimos.png"))); // NOI18N
        btn2Centimos.setActionCommand("dinero");
        btn2Centimos.setName("0.02"); // NOI18N
        btn2Centimos.setPreferredSize(new java.awt.Dimension(70, 70));

        btn20Centimos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/vinteCentimos.png"))); // NOI18N
        btn20Centimos.setActionCommand("dinero");
        btn20Centimos.setName("0.20"); // NOI18N
        btn20Centimos.setPreferredSize(new java.awt.Dimension(70, 70));

        btn1Centimo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/unCentimo.png"))); // NOI18N
        btn1Centimo.setActionCommand("dinero");
        btn1Centimo.setName("0.01"); // NOI18N
        btn1Centimo.setPreferredSize(new java.awt.Dimension(70, 70));

        btnCE.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnCE.setText("CE");
        btnCE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCEActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCarteraLayout = new javax.swing.GroupLayout(panelCartera);
        panelCartera.setLayout(panelCarteraLayout);
        panelCarteraLayout.setHorizontalGroup(
            panelCarteraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCarteraLayout.createSequentialGroup()
                .addGroup(panelCarteraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn50Euros, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn20Euros, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn10Euros, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn5Euros, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelCarteraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCarteraLayout.createSequentialGroup()
                        .addGroup(panelCarteraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelCarteraLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn2Euros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn10Centimos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelCarteraLayout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(btn1Euro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn5Centimos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCarteraLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelCarteraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCarteraLayout.createSequentialGroup()
                                .addComponent(btn20Centimos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn1Centimo, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCarteraLayout.createSequentialGroup()
                                .addComponent(btn50Centimos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn2Centimos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnCE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        panelCarteraLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn10Centimos, btn1Centimo, btn1Euro, btn20Centimos, btn2Centimos, btn2Euros, btn50Centimos, btn5Centimos});

        panelCarteraLayout.setVerticalGroup(
            panelCarteraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCarteraLayout.createSequentialGroup()
                .addGroup(panelCarteraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCarteraLayout.createSequentialGroup()
                        .addComponent(btn50Euros, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn20Euros, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn10Euros, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn5Euros, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCarteraLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(panelCarteraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn2Euros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn10Centimos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(panelCarteraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn5Centimos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn1Euro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelCarteraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn2Centimos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn50Centimos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelCarteraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn20Centimos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn1Centimo, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addComponent(btnCE, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelCarteraLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn10Centimos, btn1Centimo, btn1Euro, btn20Centimos, btn2Centimos, btn2Euros, btn50Centimos, btn5Centimos});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(panelCartera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelCartera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btnCEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCEActionPerformed
        entregado = new BigDecimal("0.00");
        entregado.setScale(2, RoundingMode.HALF_DOWN);
        hacerCuentas();
    }//GEN-LAST:event_btnCEActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        entregado = new BigDecimal(lblEntregadoValue.getText());
        entregado.setScale(2, RoundingMode.HALF_DOWN);
        cambio = entregado.subtract(cuenta.getTotal());
        if (cambio.doubleValue() >= 0) {
            cuenta.setCobrada(true);
            factura.setCobrada(true);
            factura.setTotal(cuenta.getTotal());
            factura.getCuentas().add(cuenta);
            cuenta.setFactura(factura);

            //guardar no BD
            context.getBean(ServicioFactura.class).save(factura);

            factura.getCuentas().get(0).setReabrir(false);
            if (session.getFactura() != null && session.getFactura().equals(factura)) {
                session.setFactura(null);
            }

            //imprimir
            this.setVisible(false);
            ticket();

            //Logar cliente abitual
            tpv.crearCuenta();
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, messageSource.getMessage("message_IsMissingSomeMoney", null, applicationLocale.getLocale()));
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btn50EurosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn50EurosKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn50EurosKeyPressed

    private void lblTotalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTotalMouseClicked
        if (evt.getClickCount() == 2) {
            if (!fullprint) {
                lblTotal.setFont(new java.awt.Font("Tahoma", Font.BOLD, 18));
                fullprint = true;
            } else {
                lblTotal.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 18));
                fullprint = false;
            }
        }
    }//GEN-LAST:event_lblTotalMouseClicked

    private void hacerCuentas() {
        cambio = entregado.subtract(cuenta.getTotal());
        lblEntregadoValue.setText(entregado.toString());
        lblCambioValue.setText(cambio.toString());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn10Centimos;
    private javax.swing.JButton btn10Euros;
    private javax.swing.JButton btn1Centimo;
    private javax.swing.JButton btn1Euro;
    private javax.swing.JButton btn20Centimos;
    private javax.swing.JButton btn20Euros;
    private javax.swing.JButton btn2Centimos;
    private javax.swing.JButton btn2Euros;
    private javax.swing.JButton btn50Centimos;
    private javax.swing.JButton btn50Euros;
    private javax.swing.JButton btn5Centimos;
    private javax.swing.JButton btn5Euros;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCE;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCambio;
    private javax.swing.JLabel lblCambioValue;
    private javax.swing.JLabel lblEntregado;
    private javax.swing.JLabel lblEntregadoValue;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalValue;
    private javax.swing.JPanel panelCartera;
    // End of variables declaration//GEN-END:variables
}
