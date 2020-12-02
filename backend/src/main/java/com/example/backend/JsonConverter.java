package com.example.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class JsonConverter{
    private static JsonConverter instance;

    private JsonConverter(){}

    public static JsonConverter getInstance(){
        if(instance == null) instance = new JsonConverter();
        return instance;
    }
    public String shapeToJsonString(Shape shape){
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = "";
        try {
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(shape);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

    public String jsonStrFromHashMap(HashMap<Integer, Shape> map) {
        String jsonStr = "{" + "\"Shapes\"" + ":" + " " + "{" + "\"Shape\"" + ":" + " " + "[ " + "\n";
        for (Map.Entry<Integer, Shape> set : map.entrySet()) {
            jsonStr += shapeToJsonString(set.getValue());
            jsonStr += "," + "\n";
        }
        jsonStr = jsonStr.substring(0, jsonStr.length() - 2);
        jsonStr += "\n] } \n}";
        return jsonStr;

    }
}
