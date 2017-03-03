package com.brasajava.view.menu;

import com.brasajava.util.ApplicationLocale;
import com.brasajava.view.persona.FrameBusqueda;
import com.brasajava.util.interfaces.Command;
import com.brasajava.view.menu.MenuBar;
import com.brasajava.view.menu.MiMenuItem;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Esta clase implementa la interfaz com.brasajava.util.interfaces.Command
 * y es una clase ejecutora de un solo metodo.
 * @author Ricardo Maximino
 */
@Component
public class MenuItemBuscarCommand implements Command {
    private final ApplicationContext context;

    
    /**
     * Único Contructor para instanciar esta clase.
     * @param context IoC contenedor del tipo org.springframework.context.ApplicationContext.
     */
    public MenuItemBuscarCommand(ApplicationContext context) {
        this.context = context;
    }

    /**
     * Este metodo exibe la ventana de búsqueda.
     * @param args del tipo java.lang.Object[]
     * <p>
     * Funciona de la siguiente manera:
     * </p>
     * <ul>
     * <li>hace el casting del objeto que ha invocado este metodo
     * (que deve estar args[0]) y llama su metodo getGroup() para saber que
     * tipo de comando deve pedir al IoC contenedor.</li>
     * <li>coje referencia de la clase responsable del manejar el Locale de la aplicación(applicationLocale)</li>
     * <li>coje referencia de la clase FrameBusqueda</li>
     * <li>frame.setState(FrameBusqueda_XXXXX) para que luego la instancia de la clase FrameBusqueda
     * sepa que comando deve pedir al IoC contenedor</li>
     * <li>frame.setName("frameBusqueda_XXXXX")</li>
     * <li>frame.setTitle(context.getMessage(frame.getName(),null,applicationLocale.getLocale()))</li>
     * <li>frame.setVisible(true)</li>
     * </ul>
     */
    @Override
    public void execute(Object[] args) {
        FrameBusqueda frame = context.getBean(FrameBusqueda.class);
        ApplicationLocale applicationLocale = context.getBean(ApplicationLocale.class);
        MiMenuItem menu = (MiMenuItem)args[0];
        //Cliente
        if (menu.getGroup().equals(MenuBar.GRUPO_CLIENTE)) {
            frame.setGroup(FrameBusqueda.GRUPO_CLIENTE);     
            frame.setName("frameBusqueda_Client");
            frame.setTitle(context.getMessage(frame.getName(),null,applicationLocale.getLocale()));
        }
        //Usuario
        else if (menu.getGroup().equals(MenuBar.GRUPO_USUARIO)) {
            frame.setGroup(FrameBusqueda.GRUPO_USUARIO);
            frame.setName("frameBusqueda_User");
            frame.setTitle(context.getMessage(frame.getName(),null,applicationLocale.getLocale()));
        }
        frame.setVisible(true);
    }

}
