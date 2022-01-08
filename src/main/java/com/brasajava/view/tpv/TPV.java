package com.brasajava.view.tpv;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

import com.brasajava.model.Cuenta;
import com.brasajava.model.Factura;
import com.brasajava.model.Grupo;
import com.brasajava.model.Persona;
import com.brasajava.model.Producto;
import com.brasajava.model.Venta;
import com.brasajava.service.ServicioCliente;
import com.brasajava.service.ServicioFactura;
import com.brasajava.service.ServicioUsuario;
import com.brasajava.util.ApplicationLocale;
import com.brasajava.util.Session;
import com.brasajava.util.interfaces.Internationalizable;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

/**
 * Esta clase representa el TPV virtual.
 *
 * @author Ricardo Maximino
 */
public class TPV extends javax.swing.JFrame implements Internationalizable {

    private Persona cliente;
    private Persona usuario;
    private BusquedaDePersona busqueda;
    private final ApplicationContext context;
    private final MessageSource messageSource;
    private final ApplicationLocale applicationLocale;
    private Session session;

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

    //crear un archivo para datos de la empresa property
    private static final String EMPRESA = "BRASAJAVA S.L.";
    private static final String DIRECCION = "CALLE SANTA ISABEL, 321";
    private static final String CODIGO_POSTAL_LOCALIDAD = "03130 - ALICANTE";
    private static final String TELEFONO = "966 658 741";
    private static final String CIF = "B9541274";

    /**
     * Este es el único contructor para crear una instancia de esa clase.
     *
     * @param context del tipo org.springframework.context.ApplicationContext;
     * @param session del tipo com.brasajava.util.Session;
     *
     * <p>
     * Atravez del context se invocará una instacia de las clases :</p>
     * <ul>
     * <li>org.springframework.context.ApplicationLocale</li>
     * <li>org.springframework.context.MessageSource</li>
     * </ul>
     */
    public TPV(ApplicationContext context, Session session) {
        this.context = context;
        this.messageSource = context.getBean(MessageSource.class);
        this.applicationLocale = context.getBean(ApplicationLocale.class);
        this.session = session;
        this.cliente = session.getCliente();
        this.usuario = session.getUsuario();

        //ButtonList
        grupoButtonList = new ArrayList<>();
        productoButtonList = new ArrayList<>();
        cuentaButtonList = new ArrayList<>();

        //Map
        grupoMap = new HashMap<>();
        productoMap = new HashMap<>();
        cuentaMap = new HashMap<>();
        cuentasMap = new HashMap<>();

        //int size of the buttons
        grupoHeight = productoHeight = cuentaHeight = 200;
        grupoWidth = productoWidth = cuentaWidth = 200;

        //defaults
        cantidad = 1;
        cuentaCount = 1;

        initComponents();

        txtCuenta.setText(((CajaTableModel) tabla.getModel()).getCuenta().getNombre());
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lblCantidadValue.setName("false");

        setColumnModel();
        setWithInternationalization();
    }

    /**
     * Retorna el valor de la variable cliente.
     *
     * @return del tipo com.brasajava.model.Persona.
     */
    public Persona getCliente() {
        return cliente;
    }

    /**
     * Configura la variable cliente.
     *
     * @param cliente del tipo com.brasajava.model.Persona.
     */
    public void setCliente(Persona cliente) {
        this.cliente = cliente;
    }

    /**
     * Retorna el valor de la variable usuario.
     *
     * @return del tipo com.brasajava.model.Persona.
     */
    public Persona getUsuario() {
        return usuario;
    }

    /**
     * Configura el valor de la variable usuario.
     *
     * @param usuario del tipo com.brasajava.model.Persona.
     */
    public void setUsuario(Persona usuario) {
        this.usuario = usuario;
    }

    /**
     * Retorna el valor de la variable busquedaDePersona.
     *
     * @return del tipo com.brasajava.view.tpv.BusquedaDePersona.
     */
    public BusquedaDePersona getBusqueda() {
        return busqueda;
    }

    /**
     * Configura el valor de la variable busquedaDePersona.
     *
     * @param busqueda del tipo com.brasajava.view.tpv.BusquedaDePersona.
     */
    public void setBusqueda(BusquedaDePersona busqueda) {
        this.busqueda = busqueda;
    }

    /**
     * Retorna el valor de la variable grupoList.
     *
     * @return del tipo java.util.List&lt;com.brasajava.model.Grupo&gt;.
     */
    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    /**
     * Configura el valor de la variable grupoList.
     *
     * @param grupoList del tipo
     * java.util.List&lt;com.brasajava.model.Grupo&gt;.
     *
     * Al instanciar la clase TPV crea una lista vacia y asigna a la variable
     * grupoList, o sea, que no hay grupos y si no hay grupos no hay productos,
     * por lo tanto es imprecindible para el funcionamento del tpv virtual. Este
     * metodo se encarga de actualizar el apartado de los grupos
     * automaticamente, sin embargo habrará que hacer click en un grupo para
     * actualizar el apartado de producotos.
     */
    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
        crearGrupos(grupoList);
    }

    /**
     * Actualizará la barra de titulo del tpv con el nombre del usuario logado
     * en la session y el nombre del cliente seleccionado al final de la
     * (flecha)&gt;&gt;---------------------------&gt;
     */
    public void refreshSession() {
        if (session.getUsuario() != null) {
            this.setTitle("TPV - " + session.getUsuario().getNombre());
        }

        if (session.getCliente() != null) {
            this.setTitle(getTitle() + " >>------------------------->  " + session.getCliente().getNombre());
        }
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

    /**
     * Abre en la tabla del tpv la cuenta pasada por parametro y guarda la
     * cuenta que estava abierta anteriormente, en caso de que cumpla los
     * requisitos para ser guardada.
     * <p>
     * Los requisitos son:</p>
     * <ul>
     * <li>No estar cobrada o estar configurada para reabrir</li>
     * <li>tener alguna venta o estar ajustada</li>
     * </ul>
     *
     * @param c del tipo com.brasajava.model.Cuenta.
     */
    public void abrirCuenta(Cuenta c) {
        CajaTableModel model = (CajaTableModel) tabla.getModel();
        guardaCuenta();
        model.setCuenta(c);
        model.fireTableDataChanged();
        reorganizaCuenta();
        lblTotalValue.setText(sumaTotal(c.getVentas()).toString());
        txtCuenta.setText(c.getNombre());
    }

    /**
     * Abre en la tabla del tpv una cuenta nueva y guarda la cuenta que estava
     * abierta anteriomente, se cumple los requisitos para ser guardada.
     */
    public void crearCuenta() {
        guardaCuenta();
        nuevaCuenta();
    }

    /**
     * Retorna un StringBuilder con los datos comunes para la impresion de
     * infoTicket y ticket.
     *
     * @return del tipo java.lang.StringBuilder.
     *
     * <p>
     * Retorna un StringBuilder con subencabezado conteniendo datos de la
     * empresa que son variables constantes y privadas desta clase. nombres de
     * las columnias como CANT DESCRIPCION PRECIO IMPORTE las ventas de la
     * cuenta sacada del model de la tabla de tpv y al final una linea
     * utilizando el metoso stringBuilder.append("\n");
     * </p>
     */
    public StringBuilder prePrint() {
        //TextArea
        CajaTableModel model = (CajaTableModel) tabla.getModel();
        StringBuilder sb = new StringBuilder();
        int start = 0;
        int end = 0;
        int base = 0;
        String str = "";

        sb.append("                                     ");//37
        str = EMPRESA;
        base = 37 - str.length();
        start = base % 2 == 0 ? sb.length() - (37 - (base / 2)) : sb.length() - (37 - ((base - 1) / 2));
        end = start + str.length();
        sb.replace(start, end, str);
        sb.append("\n");

        sb.append("                                     ");//37
        str = DIRECCION;
        base = 37 - str.length();
        start = base % 2 == 0 ? sb.length() - (37 - (base / 2)) : sb.length() - (37 - ((base - 1) / 2));
        end = start + str.length();
        sb.replace(start, end, str);
        sb.append("\n");

        sb.append("                                     ");//37
        str = CODIGO_POSTAL_LOCALIDAD;
        base = 37 - str.length();
        start = base % 2 == 0 ? sb.length() - (37 - (base / 2)) : sb.length() - (37 - ((base - 1) / 2));
        end = start + str.length();
        sb.replace(start, end, str);
        sb.append("\n");

        sb.append("                                     ");//37
        str = TELEFONO + "  -  " + CIF;
        base = 37 - str.length();
        start = base % 2 == 0 ? sb.length() - (37 - (base / 2)) : sb.length() - (37 - ((base - 1) / 2));
        end = start + str.length();
        sb.replace(start, end, str);
        sb.append("\n");
        sb.append("\n");

        sb.append("                                     ");//37
        //Nº factura
        start = sb.length() - 37;
        str = (model.getCuenta().getFactura() != null)? "Nº: " + model.getCuenta().getFactura().getId() : "Nº:";
        end = start + str.length();
        sb.replace(start, end, str);
        
        //fecha
        start = sb.length() - 16;
        str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", applicationLocale.getLocale()));
        end = start + str.length();
        sb.replace(start, end, str);
        sb.append("\n");
        
        sb.append("                                     ");//37
        //unid
        start = sb.length() - 37;
        str = messageSource.getMessage("UNIT", null, applicationLocale.getLocale());
        end = start + str.length();
        sb.replace(start, end, str);
        //Descripcion
        start = sb.length() - 32;
        str = messageSource.getMessage("DESCRIPTION", null, applicationLocale.getLocale());
        end = start + str.length();
        sb.replace(start, end, str);
        //Precio
        str = messageSource.getMessage("PRICE", null, applicationLocale.getLocale());
        end = sb.length() - 8;
        start = end - str.length();
        sb.replace(start, end, str);
        //Total
        str = messageSource.getMessage("SUBTOTAL", null, applicationLocale.getLocale());
        end = sb.length();
        start = end - str.length();
        sb.replace(start, end, str);
        sb.append("\n");
        sb.append("=====================================");
        sb.append("\n");
        for (Venta v : model.getCuenta().getVentas()) {
            sb.append("                                     ");//37
            if (v.getCantidad() < 10) {
                start = sb.length() - 34;
                end = start + 1;
                str = Integer.toString(v.getCantidad());
                sb.replace(start, end, str);
            } else if (v.getCantidad() > 9 && v.getCantidad() < 100) {
                start = sb.length() - 35;
                end = start + 2;
                str = Integer.toString(v.getCantidad());
                sb.replace(start, end, str);
            } else if (v.getCantidad() > 99 && v.getCantidad() < 1000) {
                start = sb.length() - 36;
                end = start + 3;
                str = Integer.toString(v.getCantidad());
                sb.replace(start, end, str);
            } else {
                start = sb.length() - 37;
                end = start + 4;
                str = Integer.toString(v.getCantidad());
                sb.replace(start, end, str);
            }

            start = sb.length() - 32;
            end = start + v.getProducto().getNombre().length();
            str = v.getProducto().getNombre();
            sb.replace(start, end, str);

            switch (v.getProducto().getPrecioMasIva().toString().length()) {
                case 4:
                    start = sb.length() - 12;
                    end = start + v.getProducto().getPrecioMasIva().toString().length();
                    str = v.getProducto().getPrecioMasIva().toString().replace('.', ',');
                    sb.replace(start, end, str);
                    break;
                case 5:
                    start = sb.length() - 13;
                    end = start + v.getProducto().getPrecioMasIva().toString().length();
                    str = v.getProducto().getPrecioMasIva().toString().replace('.', ',');
                    sb.replace(start, end, str);
                    break;
                case 6:
                    start = sb.length() - 14;
                    end = start + v.getProducto().getPrecioMasIva().toString().length();
                    str = v.getProducto().getPrecioMasIva().toString().replace('.', ',');
                    sb.replace(start, end, str);
                    break;
                case 7:
                    start = sb.length() - 15;
                    end = start + v.getProducto().getPrecioMasIva().toString().length();
                    str = v.getProducto().getPrecioMasIva().toString().replace('.', ',');
                    sb.replace(start, end, str);
                    break;
            }

            switch (v.getTotal().toString().length()) {
                case 4:
                    start = sb.length() - 4;
                    end = start + v.getTotal().toString().length();
                    str = v.getTotal().toString().replace('.', ',');
                    sb.replace(start, end, str);
                    break;
                case 5:
                    start = sb.length() - 5;
                    end = start + v.getTotal().toString().length();
                    str = v.getTotal().toString().replace('.', ',');
                    sb.replace(start, end, str);
                    break;
                case 6:
                    start = sb.length() - 6;
                    end = start + v.getTotal().toString().length();
                    str = v.getTotal().toString().replace('.', ',');
                    sb.replace(start, end, str);
                    break;
                case 7:
                    start = sb.length() - 7;
                    end = start + v.getTotal().toString().length();
                    str = v.getTotal().toString().replace('.', ',');
                    sb.replace(start, end, str);
                    break;
            }

            sb.append("\n");
        }
        return sb;
    }
    
    private void infoTicket() {
        JTextArea ta = new JTextArea();
        ta.setFont(new Font("monospaced", 1, 12));
        StringBuilder sb = prePrint();
        int start = 0;
        int end = 0;
        String str = "";
        sb.append("=====================================");
        sb.append("\n");
        sb.append("                                     ");
        //Total
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
            default:
                JOptionPane.showMessageDialog(this, lblTotalValue.getText().length() + "");
                break;
        }
        MessageFormat header = new MessageFormat("TPV - BRASAJAVA SWING");
        MessageFormat footer = new MessageFormat("GRACIAS POR CONFIAR EN BRASAJAVA");
        ta.append(sb.toString());
        try {
            ta.print(header, footer, false, null, null, false);
        } catch (PrinterException ex) {

        }
    }

    private void setWithInternationalization() {
        //label
        lblNombreDeLaCuenta.setText(messageSource.getMessage("label_NameOfTheAccount", null, applicationLocale.getLocale()));
        lblCantidad.setText(messageSource.getMessage("UNIT", null, applicationLocale.getLocale()));
        lblTotal.setText(messageSource.getMessage("TOTAL", null, applicationLocale.getLocale()));
        //button
        btnTicket.setText(messageSource.getMessage("button_Ticket", null, applicationLocale.getLocale()));
        btnCliente.setText(messageSource.getMessage("menu_Client", null, applicationLocale.getLocale()));
        btnUsuario.setText(messageSource.getMessage("menu_User", null, applicationLocale.getLocale()));
        btnBorrar.setText(messageSource.getMessage("button_Delete", null, applicationLocale.getLocale()));
        btnNuevaCuenta.setText(messageSource.getMessage("button_NewAccount", null, applicationLocale.getLocale()));
        btnPagar.setText(messageSource.getMessage("button_Pay", null, applicationLocale.getLocale()));
    }

    //Grupos
    /*
     * Este metodo es el arranque del tpv ya que es el responsable de añadir los
     * grupos con los productos al TPV virtual.
     *
     * @param grupoList del tipo
     * java.util.List&lt;com.brasajava.model.Grupo&gt;.
     */
    private void crearGrupos(List<Grupo> grupoList) {
        grupoButtonList.clear();
        for (Grupo g : grupoList) {
            if (g.isActivo()) {
                JButton button = new JButton(g.getNombre());
                button.setSize(grupoWidth, grupoHeight);
                button.addActionListener(this::grupoButtonAction);
                grupoButtonList.add(button);
                button.setActionCommand(g.getId() + "");
                grupoMap.put(g.getId() + "", g);
                if (g.getImage() != null && !g.getImage().isEmpty()) {
                    Image image = new ImageIcon(getClass().getResource("/images/" + g.getImage())).getImage().getScaledInstance(190, 170, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(image));
                    button.setVerticalTextPosition(AbstractButton.BOTTOM);
                    button.setHorizontalTextPosition(AbstractButton.CENTER);
                }
                button.setToolTipText(g.getNombre() + " " + g.getProductos().size());
            }
        }
        reorganizaGrupo();
    }

    private void grupoButtonAction(ActionEvent e) {
        Grupo g = grupoMap.get(((JButton) e.getSource()).getActionCommand());
        productoButtonList.clear();
        for (Producto p : g.getProductos()) {
            if (p.isActivo()) {
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
    //las cuentas que estan guardadas en la base de datos cuando se añade un producto
    //aún que ya lo haya será una nueva venta.
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
            v = context.getBean(Venta.class);
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
        lblTotalValue.setText(c.getTotal().toString());
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
        cantidad = Integer.parseInt(lblCantidadValue.getText());
        lblCantidadValue.setName("false");
    }

    private void resetCantidad() {
        cantidad = 1;
        lblCantidadValue.setText(cantidad + "");
        lblCantidadValue.setName("false");
    }
    //Cantidades

    //Cuenta
    private void guardaCuenta() {
        Cuenta cuenta = ((CajaTableModel) tabla.getModel()).getCuenta();
        if (cuenta.getVentas().size() > 0 || cuenta.isAjustada()) {
            if (!cuenta.isCobrada() || cuenta.isReabrir()) {
                //para no dar fallo confundindo cuentas ya que todas las nuevas tienen el id = 0 
                cuentasMap.remove(cuenta);

                //hacer map de las cuentas con nombre es peligroso pero el metodo equal y hashcode analizan solamente el id.
                cuentaMap.put(cuenta.getNombre(), cuenta);

                JButton b = new JButton(cuenta.getNombre());
                b.setActionCommand(cuenta.getNombre());
                b.setSize(cuentaWidth, cuentaHeight);
                b.addActionListener(this::cuentaButtonAction);
                if (cuenta.isTicket()) {
                    b.setBackground(Color.green);
                }
                if (cuenta.isReabrir()) {
                    b.setBackground(Color.red);
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

    private void borrarCuenta() {
        Cuenta c = ((CajaTableModel) tabla.getModel()).getCuenta();
        int jop = JOptionPane.showConfirmDialog(panelProducto, messageSource.getMessage("message_DoYouWantDeleteThisAccount", null, applicationLocale.getLocale()) + " " + c.getNombre() + "?", messageSource.getMessage("message_Confimation", null, applicationLocale.getLocale()), JOptionPane.YES_OPTION);
        if (jop == JOptionPane.YES_OPTION) {
            cuentasMap.remove(c);
            nuevaCuenta();
            c = null;
        }
    }

    private void cambiarNombreDeLaCuenta(String nombre) {
        if (!cuentaMap.containsKey(nombre)) {
            Cuenta cuenta = ((CajaTableModel) tabla.getModel()).getCuenta();
            cuenta.setNombre(nombre);
            cuenta.setAjustada(true);
        } else {
            JOptionPane.showMessageDialog(this, messageSource.getMessage("message_ThisNameHasBeenUsing", null, applicationLocale.getLocale()));
        }
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

    private void fireDataChage(Venta v) {
        CajaTableModel model = (CajaTableModel) tabla.getModel();
        model.fireTableDataChanged();
        sumaTotal(model.getCuenta().getVentas());
        int index = model.getCuenta().getVentas().indexOf(v);
        tabla.setRowSelectionInterval(index, index);
    }

    private void tecladoVirtualAction(java.awt.event.ActionEvent evt) {
        JButton b = (JButton) evt.getSource();
        if (lblCantidadValue.getName().equals("true")) {
            lblCantidadValue.setText(lblCantidadValue.getText() + b.getText());
        } else if (!b.getText().equals("0")) {
            lblCantidadValue.setText(b.getText());
            lblCantidadValue.setName("true");
        }
    }

    private TableModel getModel() {
        return context.getBean(CajaTableModel.class);
    }

    private void setColumnModel() {
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    tabla.getColumnModel().getColumn(0).setPreferredWidth(5);
                    break;
                case 1:
                    tabla.getColumnModel().getColumn(1).setPreferredWidth(180);
                    break;
                case 2:
                    tabla.getColumnModel().getColumn(2).setPreferredWidth(30);
                    break;
                case 3:
                    tabla.getColumnModel().getColumn(3).setPreferredWidth(30);
                    break;
            }
        }
    }

    private void initComponents() {

        jButton32 = new javax.swing.JButton();
        panelToolBar = new javax.swing.JPanel();
        btnCliente = new javax.swing.JButton();
        btnUsuario = new javax.swing.JButton();
        btnTicktes = new javax.swing.JButton();
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
        jPanel2 = new javax.swing.JPanel();
        lblNombreDeLaCuenta = new javax.swing.JLabel();
        txtCuenta = new javax.swing.JTextField();
        lblCantidad = new javax.swing.JLabel();
        lblCantidadValue = new javax.swing.JLabel();
        btnTicket = new javax.swing.JButton();
        panelPrint = new javax.swing.JPanel();
        scrollTabla = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();
        lblTotalValue = new javax.swing.JLabel();
        scrollProducto = new javax.swing.JScrollPane();
        panelProducto = new javax.swing.JPanel();

        jButton32.setText("jButton32");

        setTitle("TPV - ");
        setExtendedState(6);
        setIconImage(new ImageIcon(this.getClass().getResource("/images/icon.png")).getImage());

        panelToolBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnCliente.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnCliente.setText("Cliente");
        btnCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteActionPerformed(evt);
            }
        });

        btnUsuario.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnUsuario.setText("Usuario");
        btnUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuarioActionPerformed(evt);
            }
        });

        btnTicktes.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnTicktes.setText("Ticktes");
        btnTicktes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTicktesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelToolBarLayout = new javax.swing.GroupLayout(panelToolBar);
        panelToolBar.setLayout(panelToolBarLayout);
        panelToolBarLayout.setHorizontalGroup(
            panelToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelToolBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTicktes)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelToolBarLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCliente, btnTicktes, btnUsuario});

        panelToolBarLayout.setVerticalGroup(
            panelToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelToolBarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCliente))
                    .addComponent(btnTicktes))
                .addContainerGap())
        );

        panelToolBarLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCliente, btnTicktes, btnUsuario});

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

        javax.swing.GroupLayout panelTecladoLayout = new javax.swing.GroupLayout(panelTeclado);
        panelTeclado.setLayout(panelTecladoLayout);
        panelTecladoLayout.setHorizontalGroup(
            panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTecladoLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTecladoLayout.createSequentialGroup()
                        .addComponent(btn4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelTecladoLayout.createSequentialGroup()
                        .addComponent(btn7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMenos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelTecladoLayout.createSequentialGroup()
                        .addGroup(panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelTecladoLayout.createSequentialGroup()
                                .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCE, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
                            .addGroup(panelTecladoLayout.createSequentialGroup()
                                .addGroup(panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnPagar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn0, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnBorrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnNuevaCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        panelTecladoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9});

        panelTecladoLayout.setVerticalGroup(
            panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTecladoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblNombreDeLaCuenta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNombreDeLaCuenta.setText("CUENTA");

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

        lblCantidad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCantidad.setText("UNID.");

        lblCantidadValue.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblCantidadValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCantidadValue.setText("1");
        lblCantidadValue.setName("false"); // NOI18N

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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(lblNombreDeLaCuenta)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblCantidadValue, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lblCantidad)
                        .addGap(27, 27, 27)))
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
                            .addComponent(lblNombreDeLaCuenta)
                            .addComponent(lblCantidad))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCuenta)
                            .addComponent(lblCantidadValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        panelPrint.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tabla.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabla.setModel(getModel());
        tabla.setRowHeight(50);
        scrollTabla.setViewportView(tabla);

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTotal.setText("TOTAL:");

        lblTotalValue.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTotalValue.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalValue.setText("0,00");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20)
                .addComponent(lblTotalValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTotalValue, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout panelPrintLayout = new javax.swing.GroupLayout(panelPrint);
        panelPrint.setLayout(panelPrintLayout);
        panelPrintLayout.setHorizontalGroup(
            panelPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollTabla, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelPrintLayout.setVerticalGroup(
            panelPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrintLayout.createSequentialGroup()
                .addComponent(scrollTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout panelCajaLayout = new javax.swing.GroupLayout(panelCaja);
        panelCaja.setLayout(panelCajaLayout);
        panelCajaLayout.setHorizontalGroup(
            panelCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCajaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCajaLayout.createSequentialGroup()
                        .addGroup(panelCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelTeclado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 1, Short.MAX_VALUE))
                    .addComponent(panelPrint, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelCajaLayout.setVerticalGroup(
            panelCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCajaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTeclado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelProductoLayout = new javax.swing.GroupLayout(panelProducto);
        panelProducto.setLayout(panelProductoLayout);
        panelProductoLayout.setHorizontalGroup(
            panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 424, Short.MAX_VALUE)
        );
        panelProductoLayout.setVerticalGroup(
            panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 993, Short.MAX_VALUE)
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
                        .addComponent(scrollProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
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
        CajaTableModel model = (CajaTableModel) tabla.getModel();
        model.getCuenta().setTicket(true);
        infoTicket();
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
        //Crear con spring pasar todo para la session
        Cuenta c = sumarCuentaParaPagar();
        if (c.getFactura() == null) {
            session.setCuenta(c);
        } else {
            session.setFactura(c.getFactura());
        }
        Pagar pagar = context.getBean(Pagar.class);
        pagar.setLocationRelativeTo(null);
        pagar.setVisible(true);
        pagar = null;
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
            int cantidad = v.getCantidad() - Integer.parseInt(lblCantidadValue.getText());
            int confirmacion = -3;
            if (cantidad < 1) {
                confirmacion = JOptionPane.showConfirmDialog(this, messageSource.getMessage("message_DoYouWantDeleteThisArticle", null, applicationLocale.getLocale()));
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
                    ///
                    lblTotalValue.setText(sumaTotal(model.getCuenta().getVentas()).toString());
                }
            } else {
                v.setVenta(cantidad, v.getProducto());
                fireDataChage(v);
                lblTotalValue.setText(sumaTotal(model.getCuenta().getVentas()).toString());
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
            v.setVenta(v.getCantidad() + Integer.parseInt(lblCantidadValue.getText()), v.getProducto());
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

    private void btnClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteActionPerformed
        if (busqueda == null) {
            busqueda = context.getBean(BusquedaDePersona.class);
        }
        busqueda.setCliente(true);
        List<Persona> lp = new ArrayList<>();
        for (Persona per : context.getBean(ServicioCliente.class).findAll()) {
            lp.add(per);
        }
        busqueda.setLista(lp);
        busqueda.setLocationRelativeTo(null);
        busqueda.setVisible(true);
    }//GEN-LAST:event_btnClienteActionPerformed

    private void btnUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuarioActionPerformed
        if (busqueda == null) {
            busqueda = context.getBean(BusquedaDePersona.class);
        }
        busqueda.setCliente(false);
        List<Persona> lp = new ArrayList<>();
        for (Persona per : context.getBean(ServicioUsuario.class).findAll()) {
            lp.add(per);
        }
        busqueda.setLista(lp);
        busqueda.setLocationRelativeTo(null);
        busqueda.setVisible(true);
    }//GEN-LAST:event_btnUsuarioActionPerformed

    private void btnTicktesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTicktesActionPerformed
        BuscaFactura bf = context.getBean(BuscaFactura.class);
        List<Factura> lf = new ArrayList<>();
        for (Factura f : context.getBean(ServicioFactura.class).findAll()) {
            lf.add(f);
        }
        bf.setLista(lf);
        bf.setLocationRelativeTo(null);
        bf.setVisible(true);
    }//GEN-LAST:event_btnTicktesActionPerformed

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
    private javax.swing.JButton btnCliente;
    private javax.swing.JButton btnMas;
    private javax.swing.JButton btnMenos;
    private javax.swing.JButton btnNuevaCuenta;
    private javax.swing.JButton btnPagar;
    private javax.swing.JButton btnTicket;
    private javax.swing.JButton btnTicktes;
    private javax.swing.JButton btnUsuario;
    private javax.swing.JButton jButton32;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblCantidadValue;
    private javax.swing.JLabel lblNombreDeLaCuenta;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalValue;
    private javax.swing.JPanel panelCaja;
    private javax.swing.JPanel panelCuenta;
    private javax.swing.JPanel panelGrupo;
    private javax.swing.JPanel panelPrint;
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
}
