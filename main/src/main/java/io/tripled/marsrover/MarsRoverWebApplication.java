package io.tripled.marsrover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarsRoverWebApplication {
    public static void main(String[] args) {
        System.setProperty("spring.web.resources.static-locations","classpath:/static-web/");
//        String[] actualArgs = new String[]{"spring.web.resources.static-locations=classpath:/static-web/"};
        SpringApplication.run(MarsRoverWebApplication.class, args);
    }
}