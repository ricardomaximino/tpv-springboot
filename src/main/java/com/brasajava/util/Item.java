package com.brasajava.util;

/**
 * Esta classe representa un item,por ejemplo, en un JComboBox.<br>
 * Esta classe implementea la interfaz 
 * com.brasajava.view.menu.MessageSourceKeyName para facilitar el proceso de 
 * internacinalizacion.
 * @author Ricardo Maximino
 */
public class Item {

    private String name;
    private String label;
    private String commandName;
    private Object value;

    /**
     * Este Constructor configura dos de las tres variable globales de esta 
     * classe.<br>
     * Esta classe esta pensada para que la variables:
     * <UL>
     *  <li>
     * label lleve el nombre del
     * item con la internacionalizacion ya applicada.
     * </li>
     * <li>
     * messageSourceKeyName lleve el codigo (key value) para
     * obtener el valor de este codigo en el proceso de internacionalizacion.
     * </li>
     * <li>
     * commandName lleve el nombre del comando responsabel de ejecutar
     * la funcionalidade que este item representa usando IoC(look Up).
     * </li>
     * </UL>
     * @param commandName del tipo java.lang.String.
     * @param name del tipo java.lang.String.
     */
    public Item(String commandName, String name) {
        this.commandName = commandName;
        this.name = name;
    }

    /**
     * Retorna el valor de la variable global label.
     * @return del tipo java.lang.String.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Configura el valor de la variable global label.
     * @param label del tipo java.lang.String.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Retorna el valor de la variable global commandName.
     * @return del tipo java.lang.String.
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * Configura el valor de la variable global commandName.
     * @param commandName del tipo java.lang.String.
     */
    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }
    /**
     * Retorna el valor de la variable global value.
     * @return del tipo java.lang.Object.
     */
    public Object getValue() {
        return value;
    }

    /**
     * Configura el valor de la variable global value.
     * @param value del tipo java.lang.Object.
     */
    public void setValue(Object value) {
        this.value = value;
    }
  
    /**
     * Configura el valor de la variable global name.
     * @param name del tipo java.lang.String.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retorna el valor de la variable global name.
     * @return del tipo java.lang.String.
     */
    public String getName() {
        return name;
    }

    /**
     * Este metodo overrides el metodo toString para para que se retorne el 
     * valor de la variable global label.
     * @return del tipo java.lang.String.
     */
    @Override
    public String toString() {
        return label;
    }
}
