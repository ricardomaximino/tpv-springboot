package com.brasajava.util.interfaces;

/**
 * Esta interfaz fue creada para que el contedor pueda seleccionar todas las
 * clases que la implemente y invocar su único metodo cuando hay algun cambio
 * en el locale.
 * @author Ricardo Maximino
 */
public interface Internationalizable {
    
    /**
     * Único metodo que ejecuta el comando implementado.
     */
    void refreshLanguage();
}
