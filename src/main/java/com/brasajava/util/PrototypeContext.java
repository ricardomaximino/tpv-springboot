package com.brasajava.util;

import com.brasajava.model.Persona;
import com.brasajava.util.interfaces.Internationalizable;
import com.brasajava.view.principal.MainFrame;
import com.brasajava.view.persona.FramePersona;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Estea clase implementa com.brasajava.util.interfaces.Internationalizable, y
 * esta clase actuará como un contexto the las instancias con el scope = "prototype,
 * para que se tenga más control sob ellas. Se utiliza un mapa del tipo
 * java.util.Map&lt;com.brasajava.model.Persona,com.brasajava.view.persona.FramePersona&gt; 
 * y una lista del tipo java.util.List&lt;java.lang.Object&gt;.
 * @author Ricardo Maximino
 */
@Component
public class PrototypeContext implements Internationalizable{
    List<Object> allPrototypes;
    Map<Persona,FramePersona> mapPersonaFrame;
    ApplicationContext context;
    
    /**
     * Este es el único contructor para instanciar esta clase 100% configurada.
     * @param context del tipo org.springframework.context.ApplicationContext.
     */
    public PrototypeContext(ApplicationContext context){
        this.context = context;
        allPrototypes = new ArrayList<>();
        mapPersonaFrame = new HashMap<>();
    }
    
    /**
     * Este metodo chequea si hay algun objeto en el map utilizando como key
     * la persona pasada como parametro.
     * @param persona del tipo com.brasajava.model.Persona.
     * @return com.brasajava.view.persona.FramePersona.
     */
    public FramePersona putPersona(Persona persona){   
        if(mapPersonaFrame.containsKey(persona)){
            return mapPersonaFrame.get(persona);
        }else{
            FramePersona frame = context.getBean(FramePersona.class);
            frame.setPersona(persona);
            context.getBean(MainFrame.class).getDesktopPane().add(frame);
            mapPersonaFrame.put(persona, frame);
            allPrototypes.add(frame);
            return frame;
        }
    }
    
    /**
     * Este metodo guarda un referencia de un objeto con scope = prototype 
     * principalmentes para el cambio de idiomas.
     * @param obj del tipo java.lang.Object.
     */
    public void putPrototype(Object obj){
        allPrototypes.add(obj);
    }
    /**
     * Este metodo remove, en caso de que hay toda y cualquer referencia al
     * objeto pasado como parametro.
     * @param obj del tipo java.lang.Object.
     */
    public void remove(Object obj){
        allPrototypes.remove(obj);
        if(obj instanceof FramePersona){
            mapPersonaFrame.remove(((FramePersona)obj).getPersona(),(FramePersona)obj);
        }
    }
    /**
     * Este metodo remove todas las referencias armacenadas en el mapa y el la lista.
     */
    public void clear(){
        allPrototypes.clear();
        mapPersonaFrame.clear();
    }

    /**
     * Este metodo implementa la interfaz 
     * com.brasajava.util.interfaces.Internationalizable para propagar el 
     * cambio de locale para las clases con scope = prototype que implementan 
     * esta interfaz.
     */
    @Override
    public void refreshLanguage() {
        for(Object obj: allPrototypes){
            if(obj instanceof Internationalizable){
                System.out.println("Prototype Context refreshing language of " + obj.getClass() + " this instance has name = " +((JComponent)obj).getName() + "the size of portotype list is: " + allPrototypes.size());
                ((Internationalizable)obj).refreshLanguage();
            }
        }
    }
}
