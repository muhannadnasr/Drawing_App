package com.example.backend;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {

    public static String obj2json(Object a) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = "";
        try {
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(a);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

    public static String jsonStrFromHashMap(HashMap<Integer, Shape> map) {
        String jsonStr = "{" + "\"Shapes\"" + ":" + " " + "{" + "\"Shape\"" + ":" + " " + "[" + "\n";
        for (Map.Entry<Integer, Shape> set : map.entrySet()) {
            jsonStr += obj2json(set.getValue());
            jsonStr += "," + "\n";
        }
        jsonStr = jsonStr.substring(0, jsonStr.length() - 2);
        jsonStr += "\n] } \n}";
        return jsonStr;

    }

    public static void saveJson(String path, String str) {
        PrintWriter out;
        try {
            out = new PrintWriter(path + ".json");
            out.println(str);
            out.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    
}
