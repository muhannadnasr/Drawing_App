package com.example.backend;

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

    public String jsonStrFromHashMap(HashMap<Integer, Shape> map) {
        String jsonStr = "";
        /*
         * for (Entry<Integer, Shape> set : map.entrySet()) {
         * System.out.println(set.getKey() + " = " + set.getValue()); }
         */

        for (Map.Entry<Integer, Shape> set : map.entrySet()) {
		    System.out.println(set.getKey() + " = " + set.getValue());
		}

        return jsonStr;
        
    }
    
}
