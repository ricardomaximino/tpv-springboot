package com.brasajava.view.busqueda.command;

import com.brasajava.model.Cliente;
import com.brasajava.model.Usuario;
import com.brasajava.service.ServicioCliente;
import com.brasajava.service.ServicioPersona;
import com.brasajava.service.ServicioUsuario;
import com.brasajava.util.interfaces.Command;
import com.brasajava.view.busqueda.FrameBusqueda;
import com.brasajava.view.persona.command.ShowPersonaCommand;
import java.time.Month;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Esta classe implementa la interfaz
 * com.brasajava.view.busqueda.command.Command y esta pensada para ejecutar una
 * accion concreta al ejecutar el metodo execute(Object[] objs).
 *
 * @author Ricardo Maximino
 */
@Component
public class BuscaPersona implements Command {
    private final ApplicationContext context;
    private String titleMessageKey;
    private Class clazz;

    /**
     * Este es el único constructor para instanciar esta classe.
     *
     * @param context del tipo org.springframework.context.ApplicationContext.
     */
    public BuscaPersona(ApplicationContext context) {
        this.context = context;
    }

    /**
     * Este metodo ejecutará un comando de busqueda especifico.
     *
     * @param objs del tipo java.lang.Object.
     */
    @Override
    public void execute(Object[] objs) {
        FrameBusqueda frame = (FrameBusqueda) objs[0];
        ServicioPersona<?> servicio = null;

        //Cliente
        if (frame.getGroup().equals(FrameBusqueda.GRUPO_CLIENTE)) {
            servicio = context.getBean(ServicioCliente.class);
            clazz = Cliente.class;
            titleMessageKey = "framePersona_UpdateClient";
        } //Usuario
        else if (frame.getGroup().equals(FrameBusqueda.GRUPO_USUARIO)) {
            servicio = context.getBean(ServicioUsuario.class);
            clazz = Usuario.class;
            titleMessageKey = "framePersona_UpdateUser";
        }
        Object obj = get(servicio, frame);
        ShowPersonaCommand showPersonaCommand = context.getBean(ShowPersonaCommand.class);
        showPersonaCommand.show(obj,clazz,titleMessageKey);
    }

    private Object get(ServicioPersona<?> servicio, FrameBusqueda frame) {
        if (servicio != null) {
            switch (frame.getAccion()) {
                case FrameBusqueda.ACCION_BUSCAR_POR_NIF:                    
                    return servicio.findByNif(frame.getText());
                case FrameBusqueda.ACCION_BUSCAR_POR_NOMBRE:
                    if(clazz.equals(Cliente.class)){
                        titleMessageKey = "listaPersona_ClientListByName";
                    }else if(clazz.equals(Usuario.class)){
                        titleMessageKey = "listaPersona_UserListByName";
                    }
                    return servicio.findByNombre(frame.getText());
                case FrameBusqueda.ACCION_BUSCAR_POR_PRIMER_APELLIDO:
                    if(clazz.equals(Cliente.class)){
                        titleMessageKey = "listaPersona_ClientListByFirstLastname";
                    }else if(clazz.equals(Usuario.class)){
                        titleMessageKey = "listaPersona_UserListByFirstLastname";
                    }
                    return servicio.findByPrimerApellido(frame.getText());
                case FrameBusqueda.ACCION_BUSCAR_POR_SEGUNDO_APELLIDO:
                    if(clazz.equals(Cliente.class)){
                        titleMessageKey = "listaPersona_ClientListBySecondLastname";
                    }else if(clazz.equals(Usuario.class)){
                        titleMessageKey = "listaPersona_UserListBySecondLastname";
                    }
                    return servicio.findBySegundoApellido(frame.getText());
                case FrameBusqueda.ACCION_BUSCAR_POR_ALTA:
                    if(clazz.equals(Cliente.class)){
                        titleMessageKey = "listaPersona_ClientListByActiveTrue";
                    }else if(clazz.equals(Usuario.class)){
                        titleMessageKey = "listaPersona_UserListByActiveTrue";
                    }
                    return servicio.findByActivo(true);
                case FrameBusqueda.ACCION_BUSCAR_POR_BAJA:
                    if(clazz.equals(Cliente.class)){
                        titleMessageKey = "listaPersona_ClientListByActiveFalse";
                    }else if(clazz.equals(Usuario.class)){
                        titleMessageKey = "listaPersona_UserListByActiveFalse";
                    }
                    return servicio.findByActivo(false);
                case FrameBusqueda.ACCION_BUSCAR_POR_CUMPLEANEROS_DEL_MES:
                    if(clazz.equals(Cliente.class)){
                        titleMessageKey = "listaPersona_ClientListByBirthdayOfTheMonth";
                    }else if(clazz.equals(Usuario.class)){
                        titleMessageKey = "listaPersona_UserListByBirthdayOfTheMonth";
                    }
                    return servicio.cumpleañerosDelMes(Month.of(frame.getMes()));
            }
        }
        return null;
    }

}
