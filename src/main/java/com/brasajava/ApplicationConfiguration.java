package com.brasajava;

import com.brasajava.model.Cliente;
import com.brasajava.util.ApplicationLocale;
import com.brasajava.view.persona.FrameBusqueda;
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
import com.brasajava.util.interfaces.MiTableModel;
import com.brasajava.view.persona.MiTableModelNifNombre;
import com.brasajava.view.persona.TableModelAll;
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
    
    /**
     * String utiliza este metodo para crear una instancia de la clase com.brasajava.util.Session.
     * @return del tipo com.brasajava.util.Session.
     * <p>
     * La clase Session es donde se encontraran todas las referencia que deven 
     * estar disponibles en una session.
     * </p>
     */
    @Bean
    public Session session(){
        return new Session();
    }

    /**
     * String utiliza este metodo para crear una instancia de la clase com.brasajava.view.principal.MainFrame.
     * @param menuBar del tipo javax.swing.JMenuBar.
     * @return del tipo com.brasajava.view.principal.MainFrame
     */
    @Bean
    public MainFrame mainFrame(JMenuBar menuBar) {
        MainFrame v = new MainFrame();
        ((JFrame) v).setJMenuBar(menuBar);
        ((JFrame) v).setVisible(true);
        return v;
    }

    /**
     * String utiliza este metodo para crear una instancia de la clase javax.swing.JMenuBar.
     * @param ms del tipo org.springframework.context.MessageSource.
     * @param applicationLocale del tipo com.brasajava.util.ApplicationLocale.
     * @param context del tipo org.springframework.context.ApplicationContext.
     * @return del tipo javax.swing.JMenuBar.
     */
    @Bean
    public JMenuBar menuBar(MessageSource ms, ApplicationLocale applicationLocale,ApplicationContext context) {
        MenuBar mb = new MenuBar(ms, applicationLocale, context);
        return mb;
    }

    /**
     * String utiliza este metodo para crear una instancia de la clase com.brasajava.util.ApplicationLocale.
     * @param context del tipo org.springframework.context.ApplicationLocale.
     * @return del tipo com.brasajava.util.ApplicationLocale.
     */
    @Bean
    public ApplicationLocale applicationLocale(ApplicationContext context){
        ApplicationLocale applicationLocale = new ApplicationLocale(new Locale("es","ES"),new Locale("pt","BR"),context);
        return applicationLocale;
    }
    
    /**
     * String utiliza este metodo para crear una instancia de la clase com.brasajava.view.persona.FrameBusqueda.
     * @param applicationLocale del tipo com.brasajava.util.ApplicationLocale.
     * @param messageSource del tipo org.springframework.context.MessageSource.
     * @param context del tipo org.springframework.context.ApplicationContext.
     * @return del tipo com.brasajava.view.persona.FrameBusqueda.
     */
    @Bean
    public FrameBusqueda frameBusqueda(ApplicationLocale applicationLocale, MessageSource messageSource,ApplicationContext context){
        FrameBusqueda frame = new FrameBusqueda(messageSource,applicationLocale,context);        
        return frame;
    }
    
  
    /**
     * String utiliza este metodo para crear una instancia de la clase com.brasajava.view.persona.FramePersona.
     * @param messageSource del tipo org.springframework.context.MessageSource.
     * @param applicationLocale del tipo com.brasajava.util.ApplicationLocale.
     * @param context del tipo org.springframework.context.ApplicationContext.
     * @return del tipo com.brasajava.view.persona.FramePersona.
     */
    @Bean
    @Scope("prototype")
    public FramePersona frameCliente(MessageSource messageSource,ApplicationLocale applicationLocale,ApplicationContext context){
        FramePersona frame = new FramePersona(messageSource,applicationLocale,context);
        frame.setVisible(false);
        frame.setPreferredSize(new Dimension(500, 500));
        frame.setName("null");
        return frame;
    }
    
    /**
     * String utiliza este metodo para crear una instancia de la clase com.brasajava.model.Persona.
     * @return del tipo com.brasajava.model.Persona.
     * <p>
     * Este metodo crea y pre configura un cliente para evitar NullPointException.
     * </p>
     */
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
    
    /**
     * String utiliza este metodo para crear una instancia de la clase com.brasajava.model.Persona.
     * @return del tipo com.brasajava.model.Persona.
     * <p>
     * Este metodo crea y pre configura un cliente para evitar NullPointException.
     * </p>
     */
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
    
    /**
     * String utiliza este metodo para crear una instancia de la clase 
     * com.brasajava.view.persona.TableModelAll.
     * @param messageSource del tipo org.springframework.context.MessageSource.
     * @param applicationLocale del tipo com.brasajava.util.ApplicationLocale.
     * @return del tipo com.brasajava.view.persona.MiTableModel.
     */
    @Bean
    @Scope("prototype")
    public MiTableModel tableModelTodos(MessageSource messageSource,ApplicationLocale applicationLocale){
        return new TableModelAll(messageSource,applicationLocale);
    }
    
    /**
     * String utiliza este metodo para crear una instancia de la clase 
     * com.brasajava.view.persona.ListaDePersona.
     * @param context del tipo org.springframework.context.ApplicationContext.
     * @return del tipo com.brasajava.view.persona.ListaDePersona.
     */
    @Bean
    @Scope("prototype")
    public ListaPersona listaPersona(ApplicationContext context){
        ListaPersona listaPersona = new ListaPersona(context);
        listaPersona.setName("null");
        return listaPersona;
    }
    
    /**
     * String utiliza este metodo para crear una instancia de la clase 
     * com.brasajava.view.persona.MiTableModelNifNombre.
     * @param messageSource del tipo org.springframework.context.MessageSource.
     * @param applicationLocale del tipo com.brasajava.util.ApplicationLocale.
     * @return del tipo com.brasajava.view.persona.MiTableModel.
     */
    @Bean
    @Scope("prototype")
    public MiTableModel tableModelNifNombre(MessageSource messageSource,ApplicationLocale applicationLocale){
        return new MiTableModelNifNombre(messageSource, applicationLocale);
    }
    
    /**
     * String utiliza este metodo para crear una instancia de la clase
     * com.brasajava.view.persona.BusquedaDePersona.
     * @param context del tipo org.springframework.context.ApplicationContext.
     * @param tpv del tipo com.brasajava.view.tpv.TPV.
     * @return del tipo com.brasajava.view.BusquedaDePersona.
     */
    @Bean
    @Scope("prototype")
    public BusquedaDePersona busquedaDePersona(ApplicationContext context, TPV tpv){
        return new BusquedaDePersona(tpv, context);
    }
    
    /**
     * String utiliza este metodo para crear una instancia de la clase 
     * com.brasajava.view.tpv.BuscaFactura.
     * @param context del tipo org.springframework.context.ApplicationContext.
     * @param tpv del tipo com.brasajava.view.tpv.TPV.
     * @return del tipo com.brasaja.view.tpv.BuscaFactura.
     */
    @Bean
    @Scope("prototype")
    public BuscaFactura buscaFactura(ApplicationContext context, TPV tpv){
        return new BuscaFactura(tpv, context);
    }
    
    /**
     * String utiliza este metodo para crear una instancia de la clase 
     * com.brasajava.view.tpv.TPV.
     * @param context del tipo org.springframework.context.ApplicationContext.
     * @param session del tipo com.brasajava.util.Session.
     * @return del tipo com.brasajava.view.tpv.TPV.
     */
    @Bean 
    public TPV tpv(ApplicationContext context,Session session){
        return new TPV(context, session);
    }
    
    /**
     * String utiliza este metodo para crear una instancia de la clase 
     * com.brasajava.view.tpv.CajaTableModel.
     * @param messageSource del tipo org.springframework.context.MessageSource.
     * @param applicationLocale del tipo com.brasajava.util.ApplicationLocale.
     * @return del tipo com.brasajava.view.tpv.CajaTableModel.
     */
    @Bean
    public CajaTableModel cajaTableModel(MessageSource messageSource, ApplicationLocale applicationLocale){
        return new CajaTableModel(messageSource,applicationLocale);
    }
    
    /**
     * String utiliza este metodo para crear una instancia de la clase 
     * com.brasajava.view.tpv.Pagar.
     * @param context del tipo org.springframework.context.ApplicationContext.
     * @param tpv del tipo com.brasajava.view.tpv.TPV.
     * @return del tipo com.brasajava.view.tpv.Pagar.
     */
    @Bean
    @Scope("prototype")
    public Pagar pagar(ApplicationContext context, TPV tpv){
        return new Pagar(tpv, context);
    }
    
    /**
     * String utiliza este metodo para crear una instancia de la clase 
     * com.brasajava.model.Factura.
     * @param session del tipo com.brasajava.util.Session.
     * @return del tipo com.brasajava.model.Factura.
     */
    @Bean
    @Scope("prototype")
    public Factura factura(Session session){
        Factura f = new Factura();
        f.setCliente(session.getCliente());
        f.setUsuario(session.getUsuario());
        return f;
    }
    
    /**
     * String utiliza este metodo para crear una instancia de la clase 
     * com.brasajava.model.Venta.
     * @param session del tipo com.brasajava.util.Session.
     * @return del tipo com.brasajava.model.Venta.
     */
    @Bean
    @Scope("prototype")
    public Venta venta(Session session){
        Venta venta = new Venta();
        venta.setUsuario(session.getUsuario());
        venta.setCliente(session.getCliente());
        return venta;
    }
    
    /**
     * String utiliza este metodo para crear una instancia de la clase 
     * com.brasajava.view.producto.ProductoView.
     * @param context del tipo org.springframework.context.ApplicationContext.
     * @return del tipo com.brasajava.view.producto.ProductoView.
     */
    @Bean
    @Scope("prototype")
    public ProductoView productoView(ApplicationContext context){
        ProductoView producto = new ProductoView(context);
        return producto;
    }
    
    /**
     * String utiliza este metodo para crear una instancia de la clase 
     * com.brasajava.view.producto.GrupoView.
     * @param context del tipo org.springframework.context.ApplicationContext.
     * @return del tipo com.brasajava.view.producto.GrupoView.
     */
    @Bean
    @Scope("prototype")
    public GrupoView grupoView(ApplicationContext context){
        GrupoView grupo = new GrupoView(context);
        return grupo;
    }
    
    /**
     * String utiliza este metodo para crear una instancia de la clase 
     * com.brasajava.view.producto.BuscaProductoI.
     * @param main del tipo com.brasajava.view.principal.MainFrame.
     * @param context del tipo org.springframework.context.ApplicationContext.
     * @return del tipo com.brasajava.view.producto.BuscarProductoI.
     */
    @Bean
    @Scope("prototype")
    public BuscarProductoI buscarProducto(MainFrame main,ApplicationContext context){
        BuscarProductoI lista = new BuscarProductoI(context);
        main.getDesktopPane().add(lista);
        return lista;
    }
    
    
    
    
    /**
     * String utiliza este metodo para crear una instancia de la clase 
     * com.brasajava.view.producto.BuscarProducto.
     * @param main del tipo com.brasajava.view.principal.MainFrame.
     * @param context del tipo org.springframework.context.ApplicationContext.
     * @return del tipo com.brasajava.view.producto.BuscarProducto.
     */
    @Bean
    @Scope("prototype")
    public BuscarProducto buscarProductoParaAñadir(MainFrame main,ApplicationContext context){
        BuscarProducto lista = new BuscarProducto(main,context);
        lista.setLocationRelativeTo(null);
        return lista;
    }
   
    /**
     * String utiliza este metodo para crear una instancia de la clase 
     * com.brasajava.view.producto.BuscaGrupoI.
     * @param main del tipo com.brasajava.view.principal.MainFrame.
     * @param context del tipo org.springframework.context.ApplicationContext.
     * @return del tipo com.brasajava.view.producto.BuscaGrupoI.
     */
    @Bean
    @Scope("prototype")
    public BuscarGrupoI buscarGrupo(MainFrame main,ApplicationContext context){
        BuscarGrupoI lista = new BuscarGrupoI(context);
        main.getDesktopPane().add(lista);
        return lista;
    }
    
    /**
     * String utiliza este metodo para crear una instancia de la clase 
     * com.brasajava.view.producto.BuscarGrupo.
     * @param main del tipo com.brasajava.view.principal.MainFrame.
     * @param context del tipo org.springframework.context.ApplicationContext.
     * @return del tipo com.brasajava.view.producto.BuscaGrupo.
     */
    @Bean
    @Scope("prototype")
    public BuscarGrupo buscarGrupoParaAñadir(MainFrame main,ApplicationContext context){
        BuscarGrupo lista = new BuscarGrupo(main,context);
        lista.setLocationRelativeTo(null);
        return lista;
    }
    
    /**
     * String utiliza este metodo para crear una instancia de la clase 
     * com.brasajava.view.producto.ProductoTableModel.
     * @param messageSouce del tipo org.springframework.context.MessageSource.
     * @param applicationLocale del tipo com.brasajava.util.ApplicationLocale.
     * @return del tipo com.brasajava.view.producto.ProductoTableModel.
     */
    @Bean
    @Scope("prototype")
    public ProductoTableModel productoTableModel(MessageSource messageSouce,ApplicationLocale applicationLocale){
        return new ProductoTableModel(messageSouce,applicationLocale);
    }
    
    /**
     * String utiliza este metodo para crear una instancia de la clase 
     * com.brasajava.view.tpv.FacturaTableModel.
     * @param messageSouce del tipo org.springframework.context.MessageSource.
     * @param applicationLocale del tipo com.brasajava.util.ApplicationLocale.
     * @return del tipo com.brasajava.view.tpv.FacturaTableModel.
     */
    @Bean
    @Scope("prototype")
    public FacturaTableModel facturaTableModel(MessageSource messageSouce,ApplicationLocale applicationLocale){
        return new FacturaTableModel(messageSouce,applicationLocale);
    }
    
    /**
     * String utiliza este metodo para crear una instancia de la clase 
     * com.brasajava.view.producto.GrupoTableModel.
     * @param messageSouce del tipo org.springframework.context.MessageSource.
     * @param applicationLocale del tipo com.brasajava.util.ApplicationLocale.
     * @return del tip com.brasajava.view.producto.GrupoTableModel.
     */
    @Bean
    @Scope("prototype")
    public GrupoTableModel grupoTableModel(MessageSource messageSouce,ApplicationLocale applicationLocale){
        return new GrupoTableModel(messageSouce,applicationLocale);
    }
}
