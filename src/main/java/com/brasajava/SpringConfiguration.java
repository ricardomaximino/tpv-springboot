package com.brasajava;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Esta classe contiene toda la configuracion de spring boot necesaria para esta
 * applicación. jajajaja casi nada esto es SPRING BOOT.
 * @author Ricardo Maximino
 */
@Configuration
public class SpringConfiguration {

    /**
     * Configura el messageSource.
     * @return del tipo org.springframework.context.MessageSource.
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:messages/view");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }
}
