package com.brasajava;

import com.brasajava.util.interfaces.Internationalizable;
import java.util.Map;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Esta clase es la que conten el metodo main, es la clase que inicia la maquina
 * spring.
 * @author Ricardo Maximino
 */
@SpringBootApplication
public class TpvBootFromScratchApplication {

        /**
         * Inicia Springboot.
         * @param args del tipo java.lang.String[].
         */
	public static void main(String[] args) {
                ConfigurableApplicationContext ctx = new SpringApplicationBuilder(TpvBootFromScratchApplication.class).headless(false).web(false).run(args);
                for(String s : ctx.getBeanDefinitionNames()){
                    System.out.println(s);
                }
                
                for(Map.Entry<String,Internationalizable> entry : ctx.getBeansOfType(Internationalizable.class).entrySet()){
                    System.out.println("Internationalizable class name is : " + entry.getKey());
                    
                }
	}       
}
