package com.lyonguyen.esky.ws.utils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeCreator;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class JSONUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    private JSONUtils() {}

    public static String asJSON(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonGenerationException ex) {
            ex.printStackTrace();
            return null;
        } catch (JsonMappingException ex) {
            ex.printStackTrace();
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String asJSON(String name, String value) {
        ObjectNode object = mapper.createObjectNode();
        object.put(name, value);
        return object.toString();
    }

    public static String asJSON(String name, int value) {
        ObjectNode object = mapper.createObjectNode();
        object.put(name, value);
        return object.toString();
    }

    public static String asJSON(String name, boolean value) {
        ObjectNode object = mapper.createObjectNode();
        object.put(name, value);
        return object.toString();
    }

    public static JSONBuilder create() {
        return new JSONBuilder();
    }

    public static class JSONBuilder {

        ObjectNode objectNode;

        private JSONBuilder() {
            objectNode = mapper.createObjectNode();
        }

        public JSONBuilder add(String name, String value) {
            objectNode.put(name, value);
            return this;
        }

        public JSONBuilder add(String name, int value) {
            objectNode.put(name, value);
            return this;
        }

        public JSONBuilder add(String name, Object value) {
            Object object = mapper.valueToTree(value);
            objectNode.set(name,(JsonNode) object);
            return this;
        }

        public String build() {
            return objectNode.toString();
        }
    }
}
