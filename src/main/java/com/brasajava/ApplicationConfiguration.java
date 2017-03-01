package com.brasajava;

import com.brasajava.model.Cliente;
import com.brasajava.util.ApplicationLocale;
import com.brasajava.view.busqueda.FrameBusqueda;
import com.brasajava.view.persona.FramePersona;
import com.brasajava.view.menu.MenuBar;
import com.brasajava.view.principal.MainFrame;
import java.awt.Dimension;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.brasajava.model.Direccion;
import com.brasajava.model.Factura;
import com.brasajava.model.Persona;
import com.brasajava.model.Usuario;
import com.brasajava.model.Venta;
import com.brasajava.util.Session;
import com.brasajava.view.persona.ListaPersona;
import com.brasajava.view.persona.tablemodel.MiTableModel;
import com.brasajava.view.persona.tablemodel.MiTableModelNifNombre;
import com.brasajava.view.persona.tablemodel.TableModelAll;
import com.brasajava.view.producto.BuscarGrupo;
import com.brasajava.view.producto.BuscarGrupoI;
import com.brasajava.view.producto.BuscarProducto;
import com.brasajava.view.producto.BuscarProductoI;
import com.brasajava.view.producto.GrupoTableModel;
import com.brasajava.view.producto.GrupoView;
import com.brasajava.view.producto.ProductoTableModel;
import com.brasajava.view.producto.ProductoView;
import com.brasajava.view.tpv.BuscaFactura;
import com.brasajava.view.tpv.BusquedaDePersona;
import com.brasajava.view.tpv.CajaTableModel;
import com.brasajava.view.tpv.FacturaTableModel;
import com.brasajava.view.tpv.Pagar;
import com.brasajava.view.tpv.TPV;
import java.time.LocalDate;
import org.springframework.context.annotation.Scope;

/**
 * Esta clase representa los beans en los antiguo xml o bien spring annotations. 
 * @author Ricardo Maximino
 */
@Configuration
public class ApplicationConfiguration {
    
    @Bean
    public Session session(){
        return new Session();
    }

    @Bean
    public MainFrame mainFrame(JMenuBar menuBar) {
        MainFrame v = new MainFrame();
        ((JFrame) v).setJMenuBar(menuBar);
        ((JFrame) v).setVisible(true);
        return v;
    }

    @Bean
    public JMenuBar menuBar(MessageSource ms, ApplicationLocale applicationLocale,ApplicationContext context) {
        MenuBar mb = new MenuBar(ms, applicationLocale, context);
        return mb;
    }

    @Bean
    public ApplicationLocale applicationLocale(ApplicationContext context){
        ApplicationLocale applicationLocale = new ApplicationLocale(new Locale("es","ES"),new Locale("pt","BR"),context);
        return applicationLocale;
    }
    
    
    @Bean
    public FrameBusqueda frameBusqueda(ApplicationLocale applicationLocale, MessageSource messageSource,ApplicationContext context){
        FrameBusqueda frame = new FrameBusqueda(messageSource,applicationLocale,context);        
        return frame;
    }
    
  
    @Bean
    @Scope("prototype")
    public FramePersona frameCliente(MessageSource messageSource,ApplicationLocale applicationLocale,ApplicationContext context){
        FramePersona frame = new FramePersona(messageSource,applicationLocale,context);
        frame.setVisible(false);
        frame.setPreferredSize(new Dimension(500, 500));
        frame.setName("null");
        return frame;
    }
    
    
    @Bean
    @Scope("prototype")
    public Persona newCliente(){
        Persona cliente = new Cliente();
        cliente.setFechaNacimiento(LocalDate.now());
        cliente.setFechaPrimeraAlta(LocalDate.now());
        cliente.setActivo(true);
        cliente.setDirecion(new Direccion());
        return cliente;
    }
    @Bean
    @Scope("prototype")
    public Persona newUsuario(){
        Persona usuario = new Usuario();
        usuario.setFechaNacimiento(LocalDate.now());
        usuario.setFechaPrimeraAlta(LocalDate.now());
        usuario.setActivo(true);
        usuario.setDirecion(new Direccion());
        return usuario;
    }
    
    
    @Bean
    @Scope("prototype")
    public MiTableModel tableModelTodos(MessageSource messageSource,ApplicationLocale applicationLocale){
        return new TableModelAll(messageSource,applicationLocale);
    }
    
    @Bean
    @Scope("prototype")
    public ListaPersona listaPersona(ApplicationContext context){
        ListaPersona listaPersona = new ListaPersona(context);
        listaPersona.setName("null");
        return listaPersona;
    }
    
    @Bean
    @Scope("prototype")
    public MiTableModel tableModelNifNombre(MessageSource messageSource,ApplicationLocale applicationLocale){
        return new MiTableModelNifNombre(messageSource, applicationLocale);
    }
    
    @Bean
    @Scope("prototype")
    public BusquedaDePersona busquedaDePersona(ApplicationContext context, TPV tpv){
        return new BusquedaDePersona(tpv, context);
    }
    
    @Bean
    @Scope("prototype")
    public BuscaFactura buscaFactura(ApplicationContext context, TPV tpv){
        return new BuscaFactura(tpv, context);
    }
    
    @Bean 
    public TPV tpv(ApplicationContext context,Session session){
        return new TPV(context, session);
    }
    
    @Bean
    public CajaTableModel cajaTableModel(MessageSource messageSource, ApplicationLocale applicationLocale){
        return new CajaTableModel(messageSource,applicationLocale);
    }
    
    @Bean
    @Scope("prototype")
    public Pagar pagar(ApplicationContext context, TPV tpv){
        return new Pagar(tpv, context);
    }
    
    @Bean
    @Scope("prototype")
    public Factura factura(Session session){
        Factura f = new Factura();
        f.setCliente(session.getCliente());
        f.setUsuario(session.getUsuario());
        return f;
    }
    
    @Bean
    @Scope("prototype")
    public Venta venta(Session session){
        Venta venta = new Venta();
        venta.setUsuario(session.getUsuario());
        venta.setCliente(session.getCliente());
        return venta;
    }
    
    @Bean
    @Scope("prototype")
    public ProductoView productoView(ApplicationContext context){
        ProductoView producto = new ProductoView(context);
        return producto;
    }
    
    @Bean
    @Scope("prototype")
    public GrupoView grupoView(ApplicationContext context){
        GrupoView grupo = new GrupoView(context);
        return grupo;
    }
    
    @Bean
    @Scope("prototype")
    public BuscarProductoI buscarProducto(MainFrame main,ApplicationContext context){
        BuscarProductoI lista = new BuscarProductoI(context);
        main.getDesktopPane().add(lista);
        return lista;
    }
    
    @Bean
    @Scope("prototype")
    public BuscarProducto buscarProductoParaAñadir(MainFrame main,ApplicationContext context){
        BuscarProducto lista = new BuscarProducto(main,context);
        lista.setLocationRelativeTo(null);
        return lista;
    }
   
    @Bean
    @Scope("prototype")
    public BuscarGrupoI buscarGrupo(MainFrame main,ApplicationContext context){
        BuscarGrupoI lista = new BuscarGrupoI(context);
        main.getDesktopPane().add(lista);
        return lista;
    }
    @Bean
    @Scope("prototype")
    public BuscarGrupo buscarGrupoParaAñadir(MainFrame main,ApplicationContext context){
        BuscarGrupo lista = new BuscarGrupo(main,context);
        lista.setLocationRelativeTo(null);
        return lista;
    }
    
    @Bean
    @Scope("prototype")
    public ProductoTableModel productoTableModel(MessageSource messageSouce,ApplicationLocale applicationLocale){
        return new ProductoTableModel(messageSouce,applicationLocale);
    }
    
    @Bean
    @Scope("prototype")
    public FacturaTableModel facturaTableModel(MessageSource messageSouce,ApplicationLocale applicationLocale){
        return new FacturaTableModel(messageSouce,applicationLocale);
    }
    
    @Bean
    @Scope("prototype")
    public GrupoTableModel grupoTableModel(MessageSource messageSouce,ApplicationLocale applicationLocale){
        return new GrupoTableModel(messageSouce,applicationLocale);
    }
}
