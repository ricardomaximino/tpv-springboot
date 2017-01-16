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
import com.brasajava.model.Persona;
import com.brasajava.model.Usuario;
import com.brasajava.view.persona.ListaPersona;
import com.brasajava.view.persona.tablemodel.MiTableModel;
import com.brasajava.view.persona.tablemodel.TableModelAll;
import java.time.LocalDate;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author Ricardo
 */
@Configuration
public class ApplicationConfiguration {

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
        ApplicationLocale applicationLocale = new ApplicationLocale(new Locale("pt","BR"),new Locale("es","ES"),context);
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
}
