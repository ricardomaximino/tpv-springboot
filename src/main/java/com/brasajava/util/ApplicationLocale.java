package com.brasajava.util;

import com.brasajava.util.interfaces.Internationalizable;
import java.util.Locale;
import java.util.Map;
import org.springframework.context.ApplicationContext;

/**
 * Esta classe será usada simplesmente para fornecer el locale seleccionado.
 * @author Ricardo
 */
public class ApplicationLocale {
    private Locale locale;
    private Locale DefaultLocale;
    private final ApplicationContext context;
    
    /**
     * Este es el constructor con argumento para instanciar esta clase totalmente
     * configurada.
     * @param locale del tipo java.util.Locale.
     * @param defaultLocale del tipo java.util.Locale.
     * @param context del tipo org.springframework.context.ApplicationContext.
     */
    public ApplicationLocale(Locale locale, Locale defaultLocale,ApplicationContext context){
        this.locale = locale;
        this.DefaultLocale = defaultLocale;
        this.context = context;
    }
    
    /**
     * Este el el constructor con solo un argumento, usado cuando el locale y
     * el defaultLocale sean iguales.
     * @param locale del tipo java.util.locale
     * @param context del tipo org.springframework.context.ApplicationContext.
     */
    public ApplicationLocale(Locale locale,ApplicationContext context){
        this.locale = this.DefaultLocale = locale;
        this.context = context;
    }

    /**
     * Retorna el valor de la variable global locale.
     * @return del tipo java.util.Locale.
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Configura el valor de la variable global locale.
     * @param locale del tipo java.util.Locale.
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
        applyLocale();
    }

    /**
     * Retorna el valor da la variable global defaultLocale.
     * @return del tipo java.util.Locale.
     */
    public Locale getDefaultLocale() {
        return DefaultLocale;
    }

    /**
     * Configura el valor de la variable global defaultLocale.
     * @param DefaultLocale del tipo java.util.Locale.
     */
    public void setDefaultLocale(Locale DefaultLocale) {
        this.DefaultLocale = DefaultLocale;
    }
    
    private void applyLocale(){
        for (Map.Entry<String, Internationalizable> entry : context.getBeansOfType(Internationalizable.class).entrySet()) {
            entry.getValue().refreshLanguage();
        }
    }
    
}
