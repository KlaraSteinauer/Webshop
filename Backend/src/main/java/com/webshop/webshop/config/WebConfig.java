package com.webshop.webshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*");
    }

    //Mit CorsMapping wird sichergestellt, dass der Request von JS zum Endpoint ins Backend kommt.
    //Hauptaufgabe des CorsMapping ist den Request sicherzustellen, nichts weiter passiert hier.
    //Der Request kommt beim Controller im Backend an und wird dann weiter im Backend verarbeitet.
}