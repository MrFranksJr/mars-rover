package io.tripled.marsrover.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = MarsRoverRestController.class)
@ComponentScan(basePackages = {"io.tripled"})
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MarsRoverRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void returnsStatusCodeOk() throws Exception {

        var result = mockMvc.perform(MockMvcRequestBuilders.post("/api/createsimulation/10"));

        result.andExpect(status().isOk());
    }

    @Test
    void doesResponseReturnSimulationState() throws Exception {

        createSimulationOf10();

        var result = getSimulationState();

        result.andExpect(content().string(containsString("10")));
        result.andExpect(jsonPath("$.simulationSize").value("10"));
    }

    @Test
    void createMultipleSimulationsCheck() throws Exception {

        createSimulationOf10();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/createsimulation/20"));

        var result = getSimulationState();

        result.andExpect(content().string(containsString("10")));
        result.andExpect(jsonPath("$.simulationSize").value("10"));
    }


    @Test
    void landRover() throws Exception {
        createSimulationOf10();

        landRoverOn55();

        var result = getSimulationState();

        result.andExpect(jsonPath("$.roverList[0].roverName").value("R1"));
        result.andExpect(jsonPath("$.roverList[0].roverXPosition").value("5"));
        result.andExpect(jsonPath("$.roverList[0].roverYPosition").value("5"));
    }

    @Test
    void moveRover() throws Exception {
        createSimulationOf10();

        landRoverOn55();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/moverover/R1/f1"));

        var result = getSimulationState();

        result.andExpect(jsonPath("$.roverList[0].roverName").value("R1"));
        result.andExpect(jsonPath("$.roverList[0].roverXPosition").value("5"));
        result.andExpect(jsonPath("$.roverList[0].roverYPosition").value("6"));
    }

    @Test
    void moveRoverCaseInsensitive() throws Exception {
        createSimulationOf10();

        landRoverOn55();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/moverover/R1/F1"));

        var result = getSimulationState();

        result.andExpect(jsonPath("$.roverList[0].roverName").value("R1"));
        result.andExpect(jsonPath("$.roverList[0].roverXPosition").value("5"));
        result.andExpect(jsonPath("$.roverList[0].roverYPosition").value("6"));
    }
    @Test
    void turnsAndMovesForward() throws Exception {
        createSimulationOf10();

        landRoverOn55();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/moverover/R1/R f1"));

        var result = getSimulationState();

        result.andExpect(jsonPath("$.roverList[0].roverName").value("R1"));
        result.andExpect(jsonPath("$.roverList[0].roverXPosition").value("6"));
        result.andExpect(jsonPath("$.roverList[0].roverYPosition").value("5"));
    }
    @Test
    void roverCanTurnRight() throws Exception {
        createSimulationOf10();

        landRoverOn55();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/moverover/R1/r"));

        var result = getSimulationState();

        result.andExpect(jsonPath("$.roverList[0].roverName").value("R1"));
        result.andExpect(jsonPath("$.roverList[0].roverXPosition").value("5"));
        result.andExpect(jsonPath("$.roverList[0].roverYPosition").value("5"));
        result.andExpect(jsonPath("$.roverList[0].roverHeading").value("EAST"));
    }
    @Test
    void turnRoverIsCaseInsensitive() throws Exception {
        createSimulationOf10();

        landRoverOn55();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/moverover/R1/R"));

        var result = getSimulationState();

        result.andExpect(jsonPath("$.roverList[0].roverName").value("R1"));
        result.andExpect(jsonPath("$.roverList[0].roverXPosition").value("5"));
        result.andExpect(jsonPath("$.roverList[0].roverYPosition").value("5"));
        result.andExpect(jsonPath("$.roverList[0].roverHeading").value("EAST"));
    }
    @Test
    void turnLeftIsCaseInsensitive() throws Exception {
        createSimulationOf10();

        landRoverOn55();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/moverover/R1/L"));

        var result = getSimulationState();

        result.andExpect(jsonPath("$.roverList[0].roverName").value("R1"));
        result.andExpect(jsonPath("$.roverList[0].roverXPosition").value("5"));
        result.andExpect(jsonPath("$.roverList[0].roverYPosition").value("5"));
        result.andExpect(jsonPath("$.roverList[0].roverHeading").value("WEST"));
    }

    @Test
    void moveRoverMultipleMoves() throws Exception {
        createSimulationOf10();

        landRoverOn55();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/moverover/R1/F1 r2"));

        var result = getSimulationState();

        result.andExpect(jsonPath("$.roverList[0].roverName").value("R1"));
        result.andExpect(jsonPath("$.roverList[0].roverHeading").value("SOUTH"));
        result.andExpect(jsonPath("$.roverList[0].roverXPosition").value("5"));
        result.andExpect(jsonPath("$.roverList[0].roverYPosition").value("6"));
    }

    private void createSimulationOf10() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/createsimulation/10"));
    }

    private void landRoverOn55() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/landrover/5/5")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"));
    }
    private ResultActions getSimulationState() throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get("/api/simulationstate")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"));
    }
}
