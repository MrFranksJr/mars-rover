package io.tripled.marsrover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class MarsRoverReactApplication {
    public static void main(String[] args) {
        //System.setProperty("spring.web.resources.static-locations","classpath:/static-react/");
        SpringApplication.run(MarsRoverReactApplication.class, args);
    }
}