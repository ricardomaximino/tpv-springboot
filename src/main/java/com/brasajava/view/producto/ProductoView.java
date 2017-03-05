package com.brasajava.view.producto;

import com.brasajava.model.Grupo;
import com.brasajava.model.Producto;
import com.brasajava.service.ServicioGrupo;
import com.brasajava.service.ServicioProducto;
import com.brasajava.util.ApplicationLocale;
import com.brasajava.util.PrototypeContext;
import com.brasajava.util.interfaces.Internationalizable;
import com.brasajava.view.principal.MainFrame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

/**
 * Esta clase representa el producto, donde se crea o se actualiza un producto.
 *
 * @author Ricardo Maximino
 */
public class ProductoView extends javax.swing.JInternalFrame implements Internationalizable {

    private Producto producto;
    private DefaultListModel<Grupo> listModel;
    private final ApplicationContext context;
    private final MessageSource messageSource;
    private final ApplicationLocale applicationLocale;

    private String dialogTitle;
    private String filterDescription;
    private String message_YA_EXISTE;
    private String productoImage;

    private static final String GUARDAR = "GUARDAR";
    private static final String CANCELAR = "CANCELAR";

    /**
     * Único constructo para crear una instancia desta clase.
     *
     * @param context del tipo org.springframework.context.ApplicationContext.
     * <p>
     * Utilizando el context se pedirá una instancia de las clase:</p>
     * <ul>
     * <li>org.springframework.context.MessageSource</li>
     * <li>com.brasajava.util.ApplicationLocale</li>
     * </ul>
     */
    public ProductoView(ApplicationContext context) {
        this.context = context;
        this.messageSource = context.getBean(MessageSource.class);
        this.applicationLocale = context.getBean(ApplicationLocale.class);
        initComponents();
        setWithInternationalization();
    }

    /**
     * Retorna el valor de la variable producto.
     *
     * @return del tipo com.brasajava.model.Producto.
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Configura el valor de la variable producto.
     *
     * @param producto del tipo com.brasajava.model.Producto. Al configurar el
     * producto automaticamente se actualiza la interfaz gráfica con los datos
     * del producto.
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
        setProductoFields();
    }

    /**
     * Añade un grupo listModel, en caso de que este grupo ya esté en el
     * listModel se exibirá un mensaje adivirtiendo que yá existe.
     *
     * @param grup del tipo com.brasajava.model.Grupo.
     */
    public void add(Grupo grup) {
        if (!listModel.contains(grup)) {
            listModel.addElement(grup);
        } else {
            JOptionPane.showMessageDialog(this, message_YA_EXISTE);
        }
    }

    /**
     * Override este metodo para que antes del dispose() se elimine la
     * referencia en el prototypeContext.
     */
    @Override
    public void dispose() {
        PrototypeContext pc = context.getBean(PrototypeContext.class);
        pc.remove(this);
        super.dispose();
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

    private void setProductoFields() {
        System.out.println("SetProductoFields");
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
                lblMolduraProducto.setToolTipText(productoImage);
                System.out.println("Imagen: " + productoImage);
            } catch (NullPointerException ex) {
                System.out.println("Imagem fallo");
            }
        }
    }

    private void colectarDatosDelProducto() {
        System.out.println("CollectandoDatos del Producto");
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
        for (Grupo g : producto.getGrupos()) {
            g.getProductos().remove(producto);
            context.getBean(ServicioGrupo.class).save(g);
        }
        producto.getGrupos().clear();

        for (int i = 0; i < listModel.size(); i++) {
            Grupo grupo = listModel.getElementAt(i);
            producto.getGrupos().add(grupo);
            if (!grupo.getProductos().contains(producto)) {
                grupo.getProductos().add(producto);
                context.getBean(ServicioGrupo.class).save(grupo);
            }
        }
        this.producto = context.getBean(ServicioProducto.class).save(producto);
        setProductoFields();
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
        switch (((JButton) evt.getSource()).getName()) {
            case GUARDAR:
                colectarDatosDelProducto();
                break;
            case CANCELAR:
                setProductoFields();
                break;
            default:
                JOptionPane.showMessageDialog(this, evt.getActionCommand());
        }
    }

    private void popUpMenu(MouseEvent evt) {
        if (evt.getButton() == 3) {
            if (listProducto.getSelectedIndex() == -1) {
                menuItemQuitar.setEnabled(false);
            } else {
                menuItemQuitar.setEnabled(true);
            }
            popUpMenuGrupos.show(this, evt.getX() + 45, evt.getY() + 315);
        }
    }

    private void setWithInternationalization() {
        btnCancelar.setText(messageSource.getMessage("button_Cancel", null, applicationLocale.getLocale()));
        btnGuardar.setText(messageSource.getMessage("button_Save", null, applicationLocale.getLocale()));

        lblCodigoProducto.setText(messageSource.getMessage("label_Code", null, applicationLocale.getLocale()));
        lblNombreProducto.setText(messageSource.getMessage("label_Name", null, applicationLocale.getLocale()));
        lblDescripcionDelProducto.setText(messageSource.getMessage("label_Description", null, applicationLocale.getLocale()));
        lblCustoDelProducto.setText(messageSource.getMessage("label_CostPrice", null, applicationLocale.getLocale()));
        lblMargenDelProducto.setText(messageSource.getMessage("label_Margin", null, applicationLocale.getLocale()));
        lblPrecioSinIva.setText(messageSource.getMessage("label_PriceWithoutTax", null, applicationLocale.getLocale()));
        lblIva.setText(messageSource.getMessage("label_Tax", null, applicationLocale.getLocale()));
        lblPrecioConIva.setText(messageSource.getMessage("label_PriceWithTax", null, applicationLocale.getLocale()));
        lblAlmacen.setText(messageSource.getMessage("label_Stock", null, applicationLocale.getLocale()));
        lblGrupos.setText(messageSource.getMessage("label_Groups", null, applicationLocale.getLocale()));
        lblImagen.setText(messageSource.getMessage("label_Image", null, applicationLocale.getLocale()));

        menuItemAñadir.setText(messageSource.getMessage("menuItem_Add", null, applicationLocale.getLocale()));
        menuItemQuitar.setText(messageSource.getMessage("menuItem_exclude", null, applicationLocale.getLocale()));
        menuItemQuitarImagen.setText(messageSource.getMessage("menuItem_exclude", null, applicationLocale.getLocale()) + " "
                + messageSource.getMessage("label_Image", null, applicationLocale.getLocale()));

        dialogTitle = messageSource.getMessage("message_SelectImageForTheProduct", null, applicationLocale.getLocale());
        filterDescription = messageSource.getMessage("label_Image", null, applicationLocale.getLocale());
        message_YA_EXISTE = messageSource.getMessage("message_AlreadyHave", null, applicationLocale.getLocale());

        ckbActivo.setText(messageSource.getMessage("label_ActiveProduct", null, applicationLocale.getLocale()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popUpMenuGrupos = new javax.swing.JPopupMenu();
        menuItemAñadir = new javax.swing.JMenuItem();
        menuItemQuitar = new javax.swing.JMenuItem();
        popUpMenuImagen = new javax.swing.JPopupMenu();
        menuItemQuitarImagen = new javax.swing.JMenuItem();
        panelProducto = new javax.swing.JPanel();
        lblCodigoProducto = new javax.swing.JLabel();
        lblNombreProducto = new javax.swing.JLabel();
        lblDescripcionDelProducto = new javax.swing.JLabel();
        lblCustoDelProducto = new javax.swing.JLabel();
        lblPrecioSinIva = new javax.swing.JLabel();
        lblPrecioConIva = new javax.swing.JLabel();
        lblIva = new javax.swing.JLabel();
        lblAlmacen = new javax.swing.JLabel();
        lblGrupos = new javax.swing.JLabel();
        lblImagen = new javax.swing.JLabel();
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
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        scrollList = new javax.swing.JScrollPane();
        listProducto = new javax.swing.JList<>();
        ckbActivo = new javax.swing.JCheckBox();

        menuItemAñadir.setText("AÑADIR");
        menuItemAñadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAñadirActionPerformed(evt);
            }
        });
        popUpMenuGrupos.add(menuItemAñadir);

        menuItemQuitar.setText("QUITAR");
        menuItemQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemQuitarActionPerformed(evt);
            }
        });
        popUpMenuGrupos.add(menuItemQuitar);

        menuItemQuitarImagen.setText("Quitar Imagen");
        menuItemQuitarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemQuitarImagenActionPerformed(evt);
            }
        });
        popUpMenuImagen.add(menuItemQuitarImagen);

        setClosable(true);
        setIconifiable(true);

        lblCodigoProducto.setText("CÓDIGO");

        lblNombreProducto.setText("NOMBRE");

        lblDescripcionDelProducto.setText("DESCRIPCION");

        lblCustoDelProducto.setText("CUSTO DEL PROD.");

        lblPrecioSinIva.setText("PRECIO SIN I.V.A.");

        lblPrecioConIva.setText("PRECIO CON I.V.A.");

        lblIva.setText("I.V.A. (%)");

        lblAlmacen.setText("ALMACEN");

        lblGrupos.setText("GRUPOS");

        lblImagen.setText("IMAGEN");

        lblMolduraProducto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        java.awt.Image image = new javax.swing.ImageIcon(getClass().getResource("/images/hitPina.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
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

        btnCancelar.setText("CANCELAR");
        btnCancelar.setName("CANCELAR"); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnGuardar.setText("GUARDAR");
        btnGuardar.setToolTipText("");
        btnGuardar.setName("GUARDAR"); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
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
                            .addComponent(lblNombreProducto)
                            .addComponent(lblDescripcionDelProducto)
                            .addComponent(lblCustoDelProducto)
                            .addComponent(lblPrecioSinIva)
                            .addComponent(lblPrecioConIva)
                            .addComponent(lblCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                            .addGroup(panelProductoLayout.createSequentialGroup()
                                .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(panelProductoLayout.createSequentialGroup()
                        .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelProductoLayout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addComponent(lblCodigoProductoValue, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(58, 58, 58)
                                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelProductoLayout.createSequentialGroup()
                                .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelProductoLayout.createSequentialGroup()
                                        .addGap(55, 55, 55)
                                        .addComponent(lblGrupos))
                                    .addComponent(scrollList, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(39, 39, 39)
                                .addComponent(lblMolduraProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelProductoLayout.createSequentialGroup()
                        .addComponent(ckbActivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblImagen)
                        .addGap(101, 101, 101)))
                .addContainerGap())
        );

        panelProductoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCancelar, btnGuardar});

        panelProductoLayout.setVerticalGroup(
            panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelProductoLayout.createSequentialGroup()
                        .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCodigoProductoValue, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCodigoProducto)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnCancelar)
                                .addComponent(btnGuardar)))
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
                    .addComponent(lblImagen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelProductoLayout.createSequentialGroup()
                        .addComponent(lblGrupos)
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

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        btnProductoAction(evt);
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        btnProductoAction(evt);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void lblMolduraProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMolduraProductoMouseClicked
        if (evt.getButton() == 1) {
            JFileChooser fc = new JFileChooser(getClass().getResource("/images/").getPath(), FileSystemView.getFileSystemView());
            fc.setDialogTitle(dialogTitle + " " + producto.getNombre());
            fc.setFileFilter(new FileNameExtensionFilter(filterDescription, "jpg", "gif", "png", "jpeg"));
            int respuesta = fc.showOpenDialog(this);
            if (respuesta == JFileChooser.APPROVE_OPTION) {
                productoImage = fc.getSelectedFile().getName();

                try {
                    Image image = new ImageIcon(getClass().getResource("/images/" + productoImage)).getImage().getScaledInstance(190, 170, Image.SCALE_SMOOTH);
                    lblMolduraProducto.setIcon(new ImageIcon(image));
                    lblMolduraProducto.setToolTipText(productoImage);
                } catch (NullPointerException ex) {
                }

            }
        }

        if (evt.getButton() == 3) {
            if (productoImage != null && productoImage.isEmpty()) {
                menuItemQuitarImagen.setEnabled(false);
            } else {
                menuItemQuitarImagen.setEnabled(true);
            }
            popUpMenuImagen.show(this, evt.getX() + 250, evt.getY() + 300);
        }
    }//GEN-LAST:event_lblMolduraProductoMouseClicked

    private void listProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listProductoMouseClicked
        popUpMenu(evt);
        if (evt.getClickCount() == 2) {
            int index = listProducto.getSelectedIndex();
            if (index > -1) {
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

    private void menuItemQuitarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemQuitarImagenActionPerformed
        productoImage = "";
        lblMolduraProducto.setIcon(null);
    }//GEN-LAST:event_menuItemQuitarImagenActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JCheckBox ckbActivo;
    private javax.swing.JLabel lblAlmacen;
    private javax.swing.JLabel lblCodigoProducto;
    private javax.swing.JLabel lblCodigoProductoValue;
    private javax.swing.JLabel lblCustoDelProducto;
    private javax.swing.JLabel lblDescripcionDelProducto;
    private javax.swing.JLabel lblGrupos;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblIva;
    private javax.swing.JLabel lblMargenDelProducto;
    private javax.swing.JLabel lblMolduraProducto;
    private javax.swing.JLabel lblNombreProducto;
    private javax.swing.JLabel lblPrecioConIva;
    private javax.swing.JLabel lblPrecioSinIva;
    private javax.swing.JList<Grupo> listProducto;
    private javax.swing.JMenuItem menuItemAñadir;
    private javax.swing.JMenuItem menuItemQuitar;
    private javax.swing.JMenuItem menuItemQuitarImagen;
    private javax.swing.JPanel panelProducto;
    private javax.swing.JPopupMenu popUpMenuGrupos;
    private javax.swing.JPopupMenu popUpMenuImagen;
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
