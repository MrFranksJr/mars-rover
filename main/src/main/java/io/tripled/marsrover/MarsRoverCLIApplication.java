package io.tripled.marsrover;

import io.tripled.marsrover.cli.commands.CommandParser;
import io.tripled.marsrover.cli.input.InputHandler;
import io.tripled.marsrover.cli.input.InputReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
//Needed for the CLI Application which is not wired via spring boot
@PropertySource("classpath:application.properties")
public class MarsRoverCLIApplication {

    public static void main(String[] args) {
        final MarsRoverApi marsRoverApi = createApplicationViaSpring();
        final InputReader inputReader = buildReader(marsRoverApi);
        inputReader.readInput();
    }

    private static MarsRoverApi createApplicationViaSpring() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("io.tripled.marsrover");
        return applicationContext.getBean(MarsRoverApi.class);
    }

    private static InputReader buildReader(MarsRoverApi marsRoverApi) {
        final CommandParser commandParser = new CommandParser(marsRoverApi);
        final InputHandler inputHandler = new InputHandler(commandParser);
        return new InputReader(inputHandler);
    }
}