package com.a506.webrtc.openvidu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class OpenviduConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // registry.addMapping("/**")
        //         .allowedOrigins("http://localhost:3000", "https://i10a506.p.ssafy.io")
        //         .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE");
        registry.addMapping("/**")
            .allowedOrigins("i10a506.p.ssafy.io:3001", "https://i10a506.p.ssafy.io", "http://localhost:3000")
            .allowedHeaders("Authorization", "content-type")
            .allowedMethods("GET", "POST", "DELETE", "PATCH", "OPTIONS")
            .allowCredentials(true);

    }
}
