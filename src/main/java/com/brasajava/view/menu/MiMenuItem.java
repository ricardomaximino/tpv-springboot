package com.brasajava.view.menu;

import com.brasajava.util.interfaces.GroupAndCommandActionName;
import javax.swing.JMenuItem;

/**
 * Esta clase extende javax.swing.JMenuItem y implementa la interfaz
 * com.brasajava.util.interfaces.GruopAndCommandActionName;
 *
 * @author Ricardo Maximino
 */
public class MiMenuItem extends JMenuItem implements GroupAndCommandActionName {

    private String group;
    private String commandActionName;

    /**
     * Constructor con solo un parametro, es lo minimo de configuracion para
     * instanciar esta clase.
     * 
     *
     * @param name del tipo java.lang.String.
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public MiMenuItem(String name) {
        super();
        this.setName(name);
    }

    /**
     * Constructor con tres parametos para instanciar esta clase totalmente
     * configurada.
     *
     * @param name del tipo java.lang.String.
     * @param group del tipo java.lang.String.
     * @param commandActionName del tipo java.lang.String.
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public MiMenuItem(String name, String group, String commandActionName) {
        super();
        this.setName(name);
        this.group = group;
        this.commandActionName = commandActionName;
    }

    /**
     * Configura el valor de la variable global group.
     *
     * @param group del tipo java.lang.String.
     */
    @Override
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * Retorna el valor de la variable global group.
     *
     * @return del tipo java.lang.String.
     */
    @Override
    public String getGroup() {
        return group;
    }

    /**
     * Configura el valor de la variable global commandActionName.
     *
     * @param commandActionName del tipo java.lang.String.
     */
    @Override
    public void setCommandActionName(String commandActionName) {
        this.commandActionName = commandActionName;
    }

    /**
     * Retorna el valor de la variable global commandActionName.
     *
     * @return del tipo java.lang.String.
     */
    @Override
    public String getCommandActionName() {
        return commandActionName;
    }
}
