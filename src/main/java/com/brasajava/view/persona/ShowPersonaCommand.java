package com.brasajava.view.persona;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import com.brasajava.model.Cliente;
import com.brasajava.model.Persona;
import com.brasajava.model.Usuario;
import com.brasajava.util.ApplicationLocale;
import com.brasajava.util.PrototypeContext;
import com.brasajava.util.interfaces.MiTableModel;
import com.brasajava.view.principal.MainFrame;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * Esta clase centraliza la responsabilidade de apresentar todos los frames
 * (que son prototype) relacionado con Persona y exibrir los en el mainFrame 
 * y registrar las instancias en el prototypeContext para que la applicacion 
 * tenga controle de todas las instancias prototypes, ya que spring no controla
 * las instancias prototype.
 * 
 * @author Ricardo Maximino
 */
@Component
public class ShowPersonaCommand {

    private final PrototypeContext prototypeContext;
    private final MessageSource messageSource;
    private final ApplicationLocale applicationLocale;
    private final ApplicationContext context;
    private JInternalFrame frame;
    private final JDesktopPane desktopPane;

    /**
     * Único constructor para crear una instancia de esta clase.
     * @param context del tipo org.springframework.context.ApplicationContext.
     * <p>Utilizando el context se pedirá una instancia de las clase:</p>
     * <ul>
     * <li>com.brasajava.util.PrototypeContext</li>
     * <li>com.brasajava.view.principal.MainFrame</li>
     * <li>org.springframework.context.MessageSource</li>
     * <li>com.brasajava.util.ApplicationLocale</li>
     * </ul> 
     */
    public ShowPersonaCommand(ApplicationContext context) {
        this.context = context;
        this.prototypeContext = context.getBean(PrototypeContext.class);
        this.messageSource = context.getBean(MessageSource.class);
        this.applicationLocale = context.getBean(ApplicationLocale.class);
        this.desktopPane = context.getBean(MainFrame.class).getDesktopPane();
    }

    /**
     * Este metodo es el responsable de crear el frame adecuado e apresentar el objeto
     * pasado por parametro en el mainFrame.
     * @param clazz del objeto del index 0.
     * @param titleMessageKey el codigo resgatar el mensaje con MessageSource.
     * @param obj del tipo java.lang.Object se espero o una instancia de la 
     * clase com.brasajava.model.Persona o java.lang.Iterable.
     * <p>Esse metodo pide al spring una instancia del frame adecuado
     * lo configura con el objeto pasado por parameto y registra el frame
     * en el prototyeContext.</p>
     * 
     */
    public void show(Object obj,Class<? extends Persona> clazz,String titleMessageKey) {
        
        //Persona
        if (obj instanceof Persona) {
            persona(obj,clazz,titleMessageKey);
        }else if(obj instanceof Iterable){
            iterable((Iterable<? extends Persona>)obj, clazz, titleMessageKey);
        } 
        
        if (frame != null) {
            frame.setVisible(true);
        }
        //Mejorar focus
        desktopPane.setSelectedFrame(frame);
        desktopPane.getDesktopManager().deiconifyFrame(frame);
        desktopPane.getDesktopManager().activateFrame(frame);
        if (frame != null) {
            frame.moveToFront();
        }
    }

    private void persona(Object obj,Class<?> clazz,String titleMessageKey) {
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
    
    private void iterable(Iterable<? extends Persona> iterable, Class<? extends Persona> clazz,String titleMessageKey){
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
