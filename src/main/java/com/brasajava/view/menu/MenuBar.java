package com.brasajava.view.menu;

import com.brasajava.model.Cliente;
import com.brasajava.model.Grupo;
import com.brasajava.model.Persona;
import com.brasajava.model.Producto;
import com.brasajava.model.Usuario;
import com.brasajava.service.ServicioCliente;
import com.brasajava.service.ServicioGrupo;
import com.brasajava.service.ServicioUsuario;
import com.brasajava.util.interfaces.Internationalizable;
import com.brasajava.util.ApplicationLocale;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractButton;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import org.springframework.context.MessageSource;
import com.brasajava.util.interfaces.Command;
import com.brasajava.view.menu.command.DesktopController;
import com.brasajava.view.persona.command.ShowPersonaCommand;
import com.brasajava.view.principal.MainFrame;
import com.brasajava.view.producto.ProductoGrupo;
import com.brasajava.view.tpv.TPV;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Locale;
import javax.swing.JFrame;
import org.springframework.context.ApplicationContext;

/**
 * Esta Classe extends JMenuBar y es responsable por la parte visual y lógica de
 * la barra de menu del frame principal.
 *
 * @author Ricardo Maximino
 */
public class MenuBar extends JMenuBar implements Internationalizable {

    private ApplicationLocale applicationLocale;//
    private MessageSource messageSource;//
    private final List<AbstractButton> menuAndItemList;
    private final ApplicationContext context;

    //GRUPOS
    public static final String GRUPO_CLIENTE = "CLIENTE";
    public static final String GRUPO_VENTANAS = "VENTANAS";
    public static final String GRUPO_USUARIO = "USUARIO";
    public static final String GRUPO_LENGUAS = "LENGUAS";
    public static final String GRUPO_CONFIGURACIONES = "CONFIGURACIONES";
    public static final String GRUPO_TPV = "TPV";
    public static final String GRUPO_PRODUCTO_GRUPO = "PRODUCTO/GRUPO";
    //ACCIONES
    private static final String ACCION_BUSCAR = "menuItemBuscarCommand";
    private static final String ACCION_LISTAR = "menuItemListarCommand";
    private static final String ACCION_ANADIR = "menuItemAnadirCommand";
    private static final String ACCION_CERRAR = "menuItemCerrarCommand";
    private static final String ACCION_CERRAR_TODOS = "menuItemCerrarTodoCommand";
    private static final String ACCION_CASCADA = "menuItemCascadaCommand";
    private static final String ACCION_PORTUGUES = "idiomaPortuguesCommand";
    private static final String ACCION_INGLES = "idiomaInglesCommand";
    private static final String ACCION_ESPANOL = "idiomaEspanolCommand";
    private static final String ACCION_ABRIR_TPV = "abrirTPV";
    private static final String ACCION_ABRIR_PRODUCTO_GRUPO = "abrirProductoGrupo";
    private static final String ACCION_NULLA = "NULLA";

    /**
     * Este es el único construtor para instanciar un objeto desta classe.
     *
     * @param ms del tipo org.springframework.context.MessageSource.
     * @param applicationLocale del tipo com.brasajava.util.ApplicationLocale.
     * @param context del tipo org.springframework.context.ApplicationContext.
     */
    public MenuBar(MessageSource ms, ApplicationLocale applicationLocale, ApplicationContext context) {
        this.context = context;
        menuAndItemList = new ArrayList<>();
        this.applicationLocale = applicationLocale;
        this.messageSource = ms;
        this.crearMenus();
    }

    /**
     * Retorna el valor de la variable global applicationLocale.
     *
     * @return del tipo com.brasajva.util.ApplicationLocale.
     */
    public ApplicationLocale getApplicationLocale() {
        return applicationLocale;
    }

    /**
     * Configura el valor de la variable global applicationLocale.
     *
     * @param applicationLocale del tipo com.brasajva.util.ApplicationLocale.
     */
    public void setApplicationLocale(ApplicationLocale applicationLocale) {
        this.applicationLocale = applicationLocale;
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
     * @param ms del tipo org.springframework.context.MessageSource.
     */
    public void setMessageSource(MessageSource ms) {
        this.messageSource = ms;
    }

    /**
     * Metodo responsable de reecribir todos lo menus utilizando el locale de la
     * classe com.brasajava.util.ApplicationLocale.
     */
    @Override
    public void refreshLanguage() {
        for (AbstractButton abs : menuAndItemList) {
            abs.setText(messageSource.getMessage(abs.getName(), null, applicationLocale.getLocale()));
        }
    }

    private JMenu createMenu(String messageSourceKey, String grupo, String accion) {
        MiMenu menu = new MiMenu(messageSourceKey, grupo, accion);
        menu.setText(messageSource.getMessage(menu.getName(), null, applicationLocale.getLocale()));
        menuAndItemList.add(menu);
        return menu;
    }

    private JMenuItem createMenuItem(String name, String grupo, String accion) {
        MiMenuItem menuItem = new MiMenuItem(name, grupo, accion);
        System.out.println("ActionI = " + menuItem.getActionCommand());
        menuItem.setText(messageSource.getMessage(menuItem.getName(), null, applicationLocale.getLocale()));
        menuAndItemList.add(menuItem);
        return menuItem;
    }

    private void crearMenus() {
        //cliente
        JMenu cliente = createMenu("menu_Client", GRUPO_CLIENTE, ACCION_NULLA);
        JMenuItem buscaClientes = createMenuItem("menuItem_SearchClient", GRUPO_CLIENTE, ACCION_BUSCAR);
        buscaClientes.addActionListener(this::buscaPersona);
        cliente.add(buscaClientes);
        JMenuItem listarTClientes = createMenuItem("menuItem_ListAll", GRUPO_CLIENTE, ACCION_LISTAR);
        listarTClientes.addActionListener(this::listAll);
        cliente.add(listarTClientes);
        JMenuItem nuevoCliente = createMenuItem("menuItem_NewClient", GRUPO_CLIENTE, ACCION_ANADIR);
        nuevoCliente.addActionListener(this::nuevaPersona);
        cliente.add(nuevoCliente);
        this.add(cliente);

        //ventanas
        JMenu ventanas = createMenu("menu_Windows", GRUPO_VENTANAS, ACCION_NULLA);
        JMenuItem cerrar = createMenuItem("menuItem_Close", GRUPO_VENTANAS, ACCION_CERRAR);
        cerrar.addActionListener(this::desktop);
        ventanas.add(cerrar);
        JMenuItem cerrarTodo = createMenuItem("menuItem_CloseAll", GRUPO_VENTANAS, ACCION_CERRAR_TODOS);
        cerrarTodo.addActionListener(this::desktop);
        ventanas.add(cerrarTodo);
        ventanas.addSeparator();
        JMenuItem cascada = createMenuItem("menuItem_Waterfall", GRUPO_VENTANAS, ACCION_CASCADA);
        cascada.addActionListener(this::desktop);
        ventanas.add(cascada);
        this.add(ventanas);

        //usuario
        JMenu usuario = createMenu("menu_User", GRUPO_USUARIO, ACCION_NULLA);
        JMenuItem buscaUsuarios = createMenuItem("menuItem_SearchUser", GRUPO_USUARIO, ACCION_BUSCAR);
        buscaUsuarios.addActionListener(this::buscaPersona);
        usuario.add(buscaUsuarios);
        JMenuItem listaTUsuarios = createMenuItem("menuItem_ListAll", GRUPO_USUARIO, ACCION_LISTAR);
        listaTUsuarios.addActionListener(this::listAll);
        usuario.add(listaTUsuarios);
        JMenuItem nuevoUsuario = createMenuItem("menuItem_NewUser", GRUPO_USUARIO, ACCION_ANADIR);
        nuevoUsuario.addActionListener(this::nuevaPersona);
        usuario.add(nuevoUsuario);
        this.add(usuario);

        //lenguas
        JMenu lengua = createMenu("menu_Languages", GRUPO_LENGUAS, ACCION_NULLA);
        JMenuItem portugues = createMenuItem("menuItem_Portugues", GRUPO_LENGUAS, ACCION_PORTUGUES);
        portugues.addActionListener(this::lenguas);
        lengua.add(portugues);
        JMenuItem english = createMenuItem("menuItem_English", GRUPO_LENGUAS, ACCION_INGLES);
        english.addActionListener(this::lenguas);
        lengua.add(english);
        JMenuItem espanol = createMenuItem("menuItem_Spanish", GRUPO_LENGUAS, ACCION_ESPANOL);
        espanol.addActionListener(this::lenguas);
        lengua.add(espanol);
        this.add(lengua);

        //configuraciones
        JMenu configuraciones = createMenu("menu_Settings", GRUPO_CONFIGURACIONES, ACCION_NULLA);
        this.add(configuraciones);
        
        //tpv
        JMenu tpv = createMenu("menu_TPV", GRUPO_TPV, ACCION_NULLA);
        JMenuItem virtual = createMenuItem("menuItem_virtual", GRUPO_TPV, ACCION_ABRIR_TPV);
        virtual.addActionListener(this::tpvVirtual);
        tpv.add(virtual);
        this.add(tpv);
        
        //Producto/Grupo
        JMenu productoGrupo = createMenu("menu_ProductoGrupo", GRUPO_PRODUCTO_GRUPO, ACCION_NULLA);
        JMenuItem productoGrupoItem = createMenuItem("menu_ProductoGrupo", GRUPO_PRODUCTO_GRUPO, ACCION_ABRIR_PRODUCTO_GRUPO);
        productoGrupo.add(productoGrupoItem);
        productoGrupoItem.addActionListener(this::productoGrupo);
        this.add(productoGrupo);
    }

    private void tpvVirtual(ActionEvent e){
        TPV tpv = context.getBean(TPV.class);
        List<Grupo> grupoList = new ArrayList<>();
        Iterator<Grupo> iterator = context.getBean(ServicioGrupo.class).findAll().iterator();
        while(iterator.hasNext()){
            grupoList.add(iterator.next());
        }
        tpv.setUsuario(null);
        tpv.setCliente(null);
        tpv.setGrupoList(grupoList);
        tpv.setVisible(true);
        tpv.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        tpv.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    private void productoGrupo(ActionEvent e){
        ProductoGrupo p = context.getBean(ProductoGrupo.class);
        Producto pro1 = new Producto();
        pro1.setId(1);
        pro1.setNombre("Hit Uva");
        pro1.setActivo(true);
        pro1.setAlmacen(200);
        pro1.setCusto(new BigDecimal("1.00"));
        pro1.setDescripcion("This is the description of Hit Uva");
        pro1.setIva(10);
        pro1.setMargen(100);
        pro1.setPrecioSinIva(new BigDecimal("2.00"));
        pro1.setPrecioMasIva(new BigDecimal("2.20"));
        pro1.setImage("hitUva.png");
        Grupo g = new Grupo();
        g.setNombre("Mi Grupo");
        pro1.getGrupos().add(g);
        p.setProducto(pro1);
        context.getBean(MainFrame.class).getDesktopPane().add(p);
        p.setVisible(true);
    }
    
    private void buscaPersona(ActionEvent e) {
        MiMenuItem menuItem = (MiMenuItem) e.getSource();
        String grupo = menuItem.getGroup();
        context.getBean(menuItem.getCommandActionName(), Command.class).execute(new Object[]{menuItem});
    }
    private void listAll(ActionEvent e){
        MiMenuItem menu = (MiMenuItem) e.getSource();
        ShowPersonaCommand showPersonaCommand = context.getBean(ShowPersonaCommand.class);
        List<Persona> lista = new ArrayList<>();
        
        
        //CLIENTE
        if (menu.getGroup().equals(MenuBar.GRUPO_CLIENTE)) {
            ServicioCliente servicio = context.getBean(ServicioCliente.class);
            for(Cliente cli : servicio.findAll()){
                lista.add(cli);
            }
            showPersonaCommand.show(lista,Cliente.class,"listaPersona_ClientListAll");
        } 
        //USUARIO
        else if (menu.getGroup().equals(MenuBar.GRUPO_USUARIO)){
            ServicioUsuario servicio = context.getBean(ServicioUsuario.class);
            for(Usuario usu : servicio.findAll()){
                lista.add(usu);
            }
            showPersonaCommand.show(lista,Usuario.class,"listaPersona_UserListAll");
        }
    }
    private void nuevaPersona(ActionEvent e){
        MiMenuItem menu = (MiMenuItem) e.getSource();
        ShowPersonaCommand showPersonaCommand = context.getBean(ShowPersonaCommand.class);
        Persona persona;
        
        //CLIENTE
        if (menu.getGroup().equals(MenuBar.GRUPO_CLIENTE)) {
            persona = context.getBean("newCliente", Persona.class);           
            showPersonaCommand.show(persona,Cliente.class,"framePersona_NewClient");
        } 
        //USUARIO
        else if (menu.getGroup().equals(MenuBar.GRUPO_USUARIO)){
            persona = context.getBean("newUsuario", Persona.class);
            showPersonaCommand.show(persona,Usuario.class,"framePersona_NewUser");
        }
    }

    private void desktop(ActionEvent e){
         MiMenuItem menuItem = (MiMenuItem) e.getSource();
         DesktopController desktopController = context.getBean(DesktopController.class);
        switch (menuItem.getCommandActionName()) {
            case ACCION_CERRAR:
                desktopController.cerrarSelectedInternalFrame();
                break;
            case ACCION_CERRAR_TODOS:
                desktopController.cerrarTodosInternalFrames();
                break;
            case ACCION_CASCADA:
                desktopController.cascada();
                break;
        }
    }
    private void lenguas(ActionEvent e) {
        MiMenuItem menuItem = (MiMenuItem) e.getSource();
        switch (menuItem.getCommandActionName()) {
            case ACCION_PORTUGUES:
                applicationLocale.setLocale(new Locale("pt","BR"));
                break;
            case ACCION_INGLES:
                applicationLocale.setLocale(new Locale("en","US"));
                break;
            case ACCION_ESPANOL:
                applicationLocale.setLocale(new Locale("es","ES"));
                break;
        }
    }
}
