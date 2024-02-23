package io.tripled.marsrover.rest;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonFactory {
    private final JsonNodeFactory factory = JsonNodeFactory.instance;

    public String createResult(String result, String roverId) {
        ObjectNode rootNode = new ObjectNode(factory);
        ObjectNode dataNode = new ObjectNode(factory);

        rootNode.put("result", result);
        dataNode.put("roverId", roverId);
        rootNode.set("roverData", dataNode);

        return rootNode.toString();
    }
}
