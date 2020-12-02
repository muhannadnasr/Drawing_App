package com.example.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.XML;

import java.util.HashMap;
import java.util.Map;

public class JsonConverter{
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

    public String jsonStrFromXml(String xmlInputString) {
        JSONObject jsonFile = XML.toJSONObject(xmlInputString); // the process of transforming the xml file contents to json object
        String str =  jsonFile.toString(4);
        System.out.println(str);
        return str;
    }
}
