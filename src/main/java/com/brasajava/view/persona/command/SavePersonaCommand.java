/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brasajava.view.persona.command;

import com.brasajava.model.Cliente;
import com.brasajava.model.Usuario;
import com.brasajava.service.ServicioCliente;
import com.brasajava.service.ServicioUsuario;
import com.brasajava.util.ApplicationLocale;
import com.brasajava.util.PrototypeContext;
import com.brasajava.util.interfaces.Command;
import com.brasajava.view.persona.FramePersona;
import javax.swing.JOptionPane;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ricardo
 */
@Component
public class SavePersonaCommand implements Command {

    private final ApplicationContext context;

    public SavePersonaCommand(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void execute(Object[] args) {

        FramePersona frame = (FramePersona) args[0];
        PrototypeContext prototypeContext = context.getBean(PrototypeContext.class);
        ApplicationLocale applicationLocale = context.getBean(ApplicationLocale.class);
        
        //CLIENTE
        if (frame.getGroup().equals(FramePersona.GRUPO_CLIENTE)) {
          ServicioCliente servicio = context.getBean(ServicioCliente.class);
          Cliente cliente = (Cliente)frame.getPersona();
          cliente = servicio.save(cliente);
          if(cliente != null){
              JOptionPane.showMessageDialog(frame,frame.getPersona().getNombre() + " " + context.getMessage("message_was_saved_successfully",null,applicationLocale.getLocale()));
              frame.dispose();
          }else{
              JOptionPane.showMessageDialog(frame,frame.getPersona().getNombre() + " " + context.getMessage("message_was_not_saved",null,applicationLocale.getLocale()));
          }
        } 
        //USUARIO
        else if(frame.getGroup().equals(FramePersona.GRUPO_USUARIO)){
            ServicioUsuario servicio = context.getBean(ServicioUsuario.class);
            Usuario usuario = (Usuario)frame.getPersona();
          usuario = servicio.save(usuario);
          if(usuario != null){
              JOptionPane.showMessageDialog(frame,frame.getPersona().getNombre() + " " + context.getMessage("message_was_saved_successfully",null,applicationLocale.getLocale()));
              frame.dispose();
          }else{
              JOptionPane.showMessageDialog(frame,frame.getPersona().getNombre() + " " + context.getMessage("message_was_not_saved",null,applicationLocale.getLocale()));
          }
        }

    }
}
