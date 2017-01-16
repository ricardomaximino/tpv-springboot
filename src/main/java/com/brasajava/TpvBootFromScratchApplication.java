package com.brasajava;

import com.brasajava.util.interfaces.Internationalizable;
import java.util.Map;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TpvBootFromScratchApplication {

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
