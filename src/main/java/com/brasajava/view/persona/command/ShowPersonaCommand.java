/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brasajava.view.persona.command;

import com.brasajava.model.Cliente;
import com.brasajava.model.Persona;
import com.brasajava.model.Usuario;
import com.brasajava.util.ApplicationLocale;
import com.brasajava.util.PrototypeContext;
import com.brasajava.view.persona.FramePersona;
import com.brasajava.view.persona.ListaPersona;
import com.brasajava.view.persona.tablemodel.MiTableModel;
import com.brasajava.view.principal.MainFrame;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ricardo
 */
@Component
public class ShowPersonaCommand {

    private final PrototypeContext prototypeContext;
    private final MessageSource messageSource;
    private final ApplicationLocale applicationLocale;
    private final ApplicationContext context;
    private JInternalFrame frame;
    private final JDesktopPane desktopPane;

    public ShowPersonaCommand(ApplicationContext context) {
        this.context = context;
        this.prototypeContext = context.getBean(PrototypeContext.class);
        this.messageSource = context.getBean(MessageSource.class);
        this.applicationLocale = context.getBean(ApplicationLocale.class);
        this.desktopPane = context.getBean(MainFrame.class).getDesktopPane();
    }
//object (person o iterable),class,titleMessageKey
    public void show(Object obj,Class clazz,String titleMessageKey) {
        
        //Persona
        if (obj instanceof Persona) {
            persona(obj,clazz,titleMessageKey);
        }else if(obj instanceof Iterable){
            iterable(obj,clazz,titleMessageKey);
        } 
        
        if (frame != null) {
            frame.setVisible(true);
        }
        desktopPane.setSelectedFrame(frame);
        desktopPane.getDesktopManager().deiconifyFrame(frame);
        desktopPane.getDesktopManager().activateFrame(frame);
        if (frame != null) {
            frame.moveToFront();
        }
    }

    private void persona(Object obj,Class clazz,String titleMessageKey) {
            Persona persona = (Persona)obj;
            
        if (persona instanceof Cliente) {
            Cliente cliente = (Cliente) obj;
            FramePersona frame = prototypeContext.putPersona(cliente);
            frame.setGroup(FramePersona.GRUPO_CLIENTE);
            if (cliente.getId() == 0) {
                frame.setState(FramePersona.STATE_ANADIR);
                frame.setName(titleMessageKey);
            } else {
                frame.setState(FramePersona.STATE_ATUALIZAR);
                frame.setName(titleMessageKey);
                frame.setFrameToUpdateMode();
            }
            frame.setTitle(messageSource.getMessage(frame.getName(), null, applicationLocale.getLocale()));
            this.frame = frame;
        } //USUARIO
        else if (persona instanceof Usuario) {
            Usuario usuario = (Usuario) obj;
            FramePersona frame = prototypeContext.putPersona(usuario);
            frame.setGroup(FramePersona.GRUPO_USUARIO);
            if (usuario.getId() == 0) {
                frame.setState(FramePersona.STATE_ANADIR);
                frame.setName(titleMessageKey);
            } else {
                frame.setState(FramePersona.STATE_ATUALIZAR);
                frame.setName(titleMessageKey);
                frame.setFrameToUpdateMode();
            }
            frame.setTitle(messageSource.getMessage(frame.getName(), null, applicationLocale.getLocale()));
            this.frame = frame;
        }
    }
    
    private void iterable(Object obj,Class clazz,String titleMessageKey){
        Iterable iterable = (Iterable)obj;
        MiTableModel model = context.getBean("tableModelTodos",MiTableModel.class);
        ListaPersona frame = context.getBean(ListaPersona.class);
        List<Persona> lista = new ArrayList<>();
        
        if(clazz == Cliente.class){
            for(Object object : iterable){
                lista.add((Cliente)object);
            }
            model.setDatos(lista);
            frame.setName(titleMessageKey);
            frame.setTitle(context.getMessage(frame.getName(),null,applicationLocale.getLocale()));
            frame.getTabla().setModel(model);
            prototypeContext.putPrototype(frame);
        }else if(clazz == Usuario.class){
            for(Object object : iterable){
                lista.add((Usuario)object);
            }
            model.setDatos(lista);
            frame.setName(titleMessageKey);
            frame.setTitle(context.getMessage(frame.getName(),null,applicationLocale.getLocale()));
            frame.getTabla().setModel(model);
            prototypeContext.putPrototype(frame);
        }
        desktopPane.add(frame);
        this.frame = frame;
    }
}
