package com.brasajava.view.tpv;

import com.brasajava.model.Cuenta;
import com.brasajava.model.Grupo;
import com.brasajava.model.Persona;
import com.brasajava.model.Producto;
import com.brasajava.model.Venta;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;
import org.springframework.context.ApplicationContext;

/**
 * Esta clase representa el TPV virtual.
 *
 * @author Ricardo Maximino
 */
public class TPV extends javax.swing.JFrame {

    private Persona cliente;
    private Persona usuario;
    private BusquedaDePersona busqueda;
    private ApplicationContext context;

    //Grupo
    List<Grupo> grupoList;
    List<Component> grupoButtonList;
    Map<String, Grupo> grupoMap;
    int grupoWidth;
    int grupoHeight;

    //Producto
    List<Component> productoButtonList;
    Map<String, Producto> productoMap;
    int productoWidth;
    int productoHeight;

    //para registrar la posicion del scroll donde sea visible la linea de cada 
    //producto.
    Map<Cuenta, CuentaMap> cuentasMap;

    //
    int cantidad;

    //Cuenta
    List<Component> cuentaButtonList;
    Map<String, Cuenta> cuentaMap;
    int cuentaWidth;
    int cuentaHeight;
    int cuentaCount;

    /**
     * Este es el único contructor para crear una instancia de esa clase.
     *
     * @param context
     * @param cliente
     * @param usuario
     */
    public TPV(ApplicationContext context, Persona cliente, Persona usuario) {
        this.context = context;
        this.cliente = cliente;
        this.usuario = usuario;

        initComponents();
        setColumnModel();

        //ButtonList
        grupoButtonList = new ArrayList();
        productoButtonList = new ArrayList();
        cuentaButtonList = new ArrayList();
        //Map
        grupoMap = new HashMap<>();
        productoMap = new HashMap<>();
        cuentaMap = new HashMap<>();
        cuentasMap = new HashMap<>();
        //int
        grupoHeight = productoHeight = cuentaHeight = 200;
        grupoWidth = productoWidth = cuentaWidth = 200;
        cantidad = 1;
        cuentaCount = 1;

        txtCuenta.setText(((CajaTableModel) tabla.getModel()).getCuenta().getNombre());
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lblCantidad.setName("false");
        this.usuario = usuario;

    }

    public Persona getCliente() {
        return cliente;
    }

    public void setCliente(Persona cliente) {
        this.cliente = cliente;
    }

    public Persona getUsuario() {
        return usuario;
    }

    public void setUsuario(Persona usuario) {
        this.usuario = usuario;
    }

    public BusquedaDePersona getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(BusquedaDePersona busqueda) {
        this.busqueda = busqueda;
    }

    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
        crearGrupos(grupoList);
    }

    //Grupos
    /**
     * Este metodo es el arranque del tpv ya que es el responsable de añadir los
     * grupos con los productos al TPV virtual.
     *
     * @param grupoList del tipo
     * java.util.List&lt;com.brasajava.model.Grupo&gt;.
     */
    private void crearGrupos(List<Grupo> grupoList) {
        grupoButtonList.clear();
        for (Grupo g : grupoList) {
            JButton button = new JButton(g.getNombre());
            button.setSize(grupoWidth, grupoHeight);
            button.addActionListener(this::grupoButtonAction);
            grupoButtonList.add(button);
            button.setActionCommand(g.getId() + "");
            grupoMap.put(g.getId() + "", g);
        }
        reorganizaGrupo();
    }

    private void grupoButtonAction(ActionEvent e) {
        Grupo g = grupoMap.get(((JButton) e.getSource()).getActionCommand());
        productoButtonList.clear();
        for (Producto p : g.getProductos()) {
            JButton button = new JButton(p.getNombre());
            button.addActionListener(this::productoButtonAction);
            button.setActionCommand(p.getId() + "");
            button.setSize(productoWidth, productoHeight);
            if (p.getImage() != null && !p.getImage().isEmpty()) {
                Image image = new ImageIcon(getClass().getResource("/images/" + p.getImage())).getImage().getScaledInstance(190, 170, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(image));
                button.setVerticalTextPosition(AbstractButton.BOTTOM);
                button.setHorizontalTextPosition(AbstractButton.CENTER);
            }
            button.setToolTipText(p.getNombre() + " " + p.getPrecioMasIva());
            productoButtonList.add(button);
            reoganizaProducto();
            productoMap.put(p.getId() + "", p);
        }
    }

    private void reorganizaGrupo() {
        int grupoYPosition = 0;
        int grupoXPosition = 0;
        panelGrupo.removeAll();

        for (Component c : grupoButtonList) {
            c.setLocation(grupoXPosition, grupoYPosition);
            panelGrupo.add(c);
            grupoYPosition += c.getHeight();

            if (panelGrupo.getHeight() < grupoYPosition) {
                int w = panelGrupo.getWidth();

                panelGrupo.setMaximumSize(new java.awt.Dimension(200, 32767));
                panelGrupo.setPreferredSize(new java.awt.Dimension(200, grupoYPosition));

                GroupLayout panelLayout = (GroupLayout) panelGrupo.getLayout();
                panelGrupo.setLayout(panelLayout);
                panelLayout.setHorizontalGroup(
                        panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, w, Short.MAX_VALUE)
                );
                panelLayout.setVerticalGroup(
                        panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, grupoYPosition, Short.MAX_VALUE)
                );
            }
        }
        panelGrupo.repaint();
    }
    //Grupo

    //Producto
    //cada linea, en la tabla, para cada producto
    private void productoButtonAction(ActionEvent e) {
        converteCantidad();

        Producto p = productoMap.get(((JButton) e.getSource()).getActionCommand());
        Cuenta c = ((CajaTableModel) tabla.getModel()).getCuenta();
        if (!cuentasMap.containsKey(c)) {
            cuentasMap.put(c, new CuentaMap());
        }
        Map<Producto, Venta> ventasMap = cuentasMap.get(c).getMap();
        Map<Venta, Integer> pointMap = cuentasMap.get(c).getPointMap();
        List<Venta> ventas = c.getVentas();

        Venta v = ventasMap.get(p);
        if (v != null) {
            v.setVenta(v.getCantidad() + cantidad, p);
        } else {
            v = new Venta();
            v.setCuenta(c);
            v.setVenta(cantidad, p);
            ventasMap.put(p, v);
            ventas.add(v);
        }

        ((CajaTableModel) tabla.getModel()).fireTableDataChanged();
        tabla.setRowSelectionInterval(ventas.indexOf(v), ventas.indexOf(v));

        if (!pointMap.containsKey(v)) {
            int max = scrollTabla.getVerticalScrollBar().getMaximum();
            pointMap.put(v, max);
            Rectangle celRect = tabla.getCellRect(ventas.indexOf(v), 0, true);
            tabla.scrollRectToVisible(celRect);
        } else {
            scrollTabla.getVerticalScrollBar().setValue(pointMap.get(v));
        }
        c.setTotal(sumaTotal(ventas));
        resetCantidad();

    }

    private void reoganizaProducto() {
        int productoXPosition = 0;
        int productoYPosition = 0;
        panelProducto.removeAll();

        for (Component c : productoButtonList) {
            c.setLocation(productoXPosition, productoYPosition);
            panelProducto.add(c);

            productoXPosition += c.getWidth();

            if (panelProducto.getWidth() < productoXPosition + c.getWidth()) {
                productoYPosition += c.getHeight();
                productoXPosition = 0;
            }

            if (panelProducto.getHeight() < productoYPosition + c.getHeight()) {
                int w = panelProducto.getWidth();

                GroupLayout panelProductoLayout = (GroupLayout) panelProducto.getLayout();
                panelProducto.setLayout(panelProductoLayout);
                panelProductoLayout.setHorizontalGroup(
                        panelProductoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, w, Short.MAX_VALUE)
                );
                panelProductoLayout.setVerticalGroup(
                        panelProductoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, productoYPosition + c.getHeight(), Short.MAX_VALUE)
                );
            }
        }
        panelProducto.repaint();
    }
    //Producto

    //Cantidades
    private void converteCantidad() {
        cantidad = Integer.parseInt(lblCantidad.getText());
        lblCantidad.setName("false");
    }

    private void resetCantidad() {
        cantidad = 1;
        lblCantidad.setText(cantidad + "");
        lblCantidad.setName("false");
    }
    //Cantidades

    //Cuenta
    private void guardaCuenta() {
        Cuenta cuenta = ((CajaTableModel) tabla.getModel()).getCuenta();
        if (cuenta.getVentas().size() > 0 || cuenta.isAjustada()) {
            if (!cuenta.isCobrada()) {
                cuentaMap.put(cuenta.getNombre(), cuenta);
                JButton b = new JButton(cuenta.getNombre());
                b.setActionCommand(cuenta.getNombre());
                b.setSize(cuentaWidth, cuentaHeight);
                b.addActionListener(this::cuentaButtonAction);
                if (cuenta.isTicket()) {
                    b.setBackground(Color.green);
                }
                cuentaButtonList.add(b);
                reorganizaCuenta();
            }
        }
    }

    private void reorganizaCuenta() {
        int cuentaYPosition = 0;
        int cuentaXPosition = 0;
        panelCuenta.removeAll();

        for (Component c : cuentaButtonList) {
            c.setLocation(cuentaXPosition, cuentaYPosition);
            panelCuenta.add(c);
            cuentaXPosition += c.getWidth();

            if (panelCuenta.getHeight() < cuentaXPosition) {
                int h = panelCuenta.getHeight();

                panelCuenta.setMaximumSize(new Dimension(32767, 172));
                panelCuenta.setPreferredSize(new Dimension(cuentaXPosition, 172));

                GroupLayout panelLayout = (GroupLayout) panelCuenta.getLayout();
                panelCuenta.setLayout(panelLayout);
                panelLayout.setHorizontalGroup(
                        panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, cuentaXPosition, Short.MAX_VALUE)
                );
                panelLayout.setVerticalGroup(
                        panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, h, Short.MAX_VALUE)
                );
            }
        }
        panelCuenta.repaint();
    }

    private void cuentaButtonAction(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        Cuenta c = cuentaMap.get(b.getActionCommand());
        cuentaMap.remove(c.getNombre());
        cuentaButtonList.remove(b);
        abrirCuenta(c);
    }

    private void nuevaCuenta() {
        CajaTableModel model = (CajaTableModel) tabla.getModel();
        model.setCuenta(new Cuenta(cuentaCount + ""));
        lblTotalValue.setText("0,00");
        cuentaCount++;
        model.fireTableDataChanged();
        txtCuenta.setText(model.getCuenta().getNombre());
    }

    public void abrirCuenta(Cuenta c) {
        CajaTableModel model = (CajaTableModel) tabla.getModel();
        guardaCuenta();
        model.setCuenta(c);
        model.fireTableDataChanged();
        reorganizaCuenta();
        lblTotalValue.setText(sumaTotal(c.getVentas()).toString());
        txtCuenta.setText(c.getNombre());
    }

    public void crearCuenta() {
        guardaCuenta();
        nuevaCuenta();
    }

    //Arreglar internationalization
    private void borrarCuenta() {
        Cuenta c = ((CajaTableModel) tabla.getModel()).getCuenta();
        int jop = JOptionPane.showConfirmDialog(panelProducto, "Desea Borrar esta Cuenta " + c.getNombre() + "?", "Confirmación", JOptionPane.YES_OPTION);
        if (jop == JOptionPane.YES_OPTION) {
            nuevaCuenta();
            c = null;
        }
    }

    private void cambiarNombreDeLaCuenta(String nombre) {
        Cuenta cuenta = ((CajaTableModel) tabla.getModel()).getCuenta();
        cuenta.setNombre(nombre);
        cuenta.setAjustada(true);
    }

    private Cuenta sumarCuentaParaPagar() {
        Cuenta c = ((CajaTableModel) tabla.getModel()).getCuenta();
        c.setTotal(sumaTotal(c.getVentas()));
        return c;
    }
    //Cuenta

    //Total
    private BigDecimal sumaTotal(List<Venta> ventas) {
        BigDecimal total = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        for (Venta ve : ventas) {
            total = total.add(ve.getTotal().setScale(2, BigDecimal.ROUND_HALF_DOWN));
        }
        return total;
    }
    //Total

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton32 = new javax.swing.JButton();
        panelToolBar = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        scrollCuenta = new javax.swing.JScrollPane();
        panelCuenta = new javax.swing.JPanel();
        scrollGenero = new javax.swing.JScrollPane();
        panelGrupo = new javax.swing.JPanel();
        panelCaja = new javax.swing.JPanel();
        panelTeclado = new javax.swing.JPanel();
        btn1 = new javax.swing.JButton();
        btn2 = new javax.swing.JButton();
        btn3 = new javax.swing.JButton();
        btnCE = new javax.swing.JButton();
        btn4 = new javax.swing.JButton();
        btn5 = new javax.swing.JButton();
        btn6 = new javax.swing.JButton();
        btnMas = new javax.swing.JButton();
        btn7 = new javax.swing.JButton();
        btn8 = new javax.swing.JButton();
        btn9 = new javax.swing.JButton();
        btnMenos = new javax.swing.JButton();
        btn0 = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnPagar = new javax.swing.JButton();
        btnNuevaCuenta = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblTotalLabel = new javax.swing.JLabel();
        lblTotalValue = new javax.swing.JLabel();
        scrollTabla = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCuenta = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lblCantidad = new javax.swing.JLabel();
        btnTicket = new javax.swing.JButton();
        scrollProducto = new javax.swing.JScrollPane();
        panelProducto = new javax.swing.JPanel();

        jButton32.setText("jButton32");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(this.getClass().getResource("/images/icon.png")).getImage());

        panelToolBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setText("Cliente");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setText("Usuario");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelToolBarLayout = new javax.swing.GroupLayout(panelToolBar);
        panelToolBar.setLayout(panelToolBarLayout);
        panelToolBarLayout.setHorizontalGroup(
            panelToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelToolBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addContainerGap(444, Short.MAX_VALUE))
        );

        panelToolBarLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton2});

        panelToolBarLayout.setVerticalGroup(
            panelToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelToolBarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        panelToolBarLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2});

        javax.swing.GroupLayout panelCuentaLayout = new javax.swing.GroupLayout(panelCuenta);
        panelCuenta.setLayout(panelCuentaLayout);
        panelCuentaLayout.setHorizontalGroup(
            panelCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1114, Short.MAX_VALUE)
        );
        panelCuentaLayout.setVerticalGroup(
            panelCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 205, Short.MAX_VALUE)
        );

        scrollCuenta.setViewportView(panelCuenta);

        scrollGenero.setPreferredSize(new java.awt.Dimension(200, 100));

        panelGrupo.setMaximumSize(new java.awt.Dimension(100, 32767));
        panelGrupo.setPreferredSize(new java.awt.Dimension(200, 400));

        javax.swing.GroupLayout panelGrupoLayout = new javax.swing.GroupLayout(panelGrupo);
        panelGrupo.setLayout(panelGrupoLayout);
        panelGrupoLayout.setHorizontalGroup(
            panelGrupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 221, Short.MAX_VALUE)
        );
        panelGrupoLayout.setVerticalGroup(
            panelGrupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, Short.MAX_VALUE, Short.MAX_VALUE)
        );

        scrollGenero.setViewportView(panelGrupo);

        panelCaja.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        panelTeclado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btn1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn1.setText("1");
        btn1.setPreferredSize(new java.awt.Dimension(89, 23));
        btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn1ActionPerformed(evt);
            }
        });

        btn2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn2.setText("2");
        btn2.setPreferredSize(new java.awt.Dimension(89, 23));
        btn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2ActionPerformed(evt);
            }
        });

        btn3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn3.setText("3");
        btn3.setPreferredSize(new java.awt.Dimension(89, 23));
        btn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3ActionPerformed(evt);
            }
        });

        btnCE.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnCE.setText("CE");
        btnCE.setPreferredSize(new java.awt.Dimension(89, 23));
        btnCE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCEActionPerformed(evt);
            }
        });

        btn4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn4.setText("4");
        btn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn4ActionPerformed(evt);
            }
        });

        btn5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn5.setText("5");
        btn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn5ActionPerformed(evt);
            }
        });

        btn6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn6.setText("6");
        btn6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn6ActionPerformed(evt);
            }
        });

        btnMas.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnMas.setText("+");
        btnMas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasActionPerformed(evt);
            }
        });

        btn7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn7.setText("7");
        btn7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn7ActionPerformed(evt);
            }
        });

        btn8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn8.setText("8");
        btn8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn8ActionPerformed(evt);
            }
        });

        btn9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn9.setText("9");
        btn9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn9ActionPerformed(evt);
            }
        });

        btnMenos.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnMenos.setText("-");
        btnMenos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenosActionPerformed(evt);
            }
        });

        btn0.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn0.setText("0");
        btn0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn0ActionPerformed(evt);
            }
        });

        btnBorrar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBorrar.setText("BORRAR");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        btnPagar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPagar.setText("PAGAR");
        btnPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagarActionPerformed(evt);
            }
        });

        btnNuevaCuenta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnNuevaCuenta.setText("NUEVA CUENTA");
        btnNuevaCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaCuentaActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblTotalLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTotalLabel.setText("TOTAL:");

        lblTotalValue.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTotalValue.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalValue.setText("0,00");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTotalValue, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalValue, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelTecladoLayout = new javax.swing.GroupLayout(panelTeclado);
        panelTeclado.setLayout(panelTecladoLayout);
        panelTecladoLayout.setHorizontalGroup(
            panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTecladoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTecladoLayout.createSequentialGroup()
                        .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelTecladoLayout.createSequentialGroup()
                        .addComponent(btn4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMas))
                    .addGroup(panelTecladoLayout.createSequentialGroup()
                        .addComponent(btn7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMenos))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelTecladoLayout.createSequentialGroup()
                        .addGroup(panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnPagar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn0, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNuevaCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        panelTecladoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnCE, btnMas, btnMenos});

        panelTecladoLayout.setVerticalGroup(
            panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTecladoLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn1, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                    .addGroup(panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnCE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn4)
                    .addComponent(btn5)
                    .addComponent(btn6)
                    .addComponent(btnMas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn7)
                    .addComponent(btn8)
                    .addComponent(btn9)
                    .addComponent(btnMenos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn0)
                    .addComponent(btnBorrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPagar)
                    .addComponent(btnNuevaCuenta)))
        );

        panelTecladoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnBorrar, btnCE, btnMas, btnMenos, btnNuevaCuenta, btnPagar});

        tabla.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabla.setModel(getModel());
        tabla.setRowHeight(50);
        scrollTabla.setViewportView(tabla);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("NOMBRE DE LA CUENTA");

        txtCuenta.setBackground(java.awt.SystemColor.control);
        txtCuenta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCuenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCuenta.setBorder(null);
        txtCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCuentaActionPerformed(evt);
            }
        });
        txtCuenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCuentaKeyPressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("CANTIDAD");

        lblCantidad.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblCantidad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCantidad.setText("1");
        lblCantidad.setName("false"); // NOI18N

        btnTicket.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnTicket.setText("Ticket");
        btnTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTicketActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(lblCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTicket)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCuenta)
                            .addComponent(lblCantidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelCajaLayout = new javax.swing.GroupLayout(panelCaja);
        panelCaja.setLayout(panelCajaLayout);
        panelCajaLayout.setHorizontalGroup(
            panelCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCajaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrollTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelTeclado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelCajaLayout.setVerticalGroup(
            panelCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCajaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTeclado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelProductoLayout = new javax.swing.GroupLayout(panelProducto);
        panelProducto.setLayout(panelProductoLayout);
        panelProductoLayout.setHorizontalGroup(
            panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 519, Short.MAX_VALUE)
        );
        panelProductoLayout.setVerticalGroup(
            panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1013, Short.MAX_VALUE)
        );

        scrollProducto.setViewportView(panelProducto);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scrollCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelToolBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCaja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(scrollGenero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTicketActionPerformed
        ((CajaTableModel) tabla.getModel()).getCuenta().setTicket(true);
    }//GEN-LAST:event_btnTicketActionPerformed

    private void txtCuentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCuentaKeyPressed
        if (evt.getKeyCode() == 10) {
            cambiarNombreDeLaCuenta(txtCuenta.getText());
        }
    }//GEN-LAST:event_txtCuentaKeyPressed

    private void txtCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCuentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCuentaActionPerformed

    private void btnNuevaCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaCuentaActionPerformed
        crearCuenta();
    }//GEN-LAST:event_btnNuevaCuentaActionPerformed

    private void btnPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagarActionPerformed
        Pagar pagar = new Pagar(this, sumarCuentaParaPagar(), cliente, usuario, this);
        pagar.setLocationRelativeTo(null);
        pagar.setVisible(true);
    }//GEN-LAST:event_btnPagarActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        borrarCuenta();
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btn0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn0ActionPerformed
        tecladoVirtualAction(evt);
    }//GEN-LAST:event_btn0ActionPerformed

    private void btnMenosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenosActionPerformed
        int row = tabla.getSelectedRow();
        if (row >= 0) {
            CajaTableModel model = (CajaTableModel) tabla.getModel();
            Venta v = model.getCuenta().getVentas().get(row);
            int cantidad = v.getCantidad() - Integer.parseInt(lblCantidad.getText());
            int confirmacion = -3;
            if (cantidad < 1) {
                confirmacion = JOptionPane.showConfirmDialog(this, "Desea eliminar este articulo?");
                if (confirmacion == JOptionPane.OK_OPTION) {
                    model.getCuenta().getVentas().remove(v);
                    Map<Producto, Venta> ventasMap = cuentasMap.get(model.getCuenta()).getMap();
                    ventasMap.remove(v.getProducto());
                    if (model.getCuenta().getVentas().size() > 0) {
                        Venta venta = model.getCuenta().getVentas().get(model.getCuenta().getVentas().size() - 1);
                        fireDataChage(venta);
                    } else {
                        model.fireTableDataChanged();
                    }
                    sumaTotal(model.getCuenta().getVentas());
                }
            } else {
                v.setVenta(cantidad, v.getProducto());
                fireDataChage(v);
            }
        }
        resetCantidad();
    }//GEN-LAST:event_btnMenosActionPerformed

    private void btn9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn9ActionPerformed
        tecladoVirtualAction(evt);
    }//GEN-LAST:event_btn9ActionPerformed

    private void btn8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn8ActionPerformed
        tecladoVirtualAction(evt);
    }//GEN-LAST:event_btn8ActionPerformed

    private void btn7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn7ActionPerformed
        tecladoVirtualAction(evt);
    }//GEN-LAST:event_btn7ActionPerformed

    private void btnMasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasActionPerformed
        int row = tabla.getSelectedRow();
        if (row >= 0) {
            CajaTableModel model = (CajaTableModel) tabla.getModel();
            Venta v = model.getCuenta().getVentas().get(row);
            v.setVenta(v.getCantidad() + Integer.parseInt(lblCantidad.getText()), v.getProducto());
            model.fireTableDataChanged();
            sumaTotal(model.getCuenta().getVentas());
            int index = model.getCuenta().getVentas().indexOf(v);
            tabla.setRowSelectionInterval(index, index);

        }
        resetCantidad();
    }//GEN-LAST:event_btnMasActionPerformed

    private void btn6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn6ActionPerformed
        tecladoVirtualAction(evt);
    }//GEN-LAST:event_btn6ActionPerformed

    private void btn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn5ActionPerformed
        tecladoVirtualAction(evt);
    }//GEN-LAST:event_btn5ActionPerformed

    private void btn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn4ActionPerformed
        tecladoVirtualAction(evt);
    }//GEN-LAST:event_btn4ActionPerformed

    private void btnCEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCEActionPerformed
        resetCantidad();
    }//GEN-LAST:event_btnCEActionPerformed

    private void btn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3ActionPerformed
        tecladoVirtualAction(evt);
    }//GEN-LAST:event_btn3ActionPerformed

    private void btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2ActionPerformed
        tecladoVirtualAction(evt);
    }//GEN-LAST:event_btn2ActionPerformed

    private void btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn1ActionPerformed
        tecladoVirtualAction(evt);
    }//GEN-LAST:event_btn1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (busqueda != null) {
            busqueda.setLocationRelativeTo(null);
            busqueda.setVisible(true);
        } else {
            busqueda = context.getBean(BusquedaDePersona.class);
            busqueda.setLocationRelativeTo(null);
            busqueda.setVisible(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (busqueda != null) {
            busqueda.setLocationRelativeTo(null);
            busqueda.setVisible(true);
        } else {
            busqueda = context.getBean(BusquedaDePersona.class);
            busqueda.setLocationRelativeTo(null);
            busqueda.setVisible(true);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void fireDataChage(Venta v) {
        CajaTableModel model = (CajaTableModel) tabla.getModel();
        model.fireTableDataChanged();
        sumaTotal(model.getCuenta().getVentas());
        int index = model.getCuenta().getVentas().indexOf(v);
        tabla.setRowSelectionInterval(index, index);
    }

    private void tecladoVirtualAction(java.awt.event.ActionEvent evt) {
        JButton b = (JButton) evt.getSource();
        if (lblCantidad.getName().equals("true")) {
            lblCantidad.setText(lblCantidad.getText() + b.getText());
        } else if (!b.getText().equals("0")) {
            lblCantidad.setText(b.getText());
            lblCantidad.setName("true");
        }
    }

    private TableModel getModel() {
        return new CajaTableModel();
    }

    private void setColumnModel() {
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    tabla.getColumnModel().getColumn(0).setPreferredWidth(5);
                    break;
                case 1:
                    tabla.getColumnModel().getColumn(1).setPreferredWidth(200);
                    break;
                case 2:
                    tabla.getColumnModel().getColumn(2).setPreferredWidth(20);
                    break;
                case 3:
                    tabla.getColumnModel().getColumn(3).setPreferredWidth(20);
                    break;
            }
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn0;
    private javax.swing.JButton btn1;
    private javax.swing.JButton btn2;
    private javax.swing.JButton btn3;
    private javax.swing.JButton btn4;
    private javax.swing.JButton btn5;
    private javax.swing.JButton btn6;
    private javax.swing.JButton btn7;
    private javax.swing.JButton btn8;
    private javax.swing.JButton btn9;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnCE;
    private javax.swing.JButton btnMas;
    private javax.swing.JButton btnMenos;
    private javax.swing.JButton btnNuevaCuenta;
    private javax.swing.JButton btnPagar;
    private javax.swing.JButton btnTicket;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton32;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblTotalLabel;
    private javax.swing.JLabel lblTotalValue;
    private javax.swing.JPanel panelCaja;
    private javax.swing.JPanel panelCuenta;
    private javax.swing.JPanel panelGrupo;
    private javax.swing.JPanel panelProducto;
    private javax.swing.JPanel panelTeclado;
    private javax.swing.JPanel panelToolBar;
    private javax.swing.JScrollPane scrollCuenta;
    private javax.swing.JScrollPane scrollGenero;
    private javax.swing.JScrollPane scrollProducto;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField txtCuenta;
    // End of variables declaration//GEN-END:variables
/*
    private List<Grupo> createGruops() {
        Producto pro1 = new Producto();
        pro1.setId(1);
        pro1.setNombre("Coca-Cola");
        pro1.setPrecioMasIva(new BigDecimal(1.33).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        pro1.setImage("hitUva.png");

        Producto pro2 = new Producto();
        pro2.setId(2);
        pro2.setNombre("Fanta Laranja");
        pro2.setPrecioMasIva(new BigDecimal(1.33).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        pro2.setImage("hitPina.png");

        Producto pro3 = new Producto();
        pro3.setId(3);
        pro3.setNombre("Dolly");
        pro3.setPrecioMasIva(new BigDecimal(1.13).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        pro3.setImage("hitManzana.png");

        Producto pro4 = new Producto();
        pro4.setId(4);
        pro4.setNombre("Frizz");
        pro4.setPrecioMasIva(new BigDecimal(1.00).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        pro4.setImage("frizz.png");

        Producto pro5 = new Producto();
        pro5.setId(5);
        pro5.setNombre("Ciel");
        pro5.setPrecioMasIva(new BigDecimal(1.64).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        pro5.setImage("ciel.png");
    

        Grupo refrescos = new Grupo();
        refrescos.setId(1);
        refrescos.setNombre("Refrescos");
        refrescos.getProductos().add(pro1);
        refrescos.getProductos().add(pro2);
        refrescos.getProductos().add(pro3);
        refrescos.getProductos().add(pro4);
        refrescos.getProductos().add(pro5);

        Producto pro21 = new Producto();
        pro21.setId(21);
        pro21.setNombre("Solo");
        pro21.setPrecioMasIva(new BigDecimal(1).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        pro21.setImage("solo.png");

        Producto pro22 = new Producto();
        pro22.setId(22);
        pro22.setNombre("Cortado");
        pro22.setPrecioMasIva(new BigDecimal(1).setScale(2, BigDecimal.ROUND_HALF_DOWN));

        Producto pro23 = new Producto();
        pro23.setId(23);
        pro23.setNombre("Cafe con Leite");
        pro23.setPrecioMasIva(new BigDecimal(1.20).setScale(2, BigDecimal.ROUND_HALF_DOWN));

        Producto pro24 = new Producto();
        pro24.setId(24);
        pro24.setNombre("Solo largo");
        pro24.setPrecioMasIva(new BigDecimal(1).setScale(2, BigDecimal.ROUND_HALF_DOWN));

        Producto pro25 = new Producto();
        pro25.setId(25);
        pro25.setNombre("Bombom");
        pro25.setPrecioMasIva(new BigDecimal(1.2).setScale(2, BigDecimal.ROUND_HALF_DOWN));

        Producto pro26 = new Producto();
        pro26.setId(26);
        pro26.setNombre("Cafe con Leite");
        pro26.setPrecioMasIva(new BigDecimal(1.20).setScale(2, BigDecimal.ROUND_HALF_DOWN));

        Grupo cafes = new Grupo();
        cafes.setId(2);
        cafes.setNombre("Cafes");
        cafes.getProductos().add(pro21);
        cafes.getProductos().add(pro22);
        cafes.getProductos().add(pro23);
        cafes.getProductos().add(pro24);
        cafes.getProductos().add(pro25);
        cafes.getProductos().add(pro26);

        Producto pro31 = new Producto();
        pro31.setId(31);
        pro31.setNombre("1/2 Tostada con Aceite");
        pro31.setPrecioMasIva(new BigDecimal(0.60).setScale(2, BigDecimal.ROUND_HALF_DOWN));

        Producto pro32 = new Producto();
        pro32.setId(32);
        pro32.setNombre("1/2 Tostada con Fiambre");
        pro32.setPrecioMasIva(new BigDecimal(1.10).setScale(2, BigDecimal.ROUND_HALF_DOWN));

        Producto pro33 = new Producto();
        pro33.setId(33);
        pro33.setNombre("1/2 Tostada con doble Fiambre");
        pro33.setPrecioMasIva(new BigDecimal(1.13).setScale(2, BigDecimal.ROUND_HALF_DOWN));

        Grupo tostadas = new Grupo();
        tostadas.setId(3);
        tostadas.setNombre("Tostadas");
        tostadas.getProductos().add(pro31);
        tostadas.getProductos().add(pro32);
        tostadas.getProductos().add(pro33);

        List<Grupo> generos = new ArrayList();
        generos.add(refrescos);
        generos.add(cafes);
        generos.add(tostadas);

        return generos;
    }
     */
}
