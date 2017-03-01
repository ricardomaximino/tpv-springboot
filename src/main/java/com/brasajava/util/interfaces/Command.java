package com.brasajava.util.interfaces;

/**
 * Esta interfaz fue creada para modularizar el comandos de los componentes 
 * visuales.
 * @author Ricardo Maximino
 */
public interface Command {
    /**
     * Unico metodo que ejecuta el comando implementado.
     * @param args del tipo java.lang.Object[].
     */
    void execute(Object[] args);
    
}
