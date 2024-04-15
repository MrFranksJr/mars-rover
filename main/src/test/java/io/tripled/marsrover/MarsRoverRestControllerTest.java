package io.tripled.marsrover;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tripled.marsrover.dto.SimulationViewDTO;
import io.tripled.marsrover.rest.MarsRoverRestController;
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

        final String simulationId = getSimulationId();

        var result = getSimulationState(simulationId);

        result.andExpect(content().string(containsString("10")));
        result.andExpect(jsonPath("$.simulationSize").value("10"));
    }

    @Test
    void doesResponseReturnSimulationName() throws Exception {
        createSimulationWithName("SimpleName");

        final String simulationId = getSimulationId();

        var result = getSimulationState(simulationId);

        result.andExpect(jsonPath("$.simulationSize").value("10"));
        result.andExpect(jsonPath("$.simulationName").value("SimpleName"));
    }

    @Test
    void createSimulationWithCustomNameAndCustomSize() throws Exception {
        createSimulationWithNameAndSize("CustomName", 23);

        final String simulationId = getSimulationId();

        var result = getSimulationState(simulationId);

        result.andExpect(jsonPath("$.simulationSize").value("23"));
        result.andExpect(jsonPath("$.simulationName").value("CustomName"));
    }

    private void createSimulationWithNameAndSize(String simulationName, int simulationSize) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/createsimulation/" + simulationSize + "/" + simulationName)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"));
    }

    private void createSimulationWithName(String simulationName) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/createsimulation/10/" + simulationName)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"));
    }


    @Test
    void landRover() throws Exception {
        createSimulationOf10();
        final String simulationId = getSimulationId();

        landRoverOn55(simulationId);

        var result = getSimulationState(simulationId);

        result.andExpect(jsonPath("$.roverList[0].roverName").value("R1"));
        result.andExpect(jsonPath("$.roverList[0].roverXPosition").value("5"));
        result.andExpect(jsonPath("$.roverList[0].roverYPosition").value("5"));
        result.andExpect(jsonPath("$.roverList[0].hitPoints").value("5"));
        result.andExpect(jsonPath("$.roverList[0].operationalStatus").value("OPERATIONAL"));

    }

    @Test
    void moveRover() throws Exception {
        createSimulationOf10();
        final String simulationId = getSimulationId();

        landRoverOn55(simulationId);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/moverover/" + simulationId + "/R1 f"));

        var result = getSimulationState(simulationId);

        result.andExpect(jsonPath("$.roverList[0].roverName").value("R1"));
        result.andExpect(jsonPath("$.roverList[0].roverXPosition").value("5"));
        result.andExpect(jsonPath("$.roverList[0].roverYPosition").value("6"));
    }

    @Test
    void turnsAndMovesForward() throws Exception {
        createSimulationOf10();
        final String simulationId = getSimulationId();

        landRoverOn55(simulationId);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/moverover/" + simulationId + "/R1 r f1"));

        var result = getSimulationState(simulationId);

        result.andExpect(jsonPath("$.roverList[0].roverName").value("R1"));
        result.andExpect(jsonPath("$.roverList[0].roverXPosition").value("6"));
        result.andExpect(jsonPath("$.roverList[0].roverYPosition").value("5"));
    }

    @Test
    void roverCanTurnRight() throws Exception {
        createSimulationOf10();
        final String simulationId = getSimulationId();

        landRoverOn55(simulationId);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/moverover/" + simulationId + "/R1 r"));

        var result = getSimulationState(simulationId);

        result.andExpect(jsonPath("$.roverList[0].roverName").value("R1"));
        result.andExpect(jsonPath("$.roverList[0].roverXPosition").value("5"));
        result.andExpect(jsonPath("$.roverList[0].roverYPosition").value("5"));
        result.andExpect(jsonPath("$.roverList[0].roverHeading").value("EAST"));
    }

    @Test
    void roverCanTurnLeft() throws Exception {
        createSimulationOf10();
        final String simulationId = getSimulationId();

        landRoverOn55(simulationId);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/moverover/" + simulationId + "/R1 l"));

        var result = getSimulationState(simulationId);

        result.andExpect(jsonPath("$.roverList[0].roverName").value("R1"));
        result.andExpect(jsonPath("$.roverList[0].roverXPosition").value("5"));
        result.andExpect(jsonPath("$.roverList[0].roverYPosition").value("5"));
        result.andExpect(jsonPath("$.roverList[0].roverHeading").value("WEST"));
    }

    @Test
    void moveRoverMultipleMoves() throws Exception {
        createSimulationOf10();
        final String simulationId = getSimulationId();

        landRoverOn55(simulationId);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/moverover/" + simulationId + "/R1 f1 r2"));

        var result = getSimulationState(simulationId);

        result.andExpect(jsonPath("$.roverList[0].roverName").value("R1"));
        result.andExpect(jsonPath("$.roverList[0].roverHeading").value("SOUTH"));
        result.andExpect(jsonPath("$.roverList[0].roverXPosition").value("5"));
        result.andExpect(jsonPath("$.roverList[0].roverYPosition").value("6"));
    }

    @Test
    void roversR1AndR2Collide() throws Exception {
        createSimulationOf10();
        final String simulationId = getSimulationId();

        landRover(simulationId, 5, 5);
        landRover(simulationId, 5, 6);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/moverover/" + simulationId + "/R1 f1"));

        var result = getSimulationState(simulationId);

        result.andExpect(jsonPath("$.roverList[0].roverName").value("R1"));
        result.andExpect(jsonPath("$.roverList[0].roverHeading").value("NORTH"));
        result.andExpect(jsonPath("$.roverList[0].roverXPosition").value("5"));
        result.andExpect(jsonPath("$.roverList[0].roverYPosition").value("5"));
    }

    @Test
    void roverDiesWhenHitpointsAreZero() throws Exception {
        createSimulationOf10();
        final String simulationId = getSimulationId();

        landRover(simulationId, 5, 5);
        landRover(simulationId, 5, 6);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/moverover/" + simulationId + "/R1 f1"));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/moverover/" + simulationId + "/R1 f1"));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/moverover/" + simulationId + "/R1 f1"));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/moverover/" + simulationId + "/R1 f1"));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/moverover/" + simulationId + "/R1 f1"));

        var result = getSimulationState(simulationId);
        System.out.println("result is: " + result);
        result.andExpect(jsonPath("$.roverList[0].roverName").value("R1"));
        result.andExpect(jsonPath("$.roverList[0].roverHeading").value("NORTH"));
        result.andExpect(jsonPath("$.roverList[0].roverXPosition").value("5"));
        result.andExpect(jsonPath("$.roverList[0].roverYPosition").value("5"));
        result.andExpect(jsonPath("$.roverList[0].hitPoints").value("0"));
        result.andExpect(jsonPath("$.roverList[0].operationalStatus").value("BROKEN"));
    }

    @Test
    void roverWasAlreadyBroken() throws Exception {
        createSimulationOf10();

        final String simulationId = getSimulationId();

        landRover(simulationId, 5, 5);
        landRover(simulationId, 5, 6);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/moverover/" + simulationId + "/R1 f1"));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/moverover/" + simulationId + "/R1 f1"));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/moverover/" + simulationId + "/R1 f1"));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/moverover/" + simulationId + "/R1 f1"));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/moverover/" + simulationId + "/R1 f1"));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/moverover/" + simulationId + "/R1 f1"));

        var result = getSimulationState(simulationId);

        result.andExpect(jsonPath("$.roverList[0].roverName").value("R1"));
        result.andExpect(jsonPath("$.roverList[0].roverHeading").value("NORTH"));
        result.andExpect(jsonPath("$.roverList[0].roverXPosition").value("5"));
        result.andExpect(jsonPath("$.roverList[0].roverYPosition").value("5"));
        result.andExpect(jsonPath("$.roverList[0].hitPoints").value("0"));
        result.andExpect(jsonPath("$.roverList[0].operationalStatus").value("BROKEN"));
    }

    @Test
    void applicationShouldRetrieveSimulationStateOnReboot() throws Exception {
        createSimulationOf10();
        final String simulationId = getSimulationId();

        var result = getSimulationState(simulationId);

        result = getSimulationState(simulationId);

        result.andExpect(content().string(containsString("10")));
        result.andExpect(jsonPath("$.simulationSize").value("10"));
    }

    private void createSimulationOf10() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/createsimulation/10")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"));

    }

    private void landRoverOn55(String simulationId) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/landrover/" + simulationId + "/5/5")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"));
    }

    private void landRover(String simulationId, int x, int y) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/landrover/" + simulationId + "/" + x + "/" + y)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"));
    }

    private ResultActions getSimulationState(String simulationId) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get("/api/simulationstate/" + simulationId)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"));
    }

    private ResultActions getSimulationStates() throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get("/api/simulationstates")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"));
    }

    private String getSimulationId() throws Exception {
        final String simulationState = getSimulationStates().andReturn().getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        SimulationViewDTO[] objects = objectMapper.readValue(simulationState, SimulationViewDTO[].class);

        String simulationId = objects[0].simulationId();
        return simulationId;
    }

    private String getSimulationName() throws Exception {
        final String simulationState = getSimulationStates().andReturn().getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        SimulationViewDTO[] objects = objectMapper.readValue(simulationState, SimulationViewDTO[].class);

        String simulationName = objects[0].simulationName();
        return simulationName;
    }
}
