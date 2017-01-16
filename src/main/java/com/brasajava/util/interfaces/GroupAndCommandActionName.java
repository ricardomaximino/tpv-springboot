package com.brasajava.util.interfaces;

/**
 * Esta interfaz fue creada para que los componentes visuales pudan saber su propria
 * funcion.
 * @author Ricardo Maximino
 */
public interface GroupAndCommandActionName {
    /**
     * Configura una suposta variable global.
     * @param group del tipo java.lang.String.
     */
    void setGroup(String group);
    /**
     * Retorna el valor de la suposta variable global.
     * @return del tipo java.lang.String.
     */
    String getGroup();
    /**
     * Configura una suposta variable global.
     * @param commandActionName del tipo java.lang.String.
     */
    void setCommandActionName(String commandActionName);
    /**
     * Retorna el valor de la suposta variable global.
     * @return del tipo java.lang.String.
     */
    String getCommandActionName();
}
